package models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParkingSpotTest {

    @Test
    fun `it should set the vehicle to given spot given spot is free`() {
        val vehicle = Vehicle(1, VehicleType.CAR)
        val parkingSpot = ParkingSpot(1)

        parkingSpot.setVehicle(vehicle)

        assertEquals(parkingSpot.getVehicle(), vehicle)

    }
}
