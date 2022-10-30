package parking

fun main() {

    while (true) {

        val command = readln().split(" ")

        when (command[0]) {

            "spot_by_reg" -> ParkingLot.spot_by_reg(command[1])

            "spot_by_color" -> ParkingLot.spot_by_color(command[1])

            "reg_by_color" -> ParkingLot.reg_by_color(command[1])

            "create" -> ParkingLot.create(command[1].toInt())

            "park" -> ParkingLot.park(command[1], command[2])

            "status" -> ParkingLot.status()

            "leave" -> ParkingLot.leave(command[1].toInt())

            "exit" -> ParkingLot.exit()
        }
    }
}
