package models

import java.time.LocalDateTime

data class Receipt(
    val ticketID: Int,
    val spotID: Int,
    val entryTime: Int,
    val exitTime: LocalDateTime,
    val bill: Int
)
