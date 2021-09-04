package jp.co.daihata_tech.handstacks.ui.bookstocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import jp.co.daihata_tech.handstacks.R

@AndroidEntryPoint
class BookStocksFragment : Fragment() {

    private val bookStocksViewModel: BookStocksViewModel by viewModels()
//    private lateinit var binding:

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        val root = inflater.inflate(R.layout.fragment_bookstocks, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        bookStocksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
