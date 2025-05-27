package WorkerClass

import java.io.File
import java.io.PrintWriter
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*

/**
 * Менеджер ввода-вывода для консольного приложения.
 * Обеспечивает стандартизированный ввод и вывод данных.
 */
object IOManager {
    private var currentScanner: Scanner = Scanner(System.`in`)
    private val out = System.out
    private val scannerStack = Stack<Scanner>()
    private var isScriptMode = false

    fun setScanner(file: File) {
        scannerStack.push(currentScanner)
        currentScanner = Scanner(file)
        isScriptMode = true
    }

    fun restoreScanner() {
        currentScanner.close()
        if (scannerStack.isNotEmpty()) {
            currentScanner = scannerStack.pop()
        }
        isScriptMode = false
    }

    fun inScriptMode(): Boolean = isScriptMode

    /**
     * Читает следующую строку ввода
     */
    fun readLine(): String {
        return currentScanner.nextLine()
    }

    /**
     * Проверяет наличие следующей строки
     */
    fun hasNextLine(): Boolean {
        return currentScanner.hasNextLine()
    }

    /**
     * Выводит сообщение пользователю
     * @param message Текст сообщения
     */
    fun printMessage(message: String) {
        out.println(message)
    }

    /**
     * Выводит сообщение об ошибке
     * @param error Текст ошибки
     */
    fun printError(error: String) {
        System.err.println("ОШИБКА: $error")
    }

    /**
     * Выводит приглашение к вводу для указанного поля
     * @param fieldName Название поля
     * @param currentValue Текущее значение (для команд обновления)
     * @param isRequired Обязательное ли поле
     */
    private fun printPrompt(fieldName: String, currentValue: Any? = null, isRequired: Boolean = true) {
        if (isScriptMode) return

        val prompt = buildString {
            append("Введите $fieldName")
            if (currentValue != null) {
                append(" (текущее: $currentValue)")
            }
            if (!isRequired) {
                append(" (необязательное поле)")
            }
            append(":")
        }
        print("> $prompt ")
        System.out.flush()
    }

    /**
     * Читает строку с валидацией
     * @param fieldName Название поля для сообщений
     * @param validator Функция валидации
     * @param currentValue Текущее значение (для update)
     * @param isRequired Обязательное ли поле
     * @return Введенное значение или null если поле необязательное и ввод пустой
     */
    fun readString(
        fieldName: String,
        validator: (String) -> Boolean = { true },
        currentValue: String? = null,
        isRequired: Boolean = true
    ): String? {
        while (true) {
            printPrompt(fieldName, currentValue, isRequired)
            val input = readLine().trim()

            when {
                input.isEmpty() && !isRequired -> return null
                input.isEmpty() -> printError("Поле не может быть пустым")
                !validator(input) -> printError("Некорректный формат данных")
                else -> return input
            }
        }
    }

    /**
     * Читает число типа Long с валидацией
     * @param fieldName Название поля для сообщений
     * @param validator Функция валидации
     * @param currentValue Текущее значение (для update)
     * @param isRequired Обязательное ли поле
     * @return Введенное значение или null если поле необязательное и ввод пустой
     */
    fun readLong(
        fieldName: String,
        validator: (Long) -> Boolean = { true },
        currentValue: Long? = null,
        isRequired: Boolean = true
    ): Long? {
        while (true) {
            printPrompt(fieldName, currentValue, isRequired)
            val input = readLine().trim()

            if (input.isEmpty() && !isRequired) return null
            if (input.isEmpty()) {
                printError("Поле не может быть пустым")
                continue
            }

            try {
                val value = input.toLong()
                if (!validator(value)) {
                    printError("Некорректное значение")
                    continue
                }
                return value
            } catch (e: NumberFormatException) {
                printError("Введите целое число")
            }
        }
    }

    /**
     * Читает число типа Double с валидацией
     * @param fieldName Название поля для сообщений
     * @param validator Функция валидации
     * @param currentValue Текущее значение (для update)
     * @param isRequired Обязательное ли поле
     * @return Введенное значение или null если поле необязательное и ввод пустой
     */
    fun readDouble(
        fieldName: String,
        validator: (Double) -> Boolean = { true },
        currentValue: Double? = null,
        isRequired: Boolean = true
    ): Double? {
        while (true) {
            printPrompt(fieldName, currentValue, isRequired)
            val input = readLine().trim()

            if (input.isEmpty() && !isRequired) return null
            if (input.isEmpty()) {
                printError("Поле не может быть пустым")
                continue
            }

            try {
                val value = input.toDouble()
                if (!validator(value)) {
                    printError("Некорректное значение")
                    continue
                }
                return value
            } catch (e: NumberFormatException) {
                printError("Введите число")
            }
        }
    }

    /**
     * Читает значение enum с валидацией
     * @param fieldName Название поля для сообщений
     * @param enumValues Доступные значения enum
     * @param currentValue Текущее значение (для update)
     * @param isRequired Обязательное ли поле
     * @return Введенное значение или null если поле необязательное и ввод пустой
     */
    fun <T : Enum<T>> readEnum(
        fieldName: String,
        enumValues: Array<T>,
        currentValue: T? = null,
        isRequired: Boolean = true
    ): T? {
        while (true) {
            val valuesList = enumValues.joinToString { it.name }
            printPrompt("$fieldName ($valuesList)", currentValue, isRequired)
            val input = readLine().trim().uppercase()

            if (input.isEmpty() && !isRequired) return null
            if (input.isEmpty()) {
                printError("Поле не может быть пустым")
                continue
            }

            try {
                return enumValues.first { it.name == input }
            } catch (e: NoSuchElementException) {
                printError("Допустимые значения: $valuesList")
            }
        }
    }

    /**
     * Читает дату и время в формате LocalDateTime
     * @param fieldName Название поля для сообщений
     * @param currentValue Текущее значение (для update)
     * @param isRequired Обязательное ли поле
     * @return Введенное значение или null если поле необязательное и ввод пустой
     */
    fun readLocalDateTime(
        fieldName: String,
        currentValue: LocalDateTime? = null,
        isRequired: Boolean = true
    ): LocalDateTime? {
        while (true) {
            printPrompt("$fieldName (формат: ГГГГ-ММ-ДДTЧЧ:ММ:СС)", currentValue, isRequired)
            val input = readLine().trim()

            if (input.isEmpty() && !isRequired) return null
            if (input.isEmpty()) {
                printError("Поле не может быть пустым")
                continue
            }

            try {
                return LocalDateTime.parse(input)
            } catch (e: Exception) {
                printError("Введите дату и время в формате ГГГГ-ММ-ДДTЧЧ:ММ:СС")
            }
        }
    }

    /**
     * Читает дату и время с часовым поясом в формате ZonedDateTime
     * @param fieldName Название поля для сообщений
     * @param currentValue Текущее значение (для update)
     * @param isRequired Обязательное ли поле
     * @return Введенное значение или null если поле необязательное и ввод пустой
     */
    fun readZonedDateTime(
        fieldName: String,
        currentValue: ZonedDateTime? = null,
        isRequired: Boolean = true
    ): ZonedDateTime? {
        while (true) {
            printPrompt("$fieldName (формат: ГГГГ-ММ-ДДTЧЧ:ММ:СС+Зона)", currentValue, isRequired)
            val input = readLine().trim()

            if (input.isEmpty() && !isRequired) return null
            if (input.isEmpty()) {
                printError("Поле не может быть пустым")
                continue
            }

            try {
                return ZonedDateTime.parse(input)
            } catch (e: Exception) {
                printError("Введите дату и время с часовым поясом в формате ГГГГ-ММ-ДДTЧЧ:ММ:СС+Зона")
            }
        }
    }

    /**
     * Читает подтверждение действия (да/нет)
     * @param question Вопрос для подтверждения
     * @return true если пользователь подтвердил действие
     */
    fun readConfirmation(question: String): Boolean {
        while (true) {
            print("> $question (да/нет): ")
            val input = readLine().trim().lowercase()

            when (input) {
                "да", "д", "yes", "y" -> return true
                "нет", "н", "no", "n" -> return false
                else -> printError("Введите 'да' или 'нет'")
            }
        }
    }
}