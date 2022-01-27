package jp.co.daihata_tech.handstacks

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.daihata_tech.handstacks.api.RakutenApiService
import jp.co.daihata_tech.handstacks.dao.BookDao
import jp.co.daihata_tech.handstacks.repository.BookRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDataBase(context)
    }

    @Provides
    @Singleton
    fun provideBookDao(appDatabase: AppDatabase): BookDao {
        return appDatabase.bookDao()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor {
            Timber.tag("OkHttp").d(it)
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRBooksApiService(okHttpClient: OkHttpClient): RakutenApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://app.rakuten.co.jp/services/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RakutenApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookRepository(
        rakutenApiService: RakutenApiService,
        bookDao: BookDao
    ): BookRepository {
        return BookRepository(rakutenApiService, bookDao)
    }
}
