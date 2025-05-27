package Commands

import WorkerClass.IOManager
import WorkerClass.WorkerManager

object Remove {
    fun remove_by_id(id: Int) {
        val initialSize = WorkerManager.collection.size
        WorkerManager.collection.removeIf { it.id == id }

        if (WorkerManager.collection.size < initialSize) {
            IOManager.printMessage("Элемент с ID $id успешно удален.")
        } else {
            IOManager.printError("Элемент с ID $id не найден.")
        }
    }
}