package github.com.st235.easycurrency.domain

class Currency(val id: String,
               val title: String,
               var isBase: Boolean = false) {
    companion object {
        fun copyWithNewRate(currency: Currency, newRate: Double): Currency {
            val newOne = Currency(currency)
            newOne.value = currency.value
            newOne.rate = newRate
            return newOne
        }

        fun copyWithNewValue(currency: Currency, newValue: Double): Currency {
            val newOne = Currency(currency)
            newOne.value = newValue
            return newOne
        }
    }

    var value: Double = 1.0
    var rate: Double = 1.0

    constructor(currency: Currency): this(currency.id, currency.title, currency.isBase) {
        rate = currency.rate
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Currency

        if (id != other.id) return false
        if (isBase != other.isBase) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + isBase.hashCode()
        return result
    }
}
