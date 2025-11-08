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

