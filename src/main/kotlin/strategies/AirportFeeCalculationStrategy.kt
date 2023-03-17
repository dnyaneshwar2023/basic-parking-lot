package strategies

import models.FeeInterval
import models.VehicleType
import utilities.DateTimeUtil
import java.time.LocalDateTime

class AirportFeeCalculationStrategy(
    intervals: Map<VehicleType, ArrayList<FeeInterval>>,
    private val extraPerDayFee: Map<VehicleType, Int>
) : FeeCalculationStrategy(intervals) {

    override fun getBillAmount(startTime: LocalDateTime, endTime: LocalDateTime, vehicleType: VehicleType): Int {
        var totalAmount = 0
        totalAmount += super.getBillAmount(startTime, endTime, vehicleType)
        totalAmount += (DateTimeUtil.getNumberOfDaysBetween(startTime, endTime) - 1) * extraPerDayFee[vehicleType]!!

        return totalAmount
    }
}
