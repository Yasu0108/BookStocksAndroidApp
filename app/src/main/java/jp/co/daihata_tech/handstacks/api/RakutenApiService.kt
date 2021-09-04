package jp.co.daihata_tech.handstacks.api

import com.google.gson.annotations.SerializedName
import jp.co.daihata_tech.handstacks.dto.BookDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RakutenApiService {
    @GET("api/BooksBook/Search/20170404")
    suspend fun getBook(
        @Query("format") format: String,
        @Query("isbn") isbn: String,
        @Query("applicationId") applicationId: String
    ): SearchResult

    data class SearchResult(
        @SerializedName("Items")
        var items: List<Item>,
        var count: Int,
        var page: Int,
        var first: Int,
        var last: Int,
        var hits: Int,
        var carrier: Int,
        var pageCount: Int
    )

    data class Item(
        @SerializedName("Item")
        var item : BookDto? = null
    )
}
