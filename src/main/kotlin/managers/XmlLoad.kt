package WorkerClass

import Utils
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Объект для загрузки данных о работниках из XML-файла
 */
object XmlLoader {
    /**
     * Загружает коллекцию работников из XML-файла
     * @param filename Имя XML-файла
     */
    fun loadFromXml(filename: String) {
        try {
            val file = File(filename)
            if (!file.exists()) {
                IOManager.printError("Файл $filename не найден")
                return
            }

            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val doc = dBuilder.parse(file)
            doc.documentElement.normalize()

            val workersNodes = doc.getElementsByTagName("worker")
            for (i in 0 until workersNodes.length) {
                val node = workersNodes.item(i)
                if (node.nodeType == Node.ELEMENT_NODE) {
                    val workerElement = node as Element
                    try {
                        val worker = parseWorkerElement(workerElement)
                        WorkerManager.addWorker(worker)
                    } catch (e: Exception) {
                        IOManager.printError("Ошибка парсинга работника: ${e.message}")
                    }
                }
            }
            Utils.xmlID(WorkerManager.collection.size)
            IOManager.printMessage("Загружено ${WorkerManager.collection.size} работников из $filename")
        } catch (e: Exception) {
            IOManager.printError("Ошибка загрузки XML: ${e.message}")
        }
    }

    /**
     * Преобразует XML-элемент в объект Worker
     * @param workerElement XML-элемент <worker>
     * @return Объект Worker
     * @throws Exception если структура некорректна
     */
    private fun parseWorkerElement(workerElement: Element): Worker {
        val id = workerElement.getTextContent("id").toInt()
        val name = workerElement.getTextContent("name")

        /**
         * Парсит координаты из XML-элемента
         * @param coordinatesElement XML-элемент <coordinates>
         * @return Объект Coordinates
         */
        val coordinates = parseCoordinates(
            workerElement.getElementsByTagName("coordinates").item(0) as Element
        )

        val creationDate = LocalDate.parse(workerElement.getTextContent("creationDate"))
        val salary = workerElement.getTextContent("salary").toLong()

        val startDate = workerElement.getTextContent("startDate").let {
            if (it.isNotEmpty()) ZonedDateTime.parse(it) else null
        }

        val endDate = workerElement.getTextContent("endDate").let {
            if (it.isNotEmpty()) LocalDateTime.parse(it) else null
        }

        val position = workerElement.getTextContent("position").let {
            if (it.isNotEmpty()) Position.valueOf(it) else null
        }

        /**
         * Парсит объект Person из XML-элемента
         * @param personElement XML-элемент <person>
         * @return Объект Person
         */
        val person = parsePerson(
            workerElement.getElementsByTagName("person").item(0) as Element
        )

        return Worker(
            id = id,
            name = name,
            coordinates = coordinates,
            creationDate = creationDate,
            salary = salary,
            startDate = startDate,
            endDate = endDate,
            position = position,
            person = person
        )
    }

    private fun parseCoordinates(coordinatesElement: Element): Coordinates {
        val x = coordinatesElement.getTextContent("x").toFloat()
        val y = coordinatesElement.getTextContent("y").toLong()
        return Coordinates(x, y)
    }

    private fun parsePerson(personElement: Element): Person {
        val birthday = LocalDateTime.parse(personElement.getTextContent("birthday"))

        val eyeColor = personElement.getTextContent("eyeColor").let {
            if (it.isNotEmpty()) Color.valueOf(it) else null
        }

        val hairColor = Color.valueOf(personElement.getTextContent("hairColor"))
        val nationality = Country.valueOf(personElement.getTextContent("nationality"))

        return Person(birthday, eyeColor, hairColor, nationality)
    }

    /**
     * Получает текстовое содержимое первого дочернего тега с заданным именем
     * @param tagName Имя тега
     * @return Текстовое содержимое
     */
    private fun Element.getTextContent(tagName: String): String {
        return getElementsByTagName(tagName).item(0).textContent
    }
}