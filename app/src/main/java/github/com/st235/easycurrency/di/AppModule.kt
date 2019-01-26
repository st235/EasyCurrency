package github.com.st235.easycurrency.di

import github.com.st235.easycurrency.BuildConfig
import github.com.st235.easycurrency.data.CurrencyRepository
import github.com.st235.easycurrency.data.db.RatesDatabase
import github.com.st235.easycurrency.data.db.RoomDatabaseFactory
import github.com.st235.easycurrency.data.net.CurrencyApi
import github.com.st235.easycurrency.data.net.RetrofitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

private val mainModule = module {
    /**
     * net stage
     */
    single { RetrofitFactory.createApiWrapper<CurrencyApi>(BuildConfig.REVOLUT_BASE_URL) }
    single { CurrencyRepository(get()) }

    /**
     * database stage
     */
    single { RoomDatabaseFactory.create<RatesDatabase>(androidContext()) }
}

val appModules = listOf(mainModule)
