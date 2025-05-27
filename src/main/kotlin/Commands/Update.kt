package Commands

import WorkerClass.*
import java.time.LocalDateTime
import java.time.ZonedDateTime

object Update {
    fun updateCommand(id: Int) {
        val worker = WorkerManager.collection.find { it.id == id }
            ?: run {
                IOManager.printError("Работник с ID $id не найден.")
                return
            }

        if (!IOManager.inScriptMode()) {
            IOManager.printMessage("Обновление работника с ID $id. Оставьте поле пустым, чтобы сохранить текущее значение.")
        }


        worker.name = IOManager.readString(
            fieldName = "имя",
            validator = { it.isNotEmpty() },
            currentValue = worker.name,
            isRequired = true
        ) ?: worker.name

        worker.coordinates = updateCoordinates(worker.coordinates) ?: worker.coordinates
        worker.salary = readValidSalary(worker.salary)
        worker.startDate = readValidStartDate(worker.startDate)
        worker.endDate = readValidEndDate(worker.endDate)
        worker.position = readValidPosition(worker.position)
        worker.person = updatePerson(worker.person) ?: worker.person

        if (IOManager.inScriptMode()) {
            IOManager.printMessage("Работник с ID $id обновлён из скрипта:")
            IOManager.printMessage(worker.toString())
        } else {
            IOManager.printMessage("Работник с ID $id успешно обновлен.")
        }
    }

    private fun updateCoordinates(current: Coordinates): Coordinates? {
        IOManager.printMessage("Текущие координаты: x=${current.x}, y=${current.y}")
        val x = IOManager.readLong(
            fieldName = "координата x (≤42)",
            validator = { it <= 42 },
            currentValue = current.x.toLong(),
            isRequired = false
        )?.toFloat() ?: return null

        val y = IOManager.readDouble(
            fieldName = "координата y (≤431)",
            validator = { it <= 431 },
            currentValue = current.y.toDouble(),
            isRequired = false
        )?.toLong() ?: return null

        return Coordinates(x, y)
    }

    private fun readValidSalary(current: Long): Long {
        return IOManager.readLong(
            fieldName = "зарплата (>0)",
            validator = { it > 0 },
            currentValue = current,
            isRequired = false
        ) ?: current
    }

    private fun readValidStartDate(current: ZonedDateTime?): ZonedDateTime? {
        return IOManager.readZonedDateTime(
            fieldName = "дата начала работы",
            currentValue = current,
            isRequired = false
        ) ?: current
    }

    private fun readValidEndDate(current: LocalDateTime?): LocalDateTime? {
        return IOManager.readLocalDateTime(
            fieldName = "дата окончания работы",
            currentValue = current,
            isRequired = true
        ) ?: current
    }

    private fun readValidPosition(current: Position?): Position? {
        return IOManager.readEnum(
            fieldName = "должность",
            enumValues = Position.values(),
            currentValue = current,
            isRequired = false
        ) ?: current
    }

    private fun updatePerson(current: Person): Person? {
        if (!IOManager.inScriptMode()) {
            IOManager.printMessage("Обновление личных данных:")
        }


        val birthday = IOManager.readLocalDateTime(
            fieldName = "дата рождения",
            currentValue = current.birthday,
            isRequired = false
        ) ?: current.birthday

        val eyeColor = IOManager.readEnum(
            fieldName = "цвет глаз",
            enumValues = Color.values(),
            currentValue = current.eyeColor,
            isRequired = false
        )

        val hairColor = IOManager.readEnum(
            fieldName = "цвет волос",
            enumValues = Color.values(),
            currentValue = current.hairColor,
            isRequired = false
        ) ?: current.hairColor

        val nationality = IOManager.readEnum(
            fieldName = "национальность",
            enumValues = Country.values(),
            currentValue = current.nationality,
            isRequired = false
        ) ?: current.nationality

        return Person(birthday, eyeColor, hairColor, nationality)
    }
}