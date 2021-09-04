package jp.co.daihata_tech.handstacks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.co.daihata_tech.handstacks.dao.BookDao
import jp.co.daihata_tech.handstacks.dto.BookDto

@Database(
    entities = [
        BookDto::class
    ],
    version = AppDatabase.VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val NAME = "live_finder.db"
        const val VERSION = 1

        fun getDataBase(context: Context): AppDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
