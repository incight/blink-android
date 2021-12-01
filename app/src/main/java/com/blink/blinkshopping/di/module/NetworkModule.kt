package com.blink.blinkshopping.di.module

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.blink.blinkshopping.App
import com.blink.blinkshopping.network.ApiService
import com.blink.blinkshopping.util.BASE_URL
import com.blink.blinkshopping.util.SharedStorage
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class NetworkModule {

    var isLogin = false
    var newToken: String? = null
    var sharedStorage: SharedStorage? = null

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        sharedStorage = SharedStorage.getInstance(App.context!!)
        isLogin = sharedStorage!!.isLogin
        newToken = sharedStorage!!.getUserToken()

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            if (isLogin) {
                val newRequest = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer " + newToken //newToken
                    ).addHeader(
                        "Content-Type",
                        "application/json"
                    )
                    .build()
                chain.proceed(newRequest)
            } else {
                val newRequest = chain.request().newBuilder()
                    .build()
                chain.proceed(newRequest)
            }
        }.connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor).build()

        return client

    }

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {

        // Create the http response cache store
//        val cacheStore = DiskLruHttpCacheStore(File(cacheDir, "apolloCache"), 1024 * 1024)
//        val logger = ApolloAndroidLogger()
//
//        ApolloClient.builder()
//            .serverUrl(baseUrl)
//            .normalizedCache(cacheKeyResolver)
//            .httpCache(ApolloHttpCache(cacheStore, logger))
//            .defaultHttpCachePolicy(HttpCachePolicy.CACHE_FIRST)
//            .okHttpClient(okHttpClient)
//            .build()


        val cacheFactory = LruNormalizedCacheFactory(EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build())

        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .normalizedCache(cacheFactory)
            .defaultHttpCachePolicy(HttpCachePolicy.CACHE_FIRST)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(apolloClient: ApolloClient): ApiService {
        return ApiService(apolloClient)
    }

}

/*package com.blink.blinkshopping.di.module

import com.apollographql.apollo.ApolloClient
import com.blink.blinkshopping.BuildConfig
import com.blink.blinkshopping.network.ApiService
import com.blink.blinkshopping.util.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor)
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(apolloClient: ApolloClient): ApiService {
        return ApiService(apolloClient)
    }

}*/