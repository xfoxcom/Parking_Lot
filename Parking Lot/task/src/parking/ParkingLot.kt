package parking

import java.util.function.Predicate

object ParkingLot : Parking, Searcher {

    private val park = mutableListOf<String>()

    override fun create(spots: Int) {

        for (i in 0 until spots) {
            park.add("")
        }

        println("Created a parking lot with ${park.size} spots.")
    }

    override fun park(name: String, color: String) {

        if (park.isEmpty()) println("Sorry, a parking lot has not been created.")
        else if (park.stream().noneMatch(Predicate.isEqual("")))
            println("Sorry, the parking lot is full.")
        else {
            for (i in park.indices) {
                if (park[i] == "") {
                    println("$color car parked in spot ${i + 1}.")
                    park[i] = "$name $color"
                    break
                }
            }
        }
    }

    override fun status() {
        if (park.isEmpty()) println("Sorry, a parking lot has not been created.")
        else {
            var count = 0
            for (i in 0 until park.size) {
                if (park[i].isNotEmpty()) {
                    count++
                    println("${i + 1} ${park[i]}")
                }
            }
            if (count == 0) println("Parking lot is empty.")
        }
    }

    override fun leave(number: Int) {

        if (park.isEmpty()) println("Sorry, a parking lot has not been created.")
        else if (park[number - 1].isNotEmpty()) {
            println("Spot $number is free.")
            park[number - 1] = ""
        } else println("There is no car in spot $number.")

    }

    override fun exit() {
        return
    }

    override fun spot_by_reg(regNumber: String) {

        if (park.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
        } else {

            val list = mutableListOf<Int>()
            for (s in park.indices) {
                if (park[s] != "") {
                    val carNum = park[s].split("\\s+".toRegex())[0]
                    if (carNum.lowercase() == regNumber.lowercase()) list.add(s + 1)
                }
            }
            if (list.isEmpty()) println("No cars with registration number $regNumber were found.") else println(
                list.joinToString(
                    ", "
                )
            )
        }
    }

    override fun spot_by_color(color: String) {

        if (park.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
        } else {

            val list = mutableListOf<Int>()
            for (s in park.indices) {
                if (park[s] != "") {
                    val carColor = park[s].split("\\s+".toRegex())[1]
                    if (carColor.lowercase() == color.lowercase()) list.add(s + 1)
                }
            }
            if (list.isEmpty()) println("No cars with color $color were found.") else println(list.joinToString(", "))
        }
    }

    override fun reg_by_color(color: String) {

        if (park.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
        } else {

            val list = mutableListOf<String>()
            for (s in park) {
                if (s != "") {
                    val car = s.split("\\s+".toRegex())[0]
                    val carColor = s.split("\\s+".toRegex())[1]
                    if (carColor.lowercase() == color.lowercase()) list.add(car)
                }
            }
            if (list.isEmpty()) println("No cars with color $color were found.") else println(list.joinToString(", "))
        }

    }

}
