package com.kevin.gitrepos.di

import android.app.Application
import android.arch.persistence.room.Room
import com.kevin.gitrepos.data.db.AppDatabase
import com.kevin.gitrepos.data.network.GithubService
import com.kevin.gitrepos.data.repository.GitReposRepository
import com.kevin.gitrepos.util.AppExecutors
import com.kevin.gitrepos.util.ResourcesWrapper
import com.kevin.gitrepos.viewmodel.GitViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by quf93 on 2018-04-17.
 */
@Module class AppModule {

//    @Singleton @Provides
//    fun provideContext(): Context = app

    @Singleton @Provides
    fun provideResourcesWrapper(app: Application) = ResourcesWrapper(app)

    @Singleton @Provides
    fun provideGitReposRepository(appExecutors: AppExecutors, database: AppDatabase, service: GithubService):
            GitReposRepository = GitReposRepository(appExecutors, database, service)

    @Singleton @Provides
    fun provideExecutors() : AppExecutors = AppExecutors()

    @Singleton @Provides
    fun provideDB(app: Application): AppDatabase = Room
            .databaseBuilder(app, AppDatabase::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

    @Singleton @Provides
    fun provideGithubService(okHttpClient: OkHttpClient):GithubService
            = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(GithubService::class.java)

    @Provides
    fun provideGitViewModelFactory(repository: GitReposRepository) = GitViewModelFactory(repository)
}