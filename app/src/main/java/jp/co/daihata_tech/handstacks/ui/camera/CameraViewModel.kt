package jp.co.daihata_tech.handstacks.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.daihata_tech.handstacks.dto.BookDto
import jp.co.daihata_tech.handstacks.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    // 解析したEAN13
    var _analysedCode = MutableLiveData<String>("")
    var analysedCode: LiveData<String> = _analysedCode

    // 解析&情報取得完了flag
    var _isCompleteGetBookInfo = MutableLiveData(false)
    var isCompleteGetBookInfo: LiveData<Boolean> = _isCompleteGetBookInfo

    // 取得出来た本情報
    var bookInfo = MutableLiveData<BookDto>()

    val isFetchingBookInfo = AtomicBoolean(false)

    /**
     * 本取得メソッド. 取得出来たらフラグ更新
     */
    fun getBookInfo(barcodes: List<Barcode>) = viewModelScope.launch(Dispatchers.IO) {
        // １回ずつしか処理しない
        Timber.d("getBookInfo by ${Thread.currentThread().name}")

        if (isFetchingBookInfo.get() ) {
            Timber.d("getBookInfo 取得中のため処理中断")
            return@launch
        }
        if(isCompleteGetBookInfo.value!!) {
            Timber.d("getBookInfo 取得完了したため処理中断")
            return@launch
        }

        Timber.d("getBookInfo 取得中ではないので処理続行")

        isFetchingBookInfo.set(true)

        barcodes.forEach {
            bookRepository.fetchBookByISBN(it.rawValue)
                .onSuccess {
                    Timber.d("本情報取得完了 ${it.item}")
                    _isCompleteGetBookInfo.postValue(true)
                    bookInfo.postValue(it.item!!)
                }
                .onFailure {
                    return@forEach
                }
        }
        isFetchingBookInfo.set(false)
    }
}
