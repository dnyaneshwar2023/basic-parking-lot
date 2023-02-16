package models

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

const val PER_HOUR_CHARGE = 10

class ParkingLot(numberOfSpots: Int) {
    private val spots = MutableList(numberOfSpots) { ParkingSpot(it, ParkingStatus.FREE) }

    private fun calculateBill(ticket: ParkingTicket, exitTime: LocalDateTime): Int {
        val numberOfHours = ticket.entryTime.until(exitTime, ChronoUnit.HOURS).toInt()
        return numberOfHours * PER_HOUR_CHARGE
    }

    private fun getFirstAvailableSpot(): ParkingSpot {
        return spots.first { spot -> spot.isAvailable() }
    }

    private fun isSpotAvailable(): Boolean {
        return spots.any { spot -> spot.isAvailable() }
    }

    fun getSpotById(spotId: Int): ParkingSpot {
        return spots[spotId]
    }

    fun parkVehicle(vehicle: Vehicle): ParkingTicket {
        if (!isSpotAvailable()) {
            throw Exception("No Spot available for parking")
        }

        val availableSpot = getFirstAvailableSpot()

        availableSpot.setVehicle(vehicle)

        return ParkingTicket(1, availableSpot.getSpotID(), LocalDateTime.now())
    }

    fun unparkVehicle(ticket: ParkingTicket): Receipt {

        val spotToUnpark = spots[ticket.spotID]

        if (spotToUnpark.getVehicle() == null) {
            throw Exception("No Vehicle Available at the spot")
        }

        spotToUnpark.removeVehicle()

        val exitTime = LocalDateTime.now().plusHours(1)

        val billAmount = calculateBill(ticket, exitTime)

        return Receipt(ticket.ticketID, ticket.spotID, ticket.entryTime, exitTime, billAmount)
    }

}
