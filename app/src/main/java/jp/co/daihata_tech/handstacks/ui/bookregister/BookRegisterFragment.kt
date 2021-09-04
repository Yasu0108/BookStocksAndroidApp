package jp.co.daihata_tech.handstacks.ui.bookregister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.daihata_tech.handstacks.databinding.FragmentBookRegisterBinding
import jp.co.daihata_tech.handstacks.ui.scan.ScanViewModel

@AndroidEntryPoint
class BookRegisterFragment : Fragment() {

    private val viewModel: ScanViewModel by activityViewModels()
    private lateinit var binding: FragmentBookRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookRegisterBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.buttonRegisterBook.setOnClickListener {
            viewModel.registerBook()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.clearSearchBookResult()
                    findNavController().popBackStack()
                }
            })

        return binding.root
    }
}
