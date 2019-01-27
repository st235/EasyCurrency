package github.com.st235.easycurrency.di

import github.com.st235.easycurrency.BuildConfig
import github.com.st235.easycurrency.data.CurrencyRateStorageHelper
import github.com.st235.easycurrency.data.net.CurrencyRateApiWrapper
import github.com.st235.easycurrency.data.db.CurrencyRateDatabase
import github.com.st235.easycurrency.data.CurrencyRatesRepository
import github.com.st235.easycurrency.data.db.RoomDatabaseFactory
import github.com.st235.easycurrency.data.inmemory.CurrencyRateInMemoryModel
import github.com.st235.easycurrency.data.net.CurrencyRateApi
import github.com.st235.easycurrency.data.net.RetrofitFactory
import github.com.st235.easycurrency.data.prefs.CurrencyRatePrefs
import github.com.st235.easycurrency.domain.CurrencyListHolder
import github.com.st235.easycurrency.presentational.main.CurrenciesAdapter
import github.com.st235.easycurrency.presentational.main.MainPresenter
import github.com.st235.easycurrency.utils.UpdateTimer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

private val mainModule = module {
    /**
     * net stage
     */
    single { RetrofitFactory.createApiWrapper<CurrencyRateApi>(BuildConfig.REVOLUT_BASE_URL) }
    single { CurrencyRateApiWrapper(get()) }

    /**
     * database stage
     */
    single { RoomDatabaseFactory.create<CurrencyRateDatabase>(androidContext()) }

    /**
     * prefs stage
     */
    single { CurrencyRatePrefs(androidContext()) }

    /**
     * data layer stage
     */
    single { CurrencyRateInMemoryModel(get()) }
    single { CurrencyRateStorageHelper(get(), get()) }
    single { CurrencyRatesRepository(get(), get(), get(), get()) }

    /**
     * utils stage
     */
    single { UpdateTimer() }

    /**
     * domain stage
     */
    single { CurrencyListHolder(get()) }

    /**
     * presentation stage
     */
    single { MainPresenter(get()) }
    single { CurrenciesAdapter() }
}

val appModules = listOf(mainModule)
