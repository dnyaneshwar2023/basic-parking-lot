package strategies

import models.FeeInterval
import models.VehicleType
import utilities.DateTimeUtil
import java.time.LocalDateTime
import kotlin.math.min

open class FeeCalculationStrategy(private val intervals: Map<VehicleType, ArrayList<FeeInterval>>) {

    open fun getBillAmount(startTime: LocalDateTime, endTime: LocalDateTime, vehicleType: VehicleType): Int {
        var totalAmount = 0

        val numberOfHours = DateTimeUtil.getNumberOfHoursBetween(startTime, endTime)

        val chargeIntervals = intervals[vehicleType]
        if (chargeIntervals != null) {
            for (interval in chargeIntervals) {
                if (interval.start > numberOfHours) {
                    break
                }

                val chargableHours = min(numberOfHours - interval.start , interval.end - interval.start)
                totalAmount += (chargableHours * interval.charge)
            }
        } else {
            throw Exception("Invalid Vehicle Type")
        }

        return totalAmount

    }
}
