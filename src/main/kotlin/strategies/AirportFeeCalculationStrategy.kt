package strategies

import models.FeeInterval
import models.VehicleType
import java.time.LocalDateTime

class AirportFeeCalculationStrategy(
    intervals: Map<VehicleType, ArrayList<FeeInterval>>,
    private val extraPerDayFee: Int
) : FeeCalculationStrategy(intervals) {

    override fun getBillAmount(startTime: LocalDateTime, endTime: LocalDateTime, vehicleType: VehicleType): Int {
        var totalAmount = 0
        totalAmount += super.getBillAmount(startTime, endTime, vehicleType)

        val numberOfHours = getNumberOfHours(startTime, endTime)

        var numberOfDays = (numberOfHours / 24)

        if (numberOfHours % 24 > 0) {
            numberOfDays++
        }

        totalAmount += (numberOfDays - 1) * extraPerDayFee

        return totalAmount
    }
}
