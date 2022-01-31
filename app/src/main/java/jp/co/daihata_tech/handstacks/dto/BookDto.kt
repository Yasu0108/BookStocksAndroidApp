package jp.co.daihata_tech.handstacks.dto

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "stocks")
data class BookDto(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "titleKana") var titleKana: String ="",
    @ColumnInfo(name = "subTitle") var subTitle: String = "",
    @ColumnInfo(name = "subTitleKana") var subTitleKana: String = "",
    @ColumnInfo(name = "seriesName") var seriesName: String = "",
    @ColumnInfo(name = "seriesNameKana") var seriesNameKana: String = "",
    @ColumnInfo(name = "contents") var contents: String = "",
    @ColumnInfo(name = "contentsKana") var contentsKana: String = "",
    @ColumnInfo(name = "author") var author: String = "",
    @ColumnInfo(name = "authorKana") var authorKana: String = "",
    @ColumnInfo(name = "publisherName") var publisherName: String = "",
    @ColumnInfo(name = "size") var size: String = "",
    @ColumnInfo(name = "isbn") var isbn: String = "",
    @ColumnInfo(name = "itemCaption") var itemCaption: String = "",
    @ColumnInfo(name = "salesDate") var salesDate: String = "",
    @ColumnInfo(name = "itemPrice") var itemPrice: Int = 0,
    @ColumnInfo(name = "listPrice") var listPrice: Int = 0,
    @ColumnInfo(name = "discountRate") var discountRate: Int = 0,
    @ColumnInfo(name = "discountPrice") var discountPrice: Int = 0,
    @ColumnInfo(name = "itemUrl") var itemUrl: String = "",
    @ColumnInfo(name = "affiliateUrl") var affiliateUrl: String = "",
    @ColumnInfo(name = "smallImageUrl") var smallImageUrl: String = "",
    @ColumnInfo(name = "mediumImageUrl") var mediumImageUrl: String = "",
    @ColumnInfo(name = "largeImageUrl") var largeImageUrl: String = "",
    @ColumnInfo(name = "chirayomiUrl") var chirayomiUrl: String = "",
    @ColumnInfo(name = "availability") var availability: Int = 0,
    @ColumnInfo(name = "postageFlag") var postageFlag: Int = 0,
    @ColumnInfo(name = "limitedFlag") var limitedFlag: Int = 0,
    @ColumnInfo(name = "reviewCount") var reviewCount: String = "",
    @ColumnInfo(name = "reviewAverage") var reviewAverage: String = "",
    @ColumnInfo(name = "booksGenreId") var booksGenreId: String = ""
):Parcelable
