package models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ParkingFloorTest {


    @Test
    fun `it should create new parking floor`() {

        val parkingFloor = ParkingFloor(1, 5)

        assertEquals(parkingFloor.floorNumber, 1)
        assertEquals(5, parkingFloor.spots.size)

    }

    @Test
    fun `it should return the first available spot given the spot is available`() {
        val parkingFloor = ParkingFloor(1, 4)

        val firstAvailableSpot = parkingFloor.getFirstAvailableSpot()

        assertNotNull(firstAvailableSpot)
        assertEquals(0, firstAvailableSpot!!.getSpotID())
    }

    @Test
    fun `it should return null given not free spot is available`() {
        val parkingFloor = ParkingFloor(1,0)

        val firstAvailableSpot = parkingFloor.getFirstAvailableSpot()

        assertNull(firstAvailableSpot)
    }
}
