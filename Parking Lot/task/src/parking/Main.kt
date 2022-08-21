package parking

import java.util.function.Predicate

fun main() {
    val park = mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
   loop@ while (true) {
        val command = readln().split(" ")
        when (command[0]) {
            "park" -> if (park.stream()
                    .noneMatch(Predicate.isEqual(""))
            ) println("Sorry, the parking lot is full.") else {
                for (i in 0..19) {
                    if (park[i].isEmpty()) {
                        println("${command[2]} car parked in spot ${i + 1}.")
                        park[i] = command[1] + " " + command[2]
                        continue@loop
                    }
                }
        }
            "leave" -> if (park[command[1].toInt() - 1].isNotEmpty()) {
                println("Spot ${command[1]} is free.")
                park[command[1].toInt() - 1] = ""
            } else println("There is no car in spot ${command[1]}.")
            "exit" -> return
        }
    }
}
