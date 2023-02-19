package generators

import models.ParkingTicket
import java.time.LocalDateTime

object TicketGenerator {
    private var ticketIdCounter = 0

    fun getTicketFor(spotID: Int, entryTime: LocalDateTime): ParkingTicket {
        ticketIdCounter++
        return ParkingTicket(ticketIdCounter, spotID, entryTime)
    }
}
