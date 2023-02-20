package models

import constants.Charges
import generators.TicketGenerator
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ParkingLot(numberOfFloors: Int, spotsPerFloor: Int) {

    private val floors = MutableList(numberOfFloors) { ParkingFloor(it, spotsPerFloor) }

    fun getSpotBySpotNumberAndFloorNumber(spotNumber: Int, floorNumber: Int): ParkingSpot {
        return floors[floorNumber - 1].getSpotById(spotNumber)
    }

    fun parkVehicle(vehicle: Vehicle, entryTime: LocalDateTime): ParkingTicket? {
        val availableSpot = getFirstAvailableSpot() ?: return null
        availableSpot.setVehicle(vehicle)

        return TicketGenerator.getTicketFor(availableSpot.getSpotID(), entryTime)
    }

    fun unparkVehicle(ticket: ParkingTicket, exitTime: LocalDateTime): Receipt {
        val spotToUnpark = getSpotBySpotNumberAndFloorNumber(ticket.spotID, ticket.floorNumber)
        spotToUnpark.removeVehicle()

        return Receipt(
            ticket.ticketID,
            ticket.spotID,
            ticket.floorNumber,
            ticket.entryTime,
            exitTime,
            calculateBill(ticket, exitTime)
        )
    }

    private fun calculateBill(ticket: ParkingTicket, exitTime: LocalDateTime): Int {
        val numberOfHours = ticket.entryTime.until(exitTime, ChronoUnit.HOURS).toInt()
        return numberOfHours * Charges.PER_HOUR_CHARGE
    }

    private fun getFirstAvailableSpot(): ParkingSpot? {
        return floors.firstOrNull { floor -> floor.isSpotAvailable() }?.getFirstAvailableSpot()
    }
}
