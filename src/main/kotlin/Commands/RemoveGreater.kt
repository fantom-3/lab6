package Commands

import WorkerClass.IOManager
import WorkerClass.WorkerManager

object RemoveGreater {
    fun remove_greaterCommand(id: Int) {
        val initialSize = WorkerManager.collection.size
        WorkerManager.collection.removeIf { it.id > id }
        val removedCount = initialSize - WorkerManager.collection.size

        when {
            removedCount == 0 -> IOManager.printMessage("Не найдено элементов с ID больше $id")
            else -> IOManager.printMessage("Удалено $removedCount элементов")
        }
    }
}