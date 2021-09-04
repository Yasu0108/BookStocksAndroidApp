package jp.co.daihata_tech.handstacks.ui.scan

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.daihata_tech.handstacks.dto.BookDto
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

    private val _searchBookResult: MutableLiveData<BookDto> = MutableLiveData()
    val searchBookResult = _searchBookResult

    /**
     * ISBNコードで楽天BOOKSAPI検索
     */
    fun getBook(isbn: String) {
        Timber.d("getBook")
        viewModelScope.launch {
            val result = bookRepository.getBookByISBN(isbn)
            _searchBookResult.postValue(result.item)
            Timber.d("getBook Result:${result}")
        }
    }

    fun registerBook() {
        viewModelScope.launch {
            searchBookResult
        }
    }

    fun clearSearchBookResult() {
        _searchBookResult.postValue(null)
    }
}
