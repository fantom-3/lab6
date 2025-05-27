package Commands

import WorkerClass.*

object Head {
    fun headCommand() {
        if (WorkerManager.collection.isEmpty()) {
            IOManager.printMessage("Коллекция пуста.")
        } else {
            IOManager.printMessage(WorkerManager.firstWorker(WorkerManager.collection.first()))
        }
    }
}