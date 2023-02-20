package services

import generators.TicketGenerator
import models.ParkingLot
import models.ParkingTicket
import models.Receipt
import models.Vehicle
import java.time.LocalDateTime

class ParkingService(private val parkingLot: ParkingLot) {

    fun park(vehicle: Vehicle, entryTime: LocalDateTime): ParkingTicket? {
        val availableSpot = parkingLot.getFirstAvailableSpot() ?: return null
        availableSpot.setVehicle(vehicle)
        return TicketGenerator.getTicketFor(availableSpot.getSpotID(), availableSpot.getFloorNumber(), entryTime)
    }

    fun unpark(ticket: ParkingTicket, exitTime: LocalDateTime): Receipt? {
        val spotToUnpark = parkingLot.getSpotBySpotNumberAndFloorNumber(ticket.spotID, ticket.floorNumber)
        if (spotToUnpark.getVehicle() == null) {
            return null
        }
        spotToUnpark.removeVehicle()

        return Receipt(
            ticket.ticketID,
            ticket.spotID,
            ticket.floorNumber,
            ticket.entryTime,
            exitTime,
            parkingLot.calculateBill(ticket, exitTime)
        )
    }
}
