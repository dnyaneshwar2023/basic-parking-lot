package models

import generators.TicketGenerator
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

const val PER_HOUR_CHARGE = 10

class ParkingLot(numberOfSpots: Int) {
    private val spots = MutableList(numberOfSpots) { ParkingSpot(it) }

    fun getSpotById(spotId: Int): ParkingSpot {
        return spots[spotId]
    }

    fun parkVehicle(vehicle: Vehicle, entryTime: LocalDateTime): ParkingTicket {
        if (!isSpotAvailable()) {
            throw Exception("No Spot available for parking")
        }
        val availableSpot = getFirstAvailableSpot()
        availableSpot.setVehicle(vehicle)

        return TicketGenerator.getTicketFor(availableSpot.getSpotID(), entryTime)
    }

    fun unparkVehicle(ticket: ParkingTicket, exitTime: LocalDateTime): Receipt {
        if (spots[ticket.spotID].getVehicle() == null) {
            throw Exception("No Vehicle Available at the spot")
        }

        spots[ticket.spotID].removeVehicle()
        return Receipt(ticket.ticketID, ticket.spotID, ticket.entryTime, exitTime, calculateBill(ticket, exitTime))
    }

    private fun isSpotAvailable(): Boolean {
        return spots.any { spot -> spot.isAvailable() }
    }

    private fun calculateBill(ticket: ParkingTicket, exitTime: LocalDateTime): Int {
        val numberOfHours = ticket.entryTime.until(exitTime, ChronoUnit.HOURS).toInt()
        return numberOfHours * PER_HOUR_CHARGE
    }

    private fun getFirstAvailableSpot(): ParkingSpot {
        return spots.first { spot -> spot.isAvailable() }
    }
}
