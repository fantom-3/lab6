package Commands

import WorkerClass.IOManager
import WorkerClass.WorkerManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Info {
    private val initializationDate: LocalDateTime = LocalDateTime.now()
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun infoCommand() {
        IOManager.printMessage("Информация о коллекции:")
        IOManager.printMessage("Тип коллекции: ${WorkerManager.collection.javaClass.simpleName}")
        IOManager.printMessage("Дата инициализации: ${initializationDate.format(dateFormatter)}")
        IOManager.printMessage("Количество элементов: ${WorkerManager.collection.size}")
        IOManager.printMessage("Тип хранимых элементов: ${WorkerManager.collection.firstOrNull()?.javaClass?.simpleName ?: "Не определено"}")
        IOManager.printMessage("Автоматически генерируемые поля: id, creationDate")
    }
}