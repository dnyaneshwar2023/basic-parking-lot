package strategies

import models.FeeInterval
import models.VehicleType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class FeeCalculationStrategyTest {
    @Test
    fun `it should calculate the fee for car for given start and end time`() {
        val intervals = mapOf(VehicleType.CAR to arrayListOf(FeeInterval(1, 1000, 100)))
        val feeCalculationStrategy = FeeCalculationStrategy(intervals)

        val billAmount = feeCalculationStrategy.getBillAmount(
            LocalDateTime.now(), LocalDateTime.now().plusMinutes(61), VehicleType.CAR
        )

        assertEquals(100, billAmount)


    }

    @Test
    fun `it should calculate the fee for car for multiple intervals`() {
        val intervals = mapOf(VehicleType.CAR to arrayListOf(FeeInterval(1, 3, 100), FeeInterval(4, 6, 200)))
        val feeCalculationStrategy = FeeCalculationStrategy(intervals)

        val billAmount = feeCalculationStrategy.getBillAmount(
            LocalDateTime.now(), LocalDateTime.now().plusHours(5), VehicleType.CAR
        )

        assertEquals(700, billAmount)


    }

    @Test
    fun `it should throw exception when there is no intervals present for a particular vehicle type`() {
        val intervals = mapOf(VehicleType.CAR to arrayListOf(FeeInterval(1, 1000, 100)))
        val feeCalculationStrategy = FeeCalculationStrategy(intervals)

        assertThrows<Exception> {
            feeCalculationStrategy.getBillAmount(
                LocalDateTime.now(), LocalDateTime.now().plusMinutes(61), VehicleType.MOTORCYCLE
            )
        }

    }
}
