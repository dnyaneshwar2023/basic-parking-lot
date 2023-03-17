package strategies

import models.FeeInterval
import models.VehicleType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class AirportFeeCalculationStrategyTest {

    @Test
    fun `it should calculate airport parking lot fee for given start and end time`() {

        val intervals =
            mapOf(
                VehicleType.CAR to arrayListOf(
                    FeeInterval(0, 1, 0),
                    FeeInterval(1, 8, 40),
                    FeeInterval(8, 24, 60)
                )
            )
        val perDaysFees = mapOf(VehicleType.CAR to 2000)
        val feeCalculationStrategy = AirportFeeCalculationStrategy(intervals, perDaysFees)

        val billAmount = feeCalculationStrategy.getBillAmount(
            LocalDateTime.now(), LocalDateTime.now().plusHours(10), VehicleType.CAR
        )

        Assertions.assertEquals(400, billAmount)
    }

    @Test
    fun `it should calculate airport parking lot fee for given start and end time given number of days are more than 1`() {

        val intervals =
            mapOf(
                VehicleType.CAR to arrayListOf(
                    FeeInterval(0, 1, 0),
                    FeeInterval(1, 8, 40),
                    FeeInterval(8, 24, 60)
                )
            )
        val perDaysFees = mapOf(VehicleType.CAR to 2000)
        val feeCalculationStrategy = AirportFeeCalculationStrategy(intervals, perDaysFees)

        val billAmount = feeCalculationStrategy.getBillAmount(
            LocalDateTime.now(), LocalDateTime.now().plusDays(4), VehicleType.CAR
        )

        Assertions.assertEquals(7240, billAmount)
    }

    @Test
    fun `it should throw exception when there is no intervals present for a particular vehicle type`() {
        val intervals = mapOf(VehicleType.CAR to arrayListOf(FeeInterval(0, 1000, 100)))
        val perDaysFees = mapOf(VehicleType.CAR to 2000)

        val feeCalculationStrategy = AirportFeeCalculationStrategy(intervals, perDaysFees)

        assertThrows<Exception> {
            feeCalculationStrategy.getBillAmount(
                LocalDateTime.now(), LocalDateTime.now().plusMinutes(61), VehicleType.MOTORCYCLE
            )
        }
    }


    @Test
    fun `it should throw exception when there is no extraPerDayFee added for a particular vehicle type`() {
        val intervals = mapOf(VehicleType.CAR to arrayListOf(FeeInterval(0, 1000, 100)))
        val perDaysFees = mapOf(VehicleType.MOTORCYCLE to 2000)

        val feeCalculationStrategy = AirportFeeCalculationStrategy(intervals, perDaysFees)

        assertThrows<Exception> {
            feeCalculationStrategy.getBillAmount(
                LocalDateTime.now(), LocalDateTime.now().plusMinutes(61), VehicleType.CAR
            )
        }
    }

}
