package jp.co.daihata_tech.handstacks

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.co.daihata_tech.handstacks.api.RakutenApiService
import jp.co.daihata_tech.handstacks.repository.BookRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ProvidesModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDataBase(context)
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor {
            Timber.tag("OkHttp").d(it)
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return logging
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideRBooksApiService(okHttpClient: OkHttpClient): RakutenApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://app.rakuten.co.jp/services/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RakutenApiService::class.java)
    }

    @Provides
    fun provideBookRepository(rakutenApiService: RakutenApiService): BookRepository {
        return BookRepository(rakutenApiService)
    }
}
