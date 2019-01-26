package github.com.st235.easycurrency.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "rates", indices = [Index(value = ["currency"], unique = true)])
data class ConvertRatesDbEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "currency") var currency: String,
    @ColumnInfo(name = "rate") var rate: Double
) {
    constructor(currency: String, rate: Double): this(null, currency, rate)
}