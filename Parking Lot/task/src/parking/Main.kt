package parking

import java.util.function.Predicate

fun main() {
    var park = mutableListOf<String>()
    loop@ while (true) {
        val command = readln().split(" ")
        when (command[0]) {
            "spot_by_reg" -> {
                if (park.isEmpty()) {
                    println("Sorry, a parking lot has not been created.")
                } else {
                    val regNumber = command[1]
                    val list = mutableListOf<Int>()
                    for (s in park.indices) {
                        if (park[s] != "") {
                            val carNum = park[s].split("\\s+".toRegex())[0]
                            if (carNum.lowercase() == regNumber.lowercase()) list.add(s + 1)
                        }
                    }
                    if (list.isEmpty())  println("No cars with registration number $regNumber were found.") else  println(list.joinToString(", "))
                }
            }
            "spot_by_color" -> {
                if (park.isEmpty()) {
                    println("Sorry, a parking lot has not been created.")
                } else {
                    val color = command[1]
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
            "reg_by_color" -> {
                if (park.isEmpty()) {
                    println("Sorry, a parking lot has not been created.")
                } else {
                    val color = command[1]
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
            "create" -> {
                park = createPark(command[1].toInt())
                println("Created a parking lot with ${park.size} spots.")
            }
            "park" -> if (park.isEmpty()) println("Sorry, a parking lot has not been created.") else
                if (park.stream().noneMatch(Predicate.isEqual(""))
            ) println("Sorry, the parking lot is full.") else {
                for (i in park.indices) {
                    if (park[i].isEmpty()) {
                        println("${command[2]} car parked in spot ${i + 1}.")
                        park[i] = command[1] + " " + command[2]
                        continue@loop
                    }
                }
        }
            "status" -> if (park.isEmpty()) println("Sorry, a parking lot has not been created.") else status(park)
            "leave" -> if (park.isEmpty()) println("Sorry, a parking lot has not been created.") else if (park[command[1].toInt() - 1].isNotEmpty()) {
                println("Spot ${command[1]} is free.")
                park[command[1].toInt() - 1] = ""
            } else println("There is no car in spot ${command[1]}.")
            "exit" -> return
        }
    }
}
fun createPark(spots : Int) : MutableList<String> {
    val park = mutableListOf<String>()
    for (i in 0 until spots) {
        park.add("")
    }
    return park
}
fun status(park : MutableList<String>) {
    var count = 0
    for (i in 0 until park.size) {
        if (park[i].isNotEmpty()) {
            count++
            println("${i + 1} ${park[i]}")
        }
    }
    if (count == 0) println("Parking lot is empty.")
}
