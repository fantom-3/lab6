import Commands.CommandRegistry
import WorkerClass.IOManager
import WorkerClass.XmlLoader
import java.io.File

fun main() {
    val defaultFile = "workers.xml"
    if (File(defaultFile).exists()) {
        XmlLoader.loadFromXml(defaultFile)
    }

    IOManager.printMessage("Программа готова к работе. Введите 'help' для списка команд.")

    while (!Commands.Exit.shouldExit()) {
        IOManager.printMessage("> ")
        val userInput = IOManager.readLine().trim()
        if (userInput.isEmpty()) continue

        CommandRegistry.executeCommand(userInput)
    }
}