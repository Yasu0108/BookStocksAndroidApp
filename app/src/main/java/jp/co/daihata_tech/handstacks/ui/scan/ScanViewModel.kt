package jp.co.daihata_tech.handstacks.ui.scan

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.daihata_tech.handstacks.repository.BookRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class ScanViewModel @ViewModelInject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _toastString = MutableLiveData("")
    val toastString = _toastString

    fun getBook(isbn: String) {
        Timber.d("getBook")
        viewModelScope.launch {
            val result = bookRepository.getBookByISBN(isbn)
            Timber.d("getBook Result:${result}")

        }
    }
}