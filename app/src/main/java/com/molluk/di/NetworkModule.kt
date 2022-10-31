package com.molluk.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.molluk.data.character.network.CharacterNetwork
import com.molluk.data.character.network.CharacterService
import com.resource.SharedKeys
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

    // обеспечивает логирование
    @Singleton
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        interceptors.add(loggingInterceptor)
        return interceptors
    }

    // настраивает подключение по хедерам урлу и т.д. (соединяет все что создаем для коннекта в единый билдер)
    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)

        clientBuilder.addInterceptor { chain ->
            val request = chain
                .request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-type", "application/json")
                .build()
            chain.proceed(request)
        }

        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }

        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val baseUrl = "https://rickandmortyapi.com/api/"
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .build()
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedKeys.AppKey.name, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Singleton
    @Provides
    fun provideCharacterNetwork(characterService: CharacterService) = CharacterNetwork(characterService)
}