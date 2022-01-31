package jp.co.daihata_tech.handstacks.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.co.daihata_tech.handstacks.dto.BookDto

@Dao
interface BookDao {
    @Query("SELECT * FROM stocks")
    fun getAll(): List<BookDto>

    @Insert
    fun insert(vararg books: BookDto):Int

    @Update
    fun update(bookDto: BookDto)
}