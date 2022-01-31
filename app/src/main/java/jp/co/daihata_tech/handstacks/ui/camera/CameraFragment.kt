package jp.co.daihata_tech.handstacks.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.AndroidEntryPoint
import jp.co.daihata_tech.handstacks.databinding.CameraFragmentBinding
import jp.co.daihata_tech.handstacks.permissions.CameraPermission
import jp.co.daihata_tech.handstacks.ui.bookregister.BookRegisterFragmentDirections
import timber.log.Timber
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private lateinit var viewModel: CameraViewModel
    private lateinit var binding: CameraFragmentBinding
    private val workerExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private val launcher = registerForActivityResult(
        CameraPermission.RequestContract(), ::onPermissionResult
    )
    private val ORIENTATIONS = SparseIntArray()

    init {
        ORIENTATIONS.append(Surface.ROTATION_0, 0)
        ORIENTATIONS.append(Surface.ROTATION_90, 90)
        ORIENTATIONS.append(Surface.ROTATION_180, 180)
        ORIENTATIONS.append(Surface.ROTATION_270, 270)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CameraFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)

        viewModel.isCompleteGetBookInfo.observe(viewLifecycleOwner) {
            // TODO : ダイアログ出す？
            // 今の所とりあえず本登録画面
        }
        viewModel.bookInfo.observe(viewLifecycleOwner) { book ->
            if(book == null)return@observe

            // TODO:本情報取得出来たら画像で遷移確認ダイアログ出す？
            Toast.makeText(requireContext(), "Title:${book.title}", Toast.LENGTH_SHORT).show()
            val action = CameraFragmentDirections.actionCameraFragmentToBookRegisterFragment(book)
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()

        if (CameraPermission.hasPermission(requireContext())) {
            startScanner()
        } else {
            launcher.launch(Unit)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun onPermissionResult(granted: Boolean) {
        if (granted) {
            startScanner()
        } else {
            // TODO:前の画面に戻る
        }
    }

    private fun startScanner() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // preview
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.previewView.surfaceProvider)

            // barcodeScanner
            val barcodeScanning = BarcodeScanning.getClient(
                BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_EAN_13).build()
            )

            // camera x analyser
            val analyser = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(
                        workerExecutor,
                        ImageAnalyser(barcodeScanning) { barcodes ->
                            if (barcodes.isNullOrEmpty()) return@ImageAnalyser

                            // EAN-13
                            if (barcodes[0].format == Barcode.FORMAT_EAN_13) {

                                // 解析とめる必要ある？
                                viewModel.getBookInfo(barcodes)
                                // APIで探す
                                // 解析できたら写す
                            }
                        })
                }

            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    analyser
                )

            } catch (exc: Exception) {
                Timber.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))


    }

    private class ImageAnalyser(
        private val scanner: BarcodeScanner,
        private val callback: (List<Barcode>) -> Unit
    ) : ImageAnalysis.Analyzer {
        @SuppressLint("UnsafeOptInUsageError")
        override fun analyze(imageProxy: ImageProxy) {
            val image = imageProxy.image
            if (image != null) {
                val inputImage =
                    InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
                scanner.process(inputImage)
                    .addOnSuccessListener { callback(it) }
                    .addOnCompleteListener { imageProxy.close() }
            } else {
                imageProxy.close()
            }
        }

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
