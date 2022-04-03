import java.time.format.DateTimeFormatter

class Utility {
    companion object {
        val timestampFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}