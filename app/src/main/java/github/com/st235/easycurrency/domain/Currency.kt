package github.com.st235.easycurrency.domain

class Currency(val id: String,
               val title: String,
               var isBase: Boolean = false) {
    var value: Double = 1.0
    var rate: Double = 1.0

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