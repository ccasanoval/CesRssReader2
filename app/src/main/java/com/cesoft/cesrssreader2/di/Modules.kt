package com.cesoft.cesrssreader2.di

import android.app.Application
import androidx.room.Room
import com.cesoft.cesrssreader2.data.Repo
import com.cesoft.cesrssreader2.data.local.FeedDb
import com.cesoft.cesrssreader2.data.local.dao.FeedDao
import com.cesoft.cesrssreader2.data.remote.FeedService
import com.cesoft.cesrssreader2.data.remote.Util
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

	//factory<CropsViewModel> { (view: CropsFragment) -> CropsViewModel(view.findNavController()) }
}

val remoteModule = module {
	single { Util(androidApplication()) }
	single { FeedService(get()) }
}

val localModule = module {
	fun provideDatabase(application: Application): FeedDb {
		return Room.databaseBuilder(application, FeedDb::class.java, FeedDb.NAME)
			.fallbackToDestructiveMigration()
			//.allowMainThreadQueries()
			.build()
	}
	fun provideFeedDao(db: FeedDb): FeedDao {
		return db.feedDao
	}
	single { provideDatabase(androidApplication()) }
	single { provideFeedDao(get()) }
}

val repoModule = module {
	fun provideRepo(dao: FeedDao, service: FeedService): Repo {
		return Repo(dao, service)
	}

	single { provideRepo(get(), get()) }
}