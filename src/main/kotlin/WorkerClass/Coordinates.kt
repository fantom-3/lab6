package WorkerClass

/**
 * Класс, представляющий координаты на плоскости.
 *
 * @property x координата по оси X
 * @property y координата по оси Y
 */
class Coordinates(
    val x: Float, val y: Long
) {
    /**
     * Возвращает строковое представление координат.
     */
    override fun toString(): String {
        return "Coordinates(x=$x, y=$y)"
    }
}