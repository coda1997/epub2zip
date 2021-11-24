import java.io.File

object FileReader {

    fun getFilePathFromDirectory(path: String): List<File>
    = File(path).walk().filter { it.isFile }
        .filter { it.extension in listOf("epub") && !it.name.contains("._") }
        .map { it }.toList()


}