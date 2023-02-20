package models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParkingFloorTest {

    @Test
    fun `it should create new parking floor`() {

        val parkingFloor = ParkingFloor(1,5)

        assertEquals(parkingFloor.floorNumber, 1)
        assertEquals(5, parkingFloor.spots.size)

    }
}
