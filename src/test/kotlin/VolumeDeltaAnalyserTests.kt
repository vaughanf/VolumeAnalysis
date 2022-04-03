import Utility.Companion.timestampFormat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

internal class VolumeDeltaAnalyserTests {
    @Test
    fun analyseMeasurementsSampleOne() {
        val measurements = mutableListOf<Measurement>()
        measurements.add(Measurement(LocalDateTime.parse("2019-04-29 10:03:00", timestampFormat), 9100))
        measurements.add(Measurement(LocalDateTime.parse("2019-04-29 10:04:00", timestampFormat), 9400))
        measurements.add(Measurement(LocalDateTime.parse("2019-04-29 10:10:00", timestampFormat), 9700))
        val outputMeasurements = VolumeDeltaAnalyser().analyseMeasurements(measurements, 100)
        assertEquals(VolumeDeltaAnalyser().formatReports(outputMeasurements),
              "Start Timestamp,End Timestamp,Start Volume,End Volume\n" +
                    "2019-04-29 10:03:00,2019-04-29 10:04:00,9100,9400\n")
    }

    @Test
    fun analyseMeasurementsSampleTwo() {
        val measurements = mutableListOf<Measurement>()
        measurements.add(Measurement(LocalDateTime.parse("2019-04-29 10:03:00", timestampFormat), 9100))
        measurements.add(Measurement(LocalDateTime.parse("2019-04-29 10:04:00", timestampFormat), 9400))
        measurements.add(Measurement(LocalDateTime.parse("2019-04-29 10:10:00", timestampFormat), 10600))
        val outputMeasurements = VolumeDeltaAnalyser().analyseMeasurements(measurements, 100)
        assertEquals(
            VolumeDeltaAnalyser().formatReports(outputMeasurements),
            "Start Timestamp,End Timestamp,Start Volume,End Volume\n" +
                    "2019-04-29 10:03:00,2019-04-29 10:10:00,9100,10600\n"
        )
    }
}