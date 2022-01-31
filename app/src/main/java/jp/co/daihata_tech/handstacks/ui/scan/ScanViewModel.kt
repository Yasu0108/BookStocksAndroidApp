package jp.co.daihata_tech.handstacks.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.daihata_tech.handstacks.dto.BookDto
import jp.co.daihata_tech.handstacks.repository.BookRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
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
            bookRepository.fetchBookByISBN(isbn)
                .onSuccess {
                    _searchBookResult.postValue(it.item!!)
                    Timber.d("getBook Result:${it.item}")
                }
                .onFailure {
                    Timber.d("getBook 失敗しました")
                }
        }
    }
}
