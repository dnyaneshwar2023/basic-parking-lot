package services

import models.FeeInterval
import models.ParkingLot
import models.Vehicle
import models.VehicleType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import strategies.FeeCalculationStrategy
import java.time.LocalDateTime

class ParkingServiceTest {

    private val dummyFeeCalculationStrategy = FeeCalculationStrategy(mapOf(VehicleType.CAR to arrayListOf()))

    @Test
    fun `it should park the vehicle to first spot given the spot is available`() {
        val vehicle = Vehicle(1, VehicleType.CAR)
        val parkingLot = ParkingLot(5, 1, dummyFeeCalculationStrategy)

        val parkingTicket = ParkingService(parkingLot).park(vehicle, LocalDateTime.now())

        assertEquals(0, parkingTicket?.spotID)
        assertEquals(0, parkingTicket?.floorNumber)
        assertEquals(vehicle, parkingLot.getSpotBySpotNumberAndFloorNumber(0, 0).getVehicle())
    }

    @Test
    fun `it should park the vehicle to first spot given some vehicles are already parked`() {
        val vehicleOne = Vehicle(1, VehicleType.CAR)
        val vehicleTwo = Vehicle(2, VehicleType.CAR)
        val parkingLot = ParkingLot(5, 1, dummyFeeCalculationStrategy)
        ParkingService(parkingLot).park(vehicleOne, LocalDateTime.now())

        val parkingTicket = ParkingService(parkingLot).park(vehicleTwo, LocalDateTime.now())

        assertEquals(0, parkingTicket?.spotID)
        assertEquals(1, parkingTicket?.floorNumber)
        assertEquals(vehicleTwo, parkingLot.getSpotBySpotNumberAndFloorNumber(0, 1).getVehicle())
    }

    @Test
    fun `it should park the vehicle to first spot given some vehicles are already parked and then unparked`() {
        val vehicleOne = Vehicle(1, VehicleType.CAR)
        val vehicleTwo = Vehicle(2, VehicleType.CAR)
        val vehicleThree = Vehicle(3, VehicleType.CAR)
        val parkingLot = ParkingLot(5, 1, dummyFeeCalculationStrategy)
        val firstTicket = ParkingService(parkingLot).park(vehicleOne, LocalDateTime.now())
        ParkingService(parkingLot).park(vehicleTwo, LocalDateTime.now())
        ParkingService(parkingLot).unpark(firstTicket!!, LocalDateTime.now().plusHours(1))

        val thirdTicket = ParkingService(parkingLot).park(vehicleThree, LocalDateTime.now())

        assertEquals(0, thirdTicket?.spotID)
        assertEquals(0, thirdTicket?.floorNumber)
        assertEquals(vehicleThree, parkingLot.getSpotBySpotNumberAndFloorNumber(0, 0).getVehicle())
    }

    @Test
    fun `it should return null given no free spots are available`() {
        val vehicle = Vehicle(1, VehicleType.CAR)

        val parkingLot = ParkingLot(0, 0, dummyFeeCalculationStrategy)

        assertNull(ParkingService(parkingLot).park(vehicle, LocalDateTime.now()))
    }

    @Test
    fun `it should unpark the vehicle given it was parked on a spot`() {
        val vehicle = Vehicle(1, VehicleType.CAR)
        val feeCalculationStrategy =
            FeeCalculationStrategy(mapOf(VehicleType.CAR to arrayListOf(FeeInterval(0, 1000000, 20))))

        val parkingLot = ParkingLot(5, 1, feeCalculationStrategy)
        val parkingTicket = ParkingService(parkingLot).park(vehicle, LocalDateTime.now())

        val receipt = ParkingService(parkingLot).unpark(parkingTicket!!, LocalDateTime.now().plusHours(1))

        assertEquals(0, receipt?.spotID)
        assertEquals(0, receipt?.floorNumber)
        assertEquals(20, receipt?.bill)
    }


    @Test
    fun `it should return null while unparking given it was already unparked before`() {
        val vehicle = Vehicle(1, VehicleType.CAR)
        val parkingLot = ParkingLot(5, 1, dummyFeeCalculationStrategy)
        val ticket = ParkingService(parkingLot).park(vehicle, LocalDateTime.now())!!
        ParkingService(parkingLot).unpark(ticket, LocalDateTime.now().plusHours(1))

        assertNull(ParkingService(parkingLot).unpark(ticket, LocalDateTime.now().plusHours(1)))
    }
}
