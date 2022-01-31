package jp.co.daihata_tech.handstacks.ui.bookregister

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.daihata_tech.handstacks.dto.BookDto
import jp.co.daihata_tech.handstacks.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookRegisterViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    var bookInfo = MutableLiveData<BookDto>()

    fun setBookInfo(bookDto: BookDto) {
        bookInfo.postValue(bookDto)
    }

    fun registerBook() = viewModelScope.launch(Dispatchers.IO) {
        val result = bookRepository.insertBook(bookInfo.value!!)
        Timber.d("registerBook() result : $result")
    }
}