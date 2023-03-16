package strategies

import models.FeeInterval
import models.VehicleType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class FeeCalculationStrategyTest {
    @Test
    fun `it should calculate the fee for car for given start and end time`() {
        val intervals = mapOf(VehicleType.CAR to arrayListOf<FeeInterval>(FeeInterval(1, 1000, 100)))
        val feeCalculationStrategy = FeeCalculationStrategy(intervals)

        val billAmount = feeCalculationStrategy.getBillAmount(
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(61),
            VehicleType.CAR
        )

        Assertions.assertEquals(100, billAmount)


    }

    @Test
    fun `it should throw exception when there is no intervals present for a particular vehicle type`() {
        val intervals = mapOf(VehicleType.CAR to arrayListOf<FeeInterval>(FeeInterval(1, 1000, 100)))
        val feeCalculationStrategy = FeeCalculationStrategy(intervals)

        assertThrows<Exception> {
            feeCalculationStrategy.getBillAmount(
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(61),
                VehicleType.MOTORCYCLE
            )
        }

    }
}
