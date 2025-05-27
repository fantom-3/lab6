package Commands

import WorkerClass.IOManager

object Exit {
    private var shouldExit = false

    /**
     * Устанавливает флаг для мягкого выхода из программы
     */
    fun exitCommand() {
        val confirm = IOManager.readConfirmation("Вы уверены, что хотите выйти без сохранения?")
        if (confirm) {
            shouldExit = true
            IOManager.printMessage("Завершение программы...")
        } else {
            IOManager.printMessage("Выход отменен.")
        }
    }

    /**
     * Проверяет, был ли запрошен выход
     */
    fun shouldExit(): Boolean = shouldExit
}