package jp.co.daihata_tech.handstacks.ui.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
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
            scanViewModel.getBook()
        }

        return binding.root
    }
}