package Commands

import WorkerClass.WorkerManager
import WorkerClass.Worker

/**
 * Команда для фильтрации элементов по началу имени
 */
object FilterStartsWithName {

    /**
     * Выполняется фильтрация и вывод элементов, чье имя начинается с заданной подстроки
     * @param prefix Подстрока для поиска в начале имени
     */
    fun filterStarts_nameCommand(prefix: String) {
        val filtredWorkers = WorkerManager.collection.filter { worker -> worker.name.startsWith(prefix) }
        when {
            filtredWorkers.isEmpty() -> println("Не найдено элементов с именем, начинающимся на $prefix")
            else -> {
                println("Найдено ${filtredWorkers.size} элементов:")
                filtredWorkers.forEach {worker -> println(format_posWorker(worker))}
            }
        }
    }

    private fun format_posWorker(worker: Worker): String {
        return """
            |Первый элемент коллекции:
            |ID: ${worker.id}
            |Имя: ${worker.name}
            |Координаты: (x=${worker.coordinates.x}, y=${worker.coordinates.y})
            |Дата создания: ${worker.creationDate}
            |Зарплата: ${worker.salary}
            |Дата начала работы: ${worker.startDate}
            |Дата окончания: ${worker.endDate ?: "не указана"}
            |Должность: ${worker.position ?: "не указана"}
            |Личные данные:
            |   Дата рождения: ${worker.person.birthday}
            |   Цвет глаз: ${worker.person.eyeColor ?: "не указан"}
            |   Цвет волос: ${worker.person.hairColor}
            |   Национальность: ${worker.person.nationality}
        """.trimMargin()
    }
}