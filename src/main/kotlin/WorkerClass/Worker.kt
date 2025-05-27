package WorkerClass

import Utils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime

/**
 * Класс, представляющий работника.
 *
 * Этот класс используется для представления работника с его основными атрибутами, такими как ID, имя, координаты, зарплата,
 * дата начала и окончания работы, а также личные данные и должность. Класс реализует интерфейс Comparable, чтобы позволить
 * сравнивать работников по ID.
 *
 * @property id Уникальный идентификатор работника. Значение генерируется с помощью утилитного метода `Utils.generateId()`.
 * @property name Имя работника.
 * @property coordinates Координаты работника, которые представляют собой объект класса `Coordinates`.
 * @property creationDate Дата создания записи о работнике. Значение по умолчанию генерируется с помощью `Utils.generateCreationDate()`.
 * @property salary Зарплата работника.
 * @property startDate Дата начала работы работника, которая генерируется с помощью `Utils.generateStartDate()`. Может быть изменена.
 * @property endDate Дата окончания работы работника. Может быть `null`, если работник все еще работает.
 * @property position Должность работника. Может быть `null`, если должность не указана.
 * @property person Личные данные работника. Это объект класса `Person`, содержащий информацию о дате рождения, цвете волос и национальности.
 */
class Worker(
    var id: Int = Utils.generateId(),
    var name: String,
    var coordinates: Coordinates,
    var creationDate: LocalDate = Utils.generateCreationDate(),
    var salary: Long,
    var startDate: ZonedDateTime? = null,
    var endDate: LocalDateTime?,
    var position: Position?,
    var person: Person
) : Comparable<Worker> {

    /**
     * Сравнивает работника с другим работником по их ID.
     *
     * Этот метод используется для сравнения двух объектов класса `Worker`. Сравнение происходит по полю ID, что позволяет сортировать
     * работников по уникальному идентификатору.
     *
     * @param other Другой работник, с которым будет происходить сравнение.
     * @return Отрицательное число, если ID текущего работника меньше, 0, если ID равны, положительное число, если ID больше.
     */
    override fun compareTo(other: Worker): Int {
        return id.compareTo(other.id)
    }

    override fun toString(): String {
        return """
        ID: $id
        Имя: $name
        Координаты: (x=${coordinates.x}, y=${coordinates.y})
        Дата создания: $creationDate
        Зарплата: $salary
        Дата начала работы: $startDate
        Дата окончания: ${endDate ?: "не указана"}
        Должность: ${position ?: "не указана"}
        Личные данные:
          Дата рождения: ${person.birthday}
          Цвет глаз: ${person.eyeColor ?: "не указан"}
          Цвет волос: ${person.hairColor}
          Национальность: ${person.nationality}
    """.trimIndent()
    }

}