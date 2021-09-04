package jp.co.daihata_tech.handstacks.repository

import jp.co.daihata_tech.handstacks.api.RakutenApiService
import jp.co.daihata_tech.handstacks.dto.BookDto
import timber.log.Timber
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val rakutenApiService: RakutenApiService
) {

    suspend fun getBookByISBN(isbn: String): RakutenApiService.Item {
        val result = rakutenApiService.getBook("json", isbn, "1065192769086500181")
        Timber.d("getBookByISBN Result:$result")
        return result.items.first()
    }
}
