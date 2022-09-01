package com.shahjad.fabhotels.di

import android.content.Context
import android.content.SharedPreferences
import androidx.viewbinding.BuildConfig
import com.shahjad.chatspace.util.Constants
import com.shahjad.fabhotels.data.LoginDataSource
import com.shahjad.fabhotels.data.LoginRepository
import com.shahjad.fabhotels.data.local.AppSharedPreference
import com.shahjad.fabhotels.data.remote.ApiService
import com.shahjad.fabhotels.data.remote.DefaultLoginRepository
import com.shahjad.fabhotels.data.remote.RemoteLoginDataSource
import com.shahjad.fabhotels.data.remote.Urls.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteTasksDataSource

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun getSharedPrefInstance(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            Constants.SHARED_PREF_KEY,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideAppSharedPreference(sharedPreferences: SharedPreferences): AppSharedPreference {
        return AppSharedPreference(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        sharedPreferences: AppSharedPreference
    ) = if (!BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofitApiService(okHttpClient: OkHttpClient): ApiService =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideLoginDataSoure(remoteLoginDataSource: RemoteLoginDataSource):LoginDataSource = remoteLoginDataSource

    @Provides
    @Singleton
    fun provideLoginRepository(defaultLoginRepository: DefaultLoginRepository): LoginRepository = defaultLoginRepository

}