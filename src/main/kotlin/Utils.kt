import java.time.LocalDate
import java.time.ZonedDateTime


/**
 * Объект, предоставляющий методы для генерации уникальных идентификаторов, даты создания и даты начала работы.
 * Этот объект используется для генерации значений, которые могут быть полезны при создании работников или других объектов.
 */
object Utils {

    // Хранит следующее доступное значение ID
    private var nextId: Int = 1

    /**
     * Генерирует уникальный идентификатор.
     * Каждый раз, когда вызывается этот метод, значение `nextId` увеличивается на 1 и возвращается.
     *
     * @return Следующий уникальный идентификатор.
     */
    fun generateId(): Int {
        return nextId++
    }

    /**
     * Генерирует текущую дату.
     * Этот метод возвращает текущую дату в формате `LocalDate`, используя системное время.
     *
     * @return Текущая дата.
     */
    fun generateCreationDate(): LocalDate {
        return LocalDate.now()
    }

    /**
     * Генерирует текущую дату и время в формате `ZonedDateTime`.
     * Этот метод возвращает текущую дату и время с учетом часового пояса.
     *
     * @return Текущая дата и время с часовым поясом.
     */
    fun generateStartDate(): ZonedDateTime {
        return ZonedDateTime.now()
    }

    fun xmlID(arg: Int) :Int {
        nextId = arg+1
        return nextId
    }
}