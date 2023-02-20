package models

import constants.Charges
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ParkingLot(numberOfFloors: Int, spotsPerFloor: Int) {

    private val floors = MutableList(numberOfFloors) { ParkingFloor(it, spotsPerFloor) }
    fun getSpotBySpotNumberAndFloorNumber(spotNumber: Int, floorNumber: Int): ParkingSpot {
        return floors[floorNumber].getSpotById(spotNumber)
    }
    fun calculateBill(ticket: ParkingTicket, exitTime: LocalDateTime): Int {
        val numberOfHours = ticket.entryTime.until(exitTime, ChronoUnit.HOURS).toInt()
        return numberOfHours * Charges.PER_HOUR_CHARGE
    }

    fun getFirstAvailableSpot(): ParkingSpot? {
        return floors.firstOrNull { floor -> floor.isSpotAvailable() }?.getFirstAvailableSpot()
    }
}
