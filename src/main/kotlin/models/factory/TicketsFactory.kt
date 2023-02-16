package models.factory

import models.ParkingTicket
import java.time.LocalDateTime

object TicketsFactory {
    private var ticketIdCounter = 0

    fun getTicketFor(spotID: Int, entryTime: LocalDateTime): ParkingTicket {
        ticketIdCounter++
        return ParkingTicket(ticketIdCounter, spotID, entryTime)
    }
}
