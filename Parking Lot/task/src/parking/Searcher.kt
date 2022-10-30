package parking

interface Searcher {

    fun spot_by_reg(regNumber: String)

    fun spot_by_color(color: String)

    fun reg_by_color(color: String)

}