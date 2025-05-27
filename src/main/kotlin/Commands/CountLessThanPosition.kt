package Commands

import WorkerClass.Position
import WorkerClass.WorkerManager

/**
 * Команда для подсчета элементов с position меньше заданной
 */
object CountLessThanPosition {

    /**
     * Выполняет подсчет элементов с position меньше заданной
     * @param positionInp Название позиции для сравнения
     */
    fun countLess_positionCommand(positionInp: String) {
        val targetPosition = try {
            Position.valueOf(positionInp.uppercase())
        } catch (e: IllegalArgumentException) {
            println("Ошибка: $positionInp - недопустимая позиция")
            println("Допустимые позиции: ${Position.values().joinToString()}")
            return
        }

        val count = WorkerManager.collection.filter { it.position != null }.count{ it.position!! < targetPosition }

        println("Элементов с позицией меньше $targetPosition: $count")
    }
}