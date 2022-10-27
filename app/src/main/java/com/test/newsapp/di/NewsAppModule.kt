package com.test.newsapp.di

import com.test.newsapp.data.remote.NewsService
import com.test.newsapp.data.repository.NewsRepositoryImpl
import com.test.newsapp.domain.repository.NewsRepository
import com.test.newsapp.domain.use_case.GetDefaultNews
import com.test.newsapp.domain.use_case.NewsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsAppModule {

    @Provides
    @Singleton
    fun provideNewsService(): NewsService {
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client.connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)

        client.addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    "129fd7962b2e4857886644231126d1eb"  // NOTE: This is unsafe, Normally I would put it in a separate file then access it from BuildConfig
                )
                .build()
            chain.proceed(request)
        }

        return Retrofit.Builder()
            .baseUrl(NewsService.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(service: NewsService): NewsRepository =
        NewsRepositoryImpl(service = service)

    @Provides
    @Singleton
    fun provideUseCases(repository: NewsRepository): NewsUseCases =
        NewsUseCases(
            getDefaultNews = GetDefaultNews(repository)
        )
}