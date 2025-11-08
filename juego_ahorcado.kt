import kotlin.random.Random

// # Primer punto: implementacion de clases manual 

class EmptyStackException(message: String) : Exception(message)

class StackManual<T> {
    private val items = mutableListOf<T>()

    fun push(value: T) = items.add(value)

    fun pop(): T {
        if (isEmpty()) throw EmptyStackException("La pila está vacía (no hay jugadas para deshacer).")
        return items.removeAt(items.size - 1)
    }

    fun peek(): T {
        if (isEmpty()) throw EmptyStackException("La pila está vacía.")
        return items.last()
    }

    fun isEmpty() = items.isEmpty()

    fun size() = items.size

    fun clear() = items.clear()
}


class EmptyQueueException(message: String) : Exception(message)

class QueueManual<T> {
    private val items = mutableListOf<T>()

    fun enqueue(value: T) = items.add(value)

    fun dequeue(): T {
        if (isEmpty()) throw EmptyQueueException("La cola está vacía.")
        return items.removeAt(0)
    }

    fun front(): T {
        if (isEmpty()) throw EmptyQueueException("La cola está vacía.")
        return items[0]
    }

    fun isEmpty() = items.isEmpty()

    fun size() = items.size

    fun clear() = items.clear()
}


// Imporporacion de colas y pilas al juego ahorcado


fun obtenerPalabraAleatoria(): String {
    val palabras = listOf("materia", "comida", "agua", "gato", "felicidad", "saltar", "futbol")
    return palabras[Random.nextInt(palabras.size)]
}

fun mostrarTablero(palabraSecreta: String, letrasAdivinadas: List<Char>) {
    val tablero = buildString {
        for (letra in palabraSecreta) append(if (letra in letrasAdivinadas) letra else '_')
    }
    println(tablero)
}

fun jugarAhorcado() {
    val palabraSecreta = obtenerPalabraAleatoria()
    val letrasAdivinadas = mutableListOf<Char>()
    val pilaUndo = StackManual<Char>()        // Pila para deshacer jugadas
    val colaJugadores = QueueManual<String>() // Cola para turnos
    colaJugadores.enqueue("Jugador 1")
    colaJugadores.enqueue("Jugador 2")

    var intentosRestantes = 10

    println("\n=== JUEGO DEL AHORCADO ===")
    println("Comando adicional: escribe UNDO para deshacer la última letra ingresada.")

    while (intentosRestantes > 0) {
        val jugadorActual = colaJugadores.dequeue()
        colaJugadores.enqueue(jugadorActual)

        println("\nTurno de: $jugadorActual")
        mostrarTablero(palabraSecreta, letrasAdivinadas)

        print("Pila pues! introduce una letra (o UNDO): ")
        val input = readLine()?.lowercase() ?: ""
        if (input.isEmpty()) continue

        if (input == "undo") {
            try {
                val ultima = pilaUndo.pop()
                letrasAdivinadas.remove(ultima)
                println("Se deshizo la letra: $ultima")
            } catch (e: Exception) {
                println(e.message)
            }
            continue
        }

        val letra = input[0]

        if (letra in letrasAdivinadas) {
            println("Ya habías ingresado esta letra cachon!.")
            continue
        }

        pilaUndo.push(letra)

        if (palabraSecreta.contains(letra)) {
            letrasAdivinadas.add(letra)
            if (palabraSecreta.toSet().all { it in letrasAdivinadas }) {
                println("¡Tu si sirves Ganaste! La palabra era: $palabraSecreta")
                return
            }
        } else {
            intentosRestantes--
            println("Esa no era cachon. Te quedan $intentosRestantes intentos.")
        }
    }

    println("No te dio el coco. La palabra era: $palabraSecreta")
}


// Segunta parte: version con codigos nativos

fun pilaNativaDemo() {
    val pila = ArrayDeque<Char>()
    pila.addLast('a')
    pila.addLast('b')
    pila.addLast('c')
    println("\nPila nativa: $pila")
    println("Pop (removeLast): ${pila.removeLast()}")
    println("Después: $pila")
}

fun colaNativaDemo() {
    val cola = ArrayDeque<String>()
    cola.addLast("Jugador 1")
    cola.addLast("Jugador 2")
    println("\nCola nativa: $cola")
    println("Dequeue (removeFirst): ${cola.removeFirst()}")
    println("Después: $cola")
}



// ALGORITMOS (SIN CAMBIOS)


fun busquedaLineal(lista: List<Int>, clave: Int): Int {
    for (i in lista.indices) if (lista[i] == clave) return i
    return -1
}

fun busquedaBinaria(lista: List<Int>, clave: Int): Int {
    var inicio = 0
    var fin = lista.size - 1
    while (inicio <= fin) {
        val medio = (inicio + fin) / 2
        when {
            lista[medio] == clave -> return medio
            lista[medio] < clave -> inicio = medio + 1
            else -> fin = medio - 1
        }
    }
    return -1
}

fun burbuja(lista: MutableList<Int>): List<Int> {
    val n = lista.size
    for (i in 0 until n - 1) {
        for (j in 0 until n - i - 1) {
            if (lista[j] > lista[j + 1]) {
                val temp = lista[j]
                lista[j] = lista[j + 1]
                lista[j + 1] = temp
            }
        }
    }
    return lista
}

fun insercion(lista: MutableList<Int>): List<Int> {
    for (i in 1 until lista.size) {
        val clave = lista[i]
        var j = i - 1
        while (j >= 0 && lista[j] > clave) {
            lista[j + 1] = lista[j]
            j--
        }
        lista[j + 1] = clave
    }
    return lista
}



// Ejecutar el codigo completo 



fun main() {
    jugarAhorcado()

    println("\n=== VERSIONES NATIVAS ===")
    pilaNativaDemo()
    colaNativaDemo()
}
