package jp.co.daihata_tech.handstacks.repository

import jp.co.daihata_tech.handstacks.api.RakutenApiService
import jp.co.daihata_tech.handstacks.dao.BookDao
import jp.co.daihata_tech.handstacks.dto.BookDto
import timber.log.Timber
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val rakutenApiService: RakutenApiService,
    private val bookDao: BookDao
) {

    suspend fun fetchBookByISBN(isbn: String): Result<RakutenApiService.Item> {
        val result = rakutenApiService.getBook("json", isbn, "1065192769086500181")
        Timber.d("getBookByISBN Result:$result")
        return if (result.items.isEmpty()) {
            Result.failure(Throwable("本が取得出来ませんでした"))
        } else {
            Result.success(result.items.first())
        }
    }

    suspend fun insertBook(bookDto: BookDto): Int {
        return bookDao.insert(bookDto)
    }
}
