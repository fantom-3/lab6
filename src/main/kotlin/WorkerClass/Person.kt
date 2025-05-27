package WorkerClass

import java.time.LocalDateTime

/**
 * Класс, представляющий человека с характеристиками.
 *
 * @property birthday дата и время рождения
 * @property eyeColor цвет глаз (может быть null)
 * @property hairColor цвет волос
 * @property nationality гражданство
 */
class Person(
    val birthday: LocalDateTime,
    val eyeColor: Color? = null,
    val hairColor: Color,
    val nationality: Country
    ) : Comparable<Person> {

    /**
     * Сравнивает людей по дате рождения.
     *
     * @param other другой объект [Person] для сравнения
     * @return отрицательное число, если текущий родился раньше; 0 — если в один день; положительное — если позже
     */
    override fun compareTo(other: Person): Int {
        return this.birthday.compareTo(other.birthday)
    }

    /**
     * Возвращает строковое представление объекта [Person].
     */
    override fun toString(): String {
        return "Person(birthday=$birthday, eyeColor=$eyeColor, hairColor=$hairColor, nationality=$nationality)"
    }
}