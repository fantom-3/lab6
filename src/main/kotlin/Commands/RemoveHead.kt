package Commands

import WorkerClass.IOManager
import WorkerClass.WorkerManager

object RemoveHead {
    fun remove_headCommand() {
        when {
            WorkerManager.collection.isEmpty() -> IOManager.printMessage("Коллекция пуста.")
            else -> {
                val worker = WorkerManager.collection.first()
                IOManager.printMessage(WorkerManager.firstWorker(worker))
                WorkerManager.collection.removeFirst()
                IOManager.printMessage("Элемент успешно удален.")
            }
        }
    }
}