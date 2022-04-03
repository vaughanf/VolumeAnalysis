import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class VolumeDeltaFileReader {
    private val timestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
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