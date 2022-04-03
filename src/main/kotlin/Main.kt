import java.io.FileNotFoundException
import java.time.format.DateTimeParseException


fun main(args: Array<String>) {
    val usage = "Parameters: [file name] [maximum delta]"
    require(args.size == 2) { usage }

    // Checking this here rather than in the class responsible for it as we need to provide feedback
    // on the incorrect value.
    val maxDelta = args[1].toIntOrNull()
    require(maxDelta != null) { usage }

    try {
        val measurements = VolumeDeltaFileReader().readMeasurementsFromFile(args[0])
        val analyser = VolumeDeltaAnalyser()
        val reportableDeltas = analyser.analyseMeasurements(measurements, maxDelta)
        println(analyser.formatReports(reportableDeltas))
    } catch (ex: DateTimeParseException) {
        println("Invalid timestamp encountered while parsing file ${args[0]}: ${ex.message}")
    } catch (ex: java.lang.NumberFormatException) {
        println("Invalid volume encountered while parsing file ${args[0]}: ${ex.message}")
    } catch (ex: FileNotFoundException) {
        println("File not found: ${ex.message}")
    } catch (ex: Exception) {
        println("Failed to analyse volumes: ${ex.message}")
    }
}