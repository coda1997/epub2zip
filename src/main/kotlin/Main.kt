fun main() {
    val files = FileReader.getFilePathFromDirectory("C:\\备份\\manga")
    EpubReader.parentPath = "D:\\workspace\\manga"
    EpubReader.covertEpub2Zip(files.toTypedArray())
}