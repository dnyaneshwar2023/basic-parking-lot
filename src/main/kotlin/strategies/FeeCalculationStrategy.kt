package strategies

import models.FeeInterval
import models.VehicleType
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.min

open class FeeCalculationStrategy(private val intervals: Map<VehicleType, ArrayList<FeeInterval>>) {

    fun getNumberOfHours(startTime: LocalDateTime, endTime: LocalDateTime): Int {
        val numberOfMinutes = startTime.until(endTime, ChronoUnit.MINUTES)

        var numberOfHours = (numberOfMinutes / 60).toInt()

        if (numberOfMinutes % 60 > 0) {
            numberOfHours++
        }
        return numberOfHours
    }

    open fun getBillAmount(startTime: LocalDateTime, endTime: LocalDateTime, vehicleType: VehicleType): Int {
        var totalAmount = 0

        val numberOfHours = getNumberOfHours(startTime, endTime)

        val chargeIntervals = intervals[vehicleType]
        if (chargeIntervals != null) {
            for (interval in chargeIntervals) {
                if (interval.start > numberOfHours) {
                    break
                }

                val chargableHours = min(numberOfHours - interval.start + 1, interval.end - interval.start + 1)
                totalAmount += (chargableHours * interval.charge)
            }
        } else {
            throw Exception("Invalid Vehicle Type")
        }

        return totalAmount

    }
}
