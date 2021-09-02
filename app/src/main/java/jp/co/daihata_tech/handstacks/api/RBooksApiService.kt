package jp.co.daihata_tech.handstacks.api

import retrofit2.http.GET

interface RBooksApiService {
    @GET("api/json?method=getPrefectures")
    suspend fun getBook(): PrefecturesDto
}