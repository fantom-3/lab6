package Commands

import WorkerClass.IOManager
import java.io.File
import java.util.*

object Execute {
    private val scriptStack = Stack<String>()  // Отслеживание рекурсии

    fun executeCommand(fileName: String) {
        val file = File(fileName)
        if (!file.exists()) {
            IOManager.printError("Файл '$fileName' не найден.")
            return
        }

        if (scriptStack.contains(fileName)) {
            IOManager.printError("Рекурсивное выполнение '$fileName' запрещено.")
            return
        }

        try {
            scriptStack.push(fileName)
            IOManager.setScanner(file)
            IOManager.printMessage("Выполнение скрипта из '$fileName'...")

            while (IOManager.hasNextLine()) {
                val line = IOManager.readLine().trim()
                if (line.isEmpty()) continue

                IOManager.printMessage(">> $line")
                CommandRegistry.executeCommand(line)
            }

            IOManager.printMessage("Скрипт '$fileName' завершён.")
        } catch (e: Exception) {
            IOManager.printError("Ошибка при выполнении скрипта: ${e.message}")
        } finally {
            IOManager.restoreScanner()
            scriptStack.pop()
        }
    }
}