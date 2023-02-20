package models

import java.time.LocalDateTime

data class Receipt(
    val ticketID: Int,
    val spotID: Int,
    val floorNumber: Int,
    val entryTime: LocalDateTime,
    val exitTime: LocalDateTime,
    val bill: Int
)
