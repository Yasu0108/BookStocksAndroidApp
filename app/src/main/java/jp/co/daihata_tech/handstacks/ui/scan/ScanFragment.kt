package jp.co.daihata_tech.handstacks.ui.scan

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import jp.co.daihata_tech.handstacks.R
import jp.co.daihata_tech.handstacks.databinding.FragmentScanBinding

@AndroidEntryPoint
class ScanFragment : Fragment() {

    private val scanViewModel: ScanViewModel by viewModels()
    private lateinit var binding: FragmentScanBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScanBinding.inflate(inflater, container, false).apply {
            viewModel = scanViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.btnInputIsbn.setOnClickListener { v ->
            // ISBNコード入力ダイアログ表示
            val isbnEditText = inflater.inflate(R.layout.edittext_input_isbn, null)
            val editText = isbnEditText.findViewById<TextInputEditText>(R.id.text_input_isbn)
            editText.apply {
                inputType = InputType.TYPE_CLASS_NUMBER
                isSingleLine = true
            }

            MaterialAlertDialogBuilder(requireContext()).apply {
                setNegativeButton("キャンセル") { dialog, which -> dialog?.cancel() }
                setPositiveButton("OK") { dialog, witch ->
                    val inputISBN = editText.text.toString()
                    scanViewModel.getBook(inputISBN)
                    dialog.dismiss()
                }
                setMessage("ISBNコードを入力してください")
                setView(isbnEditText)
            }.show()
        }

        return binding.root
    }
}