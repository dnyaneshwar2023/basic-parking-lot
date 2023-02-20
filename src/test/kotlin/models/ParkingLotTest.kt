package models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ParkingLotTest {

    @Test
    fun `it should return the first available spot from the parking lot`() {
        val parkingLot = ParkingLot(1, 1)

        val firstAvailableSpot = parkingLot.getFirstAvailableSpot()

        assertEquals(0, firstAvailableSpot?.getSpotID())
        assertEquals(0, firstAvailableSpot?.getFloorNumber())
    }

    @Test
    fun `it should return null if no spot available`() {
        val parkingLot = ParkingLot(3, 0)

        val firstAvailableSpot = parkingLot.getFirstAvailableSpot()

        assertNull(firstAvailableSpot)
    }

    @Test
    fun `it should calculate the bill for 1 hour`() {
        val parkingLot = ParkingLot(3, 1)

        val billAmount =
            parkingLot.calculateBill(ParkingTicket(1, 0, 0, LocalDateTime.now()), LocalDateTime.now().plusHours(1))
        assertEquals(10, billAmount)
    }

    @Test
    fun `it should calculate the bill for 1 hour 10 minutes`() {
        val parkingLot = ParkingLot(3, 1)

        val billAmount =
            parkingLot.calculateBill(ParkingTicket(1, 0, 0, LocalDateTime.now()), LocalDateTime.now().plusHours(1).plusMinutes(10))
        assertEquals(20, billAmount)
    }
}
