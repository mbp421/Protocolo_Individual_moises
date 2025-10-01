import kotlin.random.Random

// Juego del ahorcado

fun obtenerPalabraAleatoria(): String {
    val palabras = listOf("materia", "comida", "agua", "gato", "felicidad", "saltar", "futbol")
    return palabras[Random.nextInt(palabras.size)]
}

fun mostrarTablero(palabraSecreta: String, letrasAdivinadas: List<Char>) {
    val tablero = buildString {
        for (letra in palabraSecreta) {
            append(if (letra in letrasAdivinadas) letra else '_')
        }
    }
    println(tablero)
}

fun jugarAhorcado() {
    val palabraSecreta = obtenerPalabraAleatoria()
    val letrasAdivinadas = mutableListOf<Char>()
    var intentosRestantes = 10

    println("\n JUEGO DEL AHORCADO ")
    while (intentosRestantes > 0) {
        mostrarTablero(palabraSecreta, letrasAdivinadas)
        print("Introduce una letra: ")
        val input = readLine()?.lowercase() ?: ""
        if (input.isEmpty()) continue
        val letra = input[0]

        if (letra in letrasAdivinadas) {
            println("Ya has ingresado esta letra cachon, intenta con otraa.")
            continue
        }

        if (palabraSecreta.contains(letra)) {
            letrasAdivinadas.add(letra)
            if (palabraSecreta.toSet().all { it in letrasAdivinadas }) {
                println("¡Felicidades sirves pa algo! Adivinaste la palabra: $palabraSecreta")
                break
            }
        } else {
            intentosRestantes--
            println("Letra incorrecta cachon. Te quedan $intentosRestantes intentos.")
        }
    }

    if (intentosRestantes == 0) {
        println("Has perdido. No te dio el coco para adivinar esta palabra: $palabraSecreta")
    }
}

// Algoritmos de busqueda y ordenamiento

fun busquedaLineal(lista: List<Int>, clave: Int): Int {
    for (i in lista.indices) {
        if (lista[i] == clave) return i
    }
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

// Programa Principal

fun main() {
    // 1. Ejecutar juego del ahorcado
    jugarAhorcado()

    // 2. Probar algoritmos
    val datos = listOf(5, 3, 8, 1, 2)
    println("\nALGORITMOS DE BUSQUEDA Y ORDENAMIENTO ===")
    println("Lista original: $datos")
    println("Ordenamiento burbuja: ${burbuja(datos.toMutableList())}")
    println("Ordenamiento inserción: ${insercion(datos.toMutableList())}")

    val clave = 3
    val posLineal = busquedaLineal(datos, clave)
    println("Busqueda Lineal ($clave): posicion $posLineal")

    val ordenados = datos.sorted()
    val posBinaria = busquedaBinaria(ordenados, clave)
    println("Busqueda Binaria ($clave): posición $posBinaria en $ordenados")

    println("\nMetodos nativos de Kotlin ")
    val ordenadosNativo = datos.sorted()
    println("Ordenados con sorted(): $ordenadosNativo")
    val posNativo = ordenadosNativo.binarySearch(clave)
    println("Posicion de $clave con binarySearch: $posNativo en $ordenadosNativo")
}
