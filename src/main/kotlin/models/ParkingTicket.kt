package models

import java.time.LocalDateTime

data class ParkingTicket(val ticketID: Int, val floorNumber: Int, val spotID: Int, val entryTime: LocalDateTime) {
}
