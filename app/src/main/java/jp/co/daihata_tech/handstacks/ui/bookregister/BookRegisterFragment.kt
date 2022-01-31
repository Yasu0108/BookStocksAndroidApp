package jp.co.daihata_tech.handstacks.ui.bookregister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import jp.co.daihata_tech.handstacks.R
import jp.co.daihata_tech.handstacks.databinding.FragmentBookRegisterBinding
import jp.co.daihata_tech.handstacks.ui.scan.ScanViewModel

@AndroidEntryPoint
class BookRegisterFragment : Fragment() {

    private val viewModel: BookRegisterViewModel by viewModels()
    private lateinit var binding: FragmentBookRegisterBinding
    val args: BookRegisterFragmentArgs by navArgs()

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

        viewModel.setBookInfo(args.bookDto)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_bookRegisterFragment_to_navigation_scan)
                }
            })

        return binding.root
    }
}
