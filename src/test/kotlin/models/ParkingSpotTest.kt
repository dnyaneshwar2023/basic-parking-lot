package models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ParkingSpotTest {

    @Test
    fun `it should set the vehicle to given spot given spot is free`() {
        val vehicle = Vehicle(1, VehicleType.CAR)
        val parkingSpot = ParkingSpot(1, ParkingStatus.FREE)

        parkingSpot.setVehicle(vehicle)

        assertEquals(parkingSpot.getVehicle(), vehicle)

    }

    @Test
    fun `it should throw exception to given spot given spot is not available`() {
        val vehicleOne = Vehicle(1, VehicleType.CAR)
        val vehicleTwo = Vehicle(2,VehicleType.CAR)

        val parkingSpot = ParkingSpot(1, ParkingStatus.FREE)
        parkingSpot.setVehicle(vehicleOne)

        assertThrows<Exception> { parkingSpot.setVehicle(vehicleTwo) }

    }
}
