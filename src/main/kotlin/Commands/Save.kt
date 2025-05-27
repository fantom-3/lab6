package Commands

import WorkerClass.*
import WorkerClass.IOManager
import java.io.FileWriter

/**
 * Команда для сохранения коллекции в XML-файл.
 */
object Save {

    /**
     * Выполняет сохранение коллекции в файл.
     * @param filename Имя файла для сохранения (если null, используется значение по умолчанию)
     */
    fun saveCommand(filename: String? = null) {
        val targetFile = filename ?: "workers.xml"

        try {
            if (WorkerManager.collection.isEmpty()) {
                IOManager.printMessage("Коллекция пуста. Ничего не будет сохранено.")
                return
            }

            val confirm = IOManager.readConfirmation("Сохранить коллекцию в файл $targetFile?")
            if (!confirm) {
                IOManager.printMessage("Сохранение отменено.")
                return
            }

            FileWriter(targetFile).use { writer ->
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                writer.write("<workers>\n")

                WorkerManager.collection.forEach { worker ->
                    writer.write(workerToXml(worker))
                }

                writer.write("</workers>")
            }

            IOManager.printMessage("Коллекция успешно сохранена в файл $targetFile")
        } catch (e: SecurityException) {
            IOManager.printError("Нет прав для записи в файл $targetFile")
        } catch (e: Exception) {
            IOManager.printError("Ошибка при сохранении файла: ${e.message}")
        }
    }

    private fun workerToXml(worker: Worker): String {
        return """
        |  <worker>
        |    <id>${worker.id}</id>
        |    <name>${escapeXml(worker.name)}</name>
        |    <coordinates>
        |      <x>${worker.coordinates.x}</x>
        |      <y>${worker.coordinates.y}</y>
        |    </coordinates>
        |    <creationDate>${worker.creationDate}</creationDate>
        |    <salary>${worker.salary}</salary>
        |    <startDate>${worker.startDate}</startDate>
        |    <endDate>${worker.endDate ?: ""}</endDate>
        |    <position>${worker.position?.name ?: ""}</position>
        |    <person>
        |      <birthday>${worker.person.birthday}</birthday>
        |      <eyeColor>${worker.person.eyeColor?.name ?: ""}</eyeColor>
        |      <hairColor>${worker.person.hairColor.name}</hairColor>
        |      <nationality>${worker.person.nationality.name}</nationality>
        |    </person>
        |  </worker>
        """.trimMargin() + "\n"
    }

    private fun escapeXml(text: String): String {
        return text.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }
}