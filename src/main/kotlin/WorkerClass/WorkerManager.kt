package WorkerClass

import java.util.*

/**
 * Объект управляет коллекцией работников.
 * Хранит данные в виде LinkedList.
 */
object WorkerManager {
    // Коллекция работников, хранимая в LinkedList
    var collection = LinkedList<Worker>()

    /**
     * Добавляет работника в коллекцию.
     *
     * @param worker Работник для добавления.
     */
    fun addWorker(worker: Worker) {
        collection.add(worker)
    }


    /**
     * Выводит информацию о всех работниках в коллекции.
     *
     * Этот метод перебирает всех работников в коллекции и выводит их данные в консоль. Для каждого работника выводится
     * информация о его ID, имени, координатах, зарплате, дате начала и окончания работы, должности, а также личные данные.
     */
    fun showAllWorkers() {
        collection.forEach { worker ->
            println(
                """
                |ID: ${worker.id}
                |Имя: ${worker.name}
                |Координаты: (x=${worker.coordinates.x}, y=${worker.coordinates.y})
                |Дата создания: ${worker.creationDate}
                |Зарплата: ${worker.salary}
                |Дата начала работы: ${worker.startDate}
                |Дата окончания работы: ${worker.endDate ?: "не указана"}
                |Должность: ${worker.position ?: "не указана"}
                |Личные данные:
                |  Дата рождения: ${worker.person.birthday}
                |  Цвет глаз: ${worker.person.eyeColor ?: "не указан"}
                |  Цвет волос: ${worker.person.hairColor}
                |  Национальность: ${worker.person.nationality}
                |-------------------------------------
                """.trimMargin()
            )
        }
    }

    /**
     * Получает информацию о первом работнике в коллекции.
     *
     * Этот метод выводит информацию о первом работнике в коллекции, если он существует. В противном случае возвращает
     * строку с указанием, что работник не найден.
     *
     * @param worker Работник, который будет отображен как первый элемент коллекции.
     * @return Строка с детальной информацией о первом работнике.
     */
    fun firstWorker(worker: Worker): String {
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

    /**
     * Очищает коллекцию работников.
     *
     * Этот метод очищает всю коллекцию работников, удаляя все элементы из списка.
     */
    fun clearCollection() {
        collection.clear()
    }
}