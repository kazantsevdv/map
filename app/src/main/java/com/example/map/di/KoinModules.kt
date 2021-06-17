package com.example.map.di


import android.app.Application
import androidx.room.Room
import com.example.map.data.MarkerDB
import com.example.map.repo.IMarkerRepo
import com.example.map.repo.MarkerRepoImp
import com.example.map.view.HistActivityViewModel
import com.example.map.view.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModule = module {

    fun provideMarkerRepo(database: MarkerDB): IMarkerRepo {
        return MarkerRepoImp(database)
    }
    single { provideMarkerRepo(get()) }
}


val model = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { HistActivityViewModel(get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): MarkerDB {
        return Room.databaseBuilder(application, MarkerDB::class.java, "db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { provideDatabase(get()) }
}