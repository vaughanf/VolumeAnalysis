import java.time.temporal.ChronoUnit
import kotlin.math.abs
import kotlin.math.roundToInt

data class MeasurementDelta (val first: Measurement, var second: Measurement) {

    // Calculation components broken out for readability
    private val minutesBetween = ChronoUnit.SECONDS.between(first.timestamp, second.timestamp) / 60.0
    private val volumeDifference = abs(second.volume - first.volume)
    val rateChangeBetween = (volumeDifference / minutesBetween).roundToInt()

    init {
        require(minutesBetween > 0) { "Measurements must have a non-zero time between them" }
    }
}