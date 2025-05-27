package Commands

import WorkerClass.IOManager
import WorkerClass.WorkerManager

object Show {
    fun showCommand() {
        if (WorkerManager.collection.isEmpty()) {
            IOManager.printMessage("Коллекция пуста.")
        } else {
            IOManager.printMessage("Элементы коллекции:")
            WorkerManager.showAllWorkers()
        }
    }
}