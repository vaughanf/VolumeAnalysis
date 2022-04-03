import Utility.Companion.timestampFormat

class VolumeDeltaAnalyser () {

    /*
     * Accepts a list of deltas that are considered to match the criteria for analysis,and provides a text report.
     */
    fun formatReports (outputReports: List<MeasurementDelta>) : String {
        var output = "Start Timestamp,End Timestamp,Start Volume,End Volume\n"
        outputReports.forEach { r ->
            output += "${r.first.timestamp.format(timestampFormat)}," +
                    "${r.second.timestamp.format(timestampFormat)}," +
                    "${r.first.volume}," +
                    "${r.second.volume}\n"
        }
        return output
    }

    /*
     * Iterate the list of measurements and analyse deltas.  Populates the list of entries that will be reported in output.
     */
    fun analyseMeasurements(measurements: List<Measurement>, maxDelta: Int) : List<MeasurementDelta> {
        val outputReports = mutableListOf<MeasurementDelta>()
        var currentDetection: MeasurementDelta? = null
        measurements.withIndex().forEach { m->
            if (m.index > 0){
                val previousEntry = measurements[m.index-1]
                val delta = MeasurementDelta(previousEntry, m.value)
                if (delta.rateChangeBetween > maxDelta) {
                    // If we have detected an exceedance in the previous measurement, and this step continues to
                    // exceed, update the timestamp in the existing detection, otherwise, create a new detection.
                    currentDetection?.let {
                        it.second = m.value
                    } ?: run {
                        currentDetection = delta
                    }
                } else {
                    // No exceedance detected - it we've got one open, add it to the report list and close it off.
                    currentDetection?.let {
                        outputReports.add(it)
                        currentDetection = null
                    }
                }
            }
        }
        // Ensure any detection in progress is closed & added.
        currentDetection?.let {
            outputReports.add(currentDetection!!)
            currentDetection = null
        }
        return outputReports
    }
}