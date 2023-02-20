package models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ParkingLotTest {

    @Test
    fun `it should park the vehicle to first spot given the spot is available`() {
        val vehicle = Vehicle(1, VehicleType.CAR)
        val parkingLot = ParkingLot(5, 1)

        val parkingTicket = parkingLot.parkVehicle(vehicle, LocalDateTime.now())

        assertEquals(0, parkingTicket?.spotID)
        assertEquals(0, parkingTicket?.floorNumber)
        assertEquals(vehicle, parkingLot.getSpotBySpotNumberAndFloorNumber(0, 0).getVehicle())
    }

    @Test
    fun `it should park the vehicle to first spot given some vehicles are already parked`() {
        val vehicleOne = Vehicle(1, VehicleType.CAR)
        val vehicleTwo = Vehicle(2, VehicleType.CAR)
        val parkingLot = ParkingLot(5, 1)
        parkingLot.parkVehicle(vehicleOne, LocalDateTime.now())

        val parkingTicket = parkingLot.parkVehicle(vehicleTwo, LocalDateTime.now())

        assertEquals(0, parkingTicket?.spotID)
        assertEquals(1,parkingTicket?.floorNumber)
        assertEquals(vehicleTwo, parkingLot.getSpotBySpotNumberAndFloorNumber(0,1).getVehicle())
    }

    @Test
    fun `it should park the vehicle to first spot given some vehicles are already parked and then unparked`() {
        val vehicleOne = Vehicle(1, VehicleType.CAR)
        val vehicleTwo = Vehicle(2, VehicleType.CAR)
        val vehicleThree = Vehicle(3, VehicleType.CAR)
        val parkingLot = ParkingLot(5, 1)
        val firstTicket = parkingLot.parkVehicle(vehicleOne, LocalDateTime.now())
        parkingLot.parkVehicle(vehicleTwo, LocalDateTime.now())
        parkingLot.unparkVehicle(firstTicket!!, LocalDateTime.now().plusHours(1))

        val thirdTicket = parkingLot.parkVehicle(vehicleThree, LocalDateTime.now())

        assertEquals(0,thirdTicket?.spotID)
        assertEquals(0,thirdTicket?.floorNumber)
        assertEquals(vehicleThree, parkingLot.getSpotBySpotNumberAndFloorNumber(0,0).getVehicle())
    }

    @Test
    fun `it should return null given no free spots are available`() {
        val vehicle = Vehicle(1, VehicleType.CAR)

        val parkingLot = ParkingLot(0,0)

        assertNull(parkingLot.parkVehicle(vehicle, LocalDateTime.now()))
    }

    @Test
    fun `it should unpark the vehicle given it was parked on a spot`() {
        val vehicle = Vehicle(1, VehicleType.CAR)
        val parkingLot = ParkingLot(5,1)
        val parkingTicket = parkingLot.parkVehicle(vehicle, LocalDateTime.now())

        val receipt = parkingLot.unparkVehicle(parkingTicket!!, LocalDateTime.now().plusHours(1))

        assertEquals(0, receipt?.spotID)
        assertEquals(0,receipt?.floorNumber)
        assertEquals(10, receipt?.bill)
    }


    @Test
    fun `it should return null while unparking given it was already unparked before`() {
        val vehicle = Vehicle(1, VehicleType.CAR)
        val parkingLot = ParkingLot(5,1)
        val ticket = parkingLot.parkVehicle(vehicle, LocalDateTime.now())

        parkingLot.unparkVehicle(ticket!!, LocalDateTime.now().plusHours(1))

        assertNull(parkingLot.unparkVehicle(ticket, LocalDateTime.now().plusHours(1)))
    }


}
