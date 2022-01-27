package jp.co.daihata_tech.handstacks.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.daihata_tech.handstacks.dto.BookDto
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {

    // 解析したEAN13
    var _analysedCode = MutableLiveData<String>("")
    var analysedCode: LiveData<String> = _analysedCode

    // 解析&情報取得完了flag
    var _isCompleteGetBookInfo = MutableLiveData(false)
    var isCompleteGetBookInfo: LiveData<Boolean> = _isCompleteGetBookInfo

    // 取得出来た本情報
    var bookInfo = MutableLiveData<BookDto>()

    /**
     * 本取得メソッド. 取得出来たらフラグ更新
     */
    fun getBookInfo() {

    }
}
