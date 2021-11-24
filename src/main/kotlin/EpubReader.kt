import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

object EpubReader {
    var parentPath:String = ""

    private lateinit var zipFile:ZipFile

    private fun readImages(path:String):List<Pair<String, InputStream>>{
        zipFile = ZipFile(path)
        val files = zipFile.entries()
        val inputs = mutableListOf<Pair<String, InputStream>>()
        while (files.hasMoreElements()) {
            val entry = files.nextElement()
            if (!entry.isDirectory&&entry.name.contains("image")){
                inputs.add(Pair(entry.name.substring(entry.name.lastIndexOf("/")+1), zipFile.getInputStream(entry)))

            }
        }
        return inputs
    }

    fun covertEpub2Zip(files:Array<File>){
        for(index in files.indices){
            val file = files[index]
            val p = file.parent
            val t = p.substring(p.lastIndexOf("\\")+1)
            val directoryPath = parentPath+"\\${t}"
            File(directoryPath).mkdirs()
            val name = "$directoryPath\\${file.nameWithoutExtension}.zip"
            print("[$index/${files.size}]covert ${file.name} to $name ... ")
            val inputs = readImages(file.absolutePath)
            zipToFile(name, inputs)
            inputs.forEach { it.second.close() }
            zipFile.close()
            println("done.")
        }
    }

    private fun zipToFile(path:String, images:List<Pair<String, InputStream>>){
        if(!File(path).exists()){
            val out = ZipOutputStream(BufferedOutputStream(FileOutputStream(path)))
            for (image in images){
                val file = image.second
                val origin = BufferedInputStream(file)
                val entry = ZipEntry(image.first)
                out.putNextEntry(entry)
                origin.readAllBytes().apply { out.write(this) }
                origin.close()
            }
            out.close()
        }
    }
}
