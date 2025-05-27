package Commands

import WorkerClass.*

object FilterLessThanPosition {
    fun filterLess_positionCommand(positionInp: String) {
        val targetPosition = try {
            Position.valueOf(positionInp.uppercase())
        } catch (e: IllegalArgumentException) {
            IOManager.printError("Недопустимая позиция: $positionInp")
            IOManager.printMessage("Допустимые позиции: ${Position.values().joinToString()}")
            return
        }

        val filteredWorkers = WorkerManager.collection
            .filter { worker -> worker.position != null && worker.position!! < targetPosition }

        if (filteredWorkers.isEmpty()) {
            IOManager.printMessage("Не найдено элементов с позицией меньше $targetPosition")
        } else {
            IOManager.printMessage("Элементы с позицией меньше $targetPosition (${filteredWorkers.size} шт.):")
            filteredWorkers.forEach { worker ->
                IOManager.printMessage(formatWorker(worker))
            }
        }
    }

    private fun formatWorker(worker: Worker): String {
        return """
            ID: ${worker.id}
            Имя: ${worker.name}
            Координаты: (x=${worker.coordinates.x}, y=${worker.coordinates.y})
            Дата создания: ${worker.creationDate}
            Зарплата: ${worker.salary}
            Дата начала работы: ${worker.startDate}
            Дата окончания: ${worker.endDate ?: "не указана"}
            Должность: ${worker.position ?: "не указана"}
            Личные данные:
              Дата рождения: ${worker.person.birthday}
              Цвет глаз: ${worker.person.eyeColor ?: "не указан"}
              Цвет волос: ${worker.person.hairColor}
              Национальность: ${worker.person.nationality}
            -------------------------------------
        """.trimIndent()
    }
}