package Commands
import WorkerClass.IOManager
import  WorkerClass.WorkerManager

/**
 * Команда для очистки коллекции.
 * Удаляет все элементы из коллекции работников.
 */
object Clear {

    /**
     * Команда для очистки коллекции.
     * Удаляет все элементы из коллекции работников.
     */
    fun clearCommand() {
        if (WorkerManager.collection.isEmpty()) {
            IOManager.printMessage("Коллекция пуста.")
            return
        }

        val confirm = IOManager.readConfirmation("Вы уверены, что хотите очистить коллекцию?")
        if (confirm) {
            WorkerManager.clearCollection()
            IOManager.printMessage("Коллекция успешно очищена.")
        } else {
            IOManager.printMessage("Очистка отменена.")
        }
    }
}