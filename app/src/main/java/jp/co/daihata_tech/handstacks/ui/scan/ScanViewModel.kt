package jp.co.daihata_tech.handstacks.ui.scan

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
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
    val isbnText = MutableLiveData("")

    private val _toastString = MutableLiveData("")
    val toastString  = _toastString

    fun getBook() {
        Timber.d("getBook")
        viewModelScope.launch {
            isbnText.value?.let {
                val result = bookRepository.getBookByISBN(it)
                Timber.d("getBook Result:${result}")
            }
        }
    }
}