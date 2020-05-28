package com.cesoft.cesrssreader2.di

import android.app.Application
import androidx.room.Room
import com.cesoft.cesrssreader2.data.Repo
import com.cesoft.cesrssreader2.data.local.RssDb
import com.cesoft.cesrssreader2.data.local.dao.RssDao
import com.cesoft.cesrssreader2.data.remote.RssServiceImpl
import com.cesoft.cesrssreader2.data.remote.Util
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

	//factory<CropsViewModel> { (view: CropsFragment) -> CropsViewModel(view.findNavController()) }
}

val remoteModule = module {
	single { Util(androidApplication()) }
	single { RssServiceImpl() }
}

val localModule = module {
	fun provideDatabase(application: Application): RssDb {
		return Room.databaseBuilder(application, RssDb::class.java, RssDb.NAME)
			.fallbackToDestructiveMigration()
			//.allowMainThreadQueries()
			.build()
	}
	fun provideFeedDao(db: RssDb): RssDao {
		return db.feedDao
	}
	single { provideDatabase(androidApplication()) }
	single { provideFeedDao(get()) }
}

val repoModule = module {
	fun provideRepo(dao: RssDao, service: RssServiceImpl): Repo {
		return Repo(dao, service)
	}

	single { provideRepo(get(), get()) }
}