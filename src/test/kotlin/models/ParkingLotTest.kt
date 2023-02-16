package models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ParkingLotTest {

    @Test
    fun `it should park the vehicle to first spot given the spot is available`() {
        val vehicle = Vehicle(1, VehicleType.CAR)
        val parkingLot = ParkingLot(5)

        val parkingTicket = parkingLot.parkVehicle(vehicle)

        assertEquals(0, parkingTicket.spotID)
        assertEquals(vehicle, parkingLot.getSpotById(parkingTicket.spotID).getVehicle())
    }

    @Test
    fun `it should park the vehicle to first spot given some vehicles are already parked`() {
        val vehicleOne = Vehicle(1, VehicleType.CAR)
        val vehicleTwo = Vehicle(1, VehicleType.CAR)
        val parkingLot = ParkingLot(5)
        parkingLot.parkVehicle(vehicleOne)

        val parkingTicket = parkingLot.parkVehicle(vehicleTwo)

        assertEquals(1, parkingTicket.spotID)
        assertEquals(vehicleTwo, parkingLot.getSpotById(parkingTicket.spotID).getVehicle())
    }

    @Test
    fun `it should park the vehicle to first spot given some vehicles are already parked and then unparked`() {
        val vehicleOne = Vehicle(1, VehicleType.CAR)
        val vehicleTwo = Vehicle(1, VehicleType.CAR)
        val vehicleThree = Vehicle(1, VehicleType.CAR)
        val parkingLot = ParkingLot(5)
        parkingLot.parkVehicle(vehicleOne)
        val ticketToUnpark = parkingLot.parkVehicle(vehicleTwo)
        parkingLot.unparkVehicle(ticketToUnpark)

        val parkingTicket = parkingLot.parkVehicle(vehicleThree)

        assertEquals(1, parkingTicket.spotID)
        assertEquals(vehicleThree, parkingLot.getSpotById(parkingTicket.spotID).getVehicle())
    }

    @Test
    fun `it should throw the exception given no free spots are available`() {
        val vehicle = Vehicle(1, VehicleType.CAR)

        val parkingLot = ParkingLot(0)

        assertThrows<Exception> { parkingLot.parkVehicle(vehicle) }
    }
}
