package models

import java.time.LocalDateTime

data class ParkingTicket(val ticketID: Int, val spotID: Int, val floorNumber: Int, val entryTime: LocalDateTime)
