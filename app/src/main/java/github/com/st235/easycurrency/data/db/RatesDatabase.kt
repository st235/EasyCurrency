package github.com.st235.easycurrency.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import github.com.st235.easycurrency.BuildConfig

@Database(entities = [RatesEntity::class], version = BuildConfig.DATABASE_VERSION)
abstract class RatesDatabase: RoomDatabase() {
    abstract fun ratesDataDao(): RatesDataDao
}