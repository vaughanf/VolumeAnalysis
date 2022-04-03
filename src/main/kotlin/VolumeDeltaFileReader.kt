import Utility.Companion.timestampFormat
import java.io.File
import java.time.LocalDateTime

class VolumeDeltaFileReader {
    /*
     * Read the specified CSV file, check for errors, and provide a list of measurement objects.
     */
    fun readMeasurementsFromFile(path: String) : List<Measurement>{
        val measurements = mutableListOf<Measurement>()

        val fileLines = File(path).readLines()
        require (fileLines.first() == "Timestamp,Volume") {"Invalid file header"}
        fileLines.subList(1, fileLines.count()).forEach { measurementString ->
            val measurementComponents = measurementString.split((','))
            require (measurementComponents.count() == 2) { "Incorrect number of arguments found within CSV file." }

            measurements.add(
                Measurement(
                    LocalDateTime.parse(measurementComponents[0], timestampFormat),
                    measurementComponents[1].toInt())
            )
        }
        return measurements
    }
}