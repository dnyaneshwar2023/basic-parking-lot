package strategies

import models.FeeInterval
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.min

open class FeeCalculationStrategy(val intervals: ArrayList<FeeInterval>) {
    open fun getBillAmount(startTime: LocalDateTime, endTime: LocalDateTime): Int {
        val numberOfMinutes = startTime.until(endTime, ChronoUnit.MINUTES)

        var numberOfHours = (numberOfMinutes / 60).toInt()

        if (numberOfMinutes % 60 > 0) {
            numberOfHours++
        }

        var totalAmount = 0

        for (interval in intervals) {
            if (interval.start > numberOfHours) {
                break
            }

            val chargableHours = min(numberOfHours - interval.start + 1, interval.end - interval.start + 1)

            totalAmount += (chargableHours * interval.charge)
        }

        return totalAmount

    }
}
