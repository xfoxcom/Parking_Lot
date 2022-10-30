package parking

interface Parking {

    fun create(spots: Int)

    fun park(name: String, color: String)

    fun status()

    fun leave(number: Int)

    fun exit()

}