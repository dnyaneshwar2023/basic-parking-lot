package models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import services.ParkingService
import strategies.FeeCalculationStrategy
import java.time.LocalDateTime

class ParkingLotTest {

    private val dummyFeeStrategy = FeeCalculationStrategy(mapOf(VehicleType.CAR to arrayListOf()))

    @Test
    fun `it should return the first available spot from the parking lot`() {
        val parkingLot = ParkingLot(1, 1, dummyFeeStrategy)

        val firstAvailableSpot = parkingLot.getFirstAvailableSpot()

        assertEquals(0, firstAvailableSpot?.getSpotID())
        assertEquals(0, firstAvailableSpot?.getFloorNumber())
    }

    @Test
    fun `it should return null if no spot available`() {
        val parkingLot = ParkingLot(3, 0, dummyFeeStrategy)

        val firstAvailableSpot = parkingLot.getFirstAvailableSpot()

        assertNull(firstAvailableSpot)
    }

    @Test
    fun `it should calculate the bill for 1 hour for car at Mall`() {
        val feeCalculationStrategy =
            FeeCalculationStrategy(mapOf(VehicleType.CAR to arrayListOf(FeeInterval(0, 1000000, 20))))

        val parkingLot = ParkingLot(3, 1, feeCalculationStrategy)

        val parkingTicket = ParkingService(parkingLot).park(Vehicle(0, VehicleType.CAR), LocalDateTime.now())

        val billAmount =
            parkingLot.calculateBill(parkingTicket!!, LocalDateTime.now().plusHours(1))
        assertEquals(20, billAmount)
    }

    @Test
    fun `it should calculate the bill for 1 hour 10 minutes for car at mall`() {

        val feeCalculationStrategy =
            FeeCalculationStrategy(mapOf(VehicleType.CAR to arrayListOf(FeeInterval(0, 1000000, 20))))

        val parkingLot = ParkingLot(3, 1, feeCalculationStrategy)

        val parkingTicket = ParkingService(parkingLot).park(Vehicle(0, VehicleType.CAR), LocalDateTime.now())

        val billAmount =
            parkingLot.calculateBill(parkingTicket!!, LocalDateTime.now().plusHours(1).plusMinutes(15))
        assertEquals(40, billAmount)
    }
}
