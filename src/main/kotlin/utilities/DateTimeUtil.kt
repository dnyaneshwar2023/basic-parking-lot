package utilities

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object DateTimeUtil {
    fun getNumberOfHoursBetween(startTime: LocalDateTime, endTime: LocalDateTime): Int {
        val numberOfMinutes = startTime.until(endTime, ChronoUnit.MINUTES)

        var numberOfHours = (numberOfMinutes / 60).toInt()

        if (numberOfMinutes % 60 > 0) {
            numberOfHours++
        }
        return numberOfHours
    }

    fun getNumberOfDaysBetween(startTime: LocalDateTime, endTime: LocalDateTime): Int {
        val numberOfHours = getNumberOfHoursBetween(startTime, endTime)
        var numberOfDays = (numberOfHours / 24)

        if (numberOfHours % 24 > 0) {
            numberOfDays++
        }
        return numberOfDays
    }
}
