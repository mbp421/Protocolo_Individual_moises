import random
import bisect

# Juego del ahorcado

def obtener_palabra_aleatoria():
    palabras = ["materia", "comida", "agua", "gato", "felicidad", "saltar", "futbol"]
    palabra_aleatoria = random.choice(palabras)
    return palabra_aleatoria

def mostrar_tablero(palabra_secreta, letras_adivinadas):
    tablero = ""
    for letra in palabra_secreta:
        if letra in letras_adivinadas:
            tablero += letra
        else:
            tablero += "_"
    print(tablero)

def jugar_ahorcado():
    palabra_secreta = obtener_palabra_aleatoria()
    letras_adivinadas = []
    intentos_restantes = 10

    print("\n JUEGO DEL AHORCADO ")
    while intentos_restantes > 0:
        mostrar_tablero(palabra_secreta, letras_adivinadas)
        letra = input("Escribe una letra: ").lower()

        if letra in letras_adivinadas:
            print("Ya has ingresado esta letra cachon, intenta con otra.")
            continue

        if letra in palabra_secreta:
            letras_adivinadas.append(letra)
            if set(palabra_secreta).issubset(set(letras_adivinadas)):
                print(f"¡Felicidades sirves pa algo! Adivinaste la palabra: {palabra_secreta}")
                break
        else:
            intentos_restantes -= 1
            print(f"Te equibocaste de letra cachon. Te quedan {intentos_restantes} intentos.")

    if intentos_restantes == 0:
        print(f"Has perdido. No te dio el coco para adivinar esta palabra: {palabra_secreta}")


# Algoritmos de busqueda y ordenamiento

def busqueda_lineal(lista, clave):
    for i, valor in enumerate(lista):
        if valor == clave:
            return i
    return -1

def busqueda_binaria(lista, clave):
    inicio, fin = 0, len(lista) - 1
    while inicio <= fin:
        medio = (inicio + fin) // 2
        if lista[medio] == clave:
            return medio
        elif lista[medio] < clave:
            inicio = medio + 1
        else:
            fin = medio - 1
    return -1

def burbuja(lista):
    n = len(lista)
    lista = lista.copy()
    for i in range(n):
        for j in range(0, n - i - 1):
            if lista[j] > lista[j + 1]:
                lista[j], lista[j + 1] = lista[j + 1], lista[j]
    return lista

def insercion(lista):
    lista = lista.copy()
    for i in range(1, len(lista)):
        clave = lista[i]
        j = i - 1
        while j >= 0 and lista[j] > clave:
            lista[j + 1] = lista[j]
            j -= 1
        lista[j + 1] = clave
    return lista

# Programa Principal

if __name__ == "__main__":
    # 1. Ejecutar juego del ahorcado
    jugar_ahorcado()

    # 2. Probar algoritmos
    datos = [5, 3, 8, 1, 2]
    print("\n ALGORITMOS DE BUSQUEDA Y ORDENAMIENTO ")
    print("Lista original:", datos)
    print("Burbuja:", burbuja(datos))
    print("Insercion:", insercion(datos))

    clave = 3
    pos_lineal = busqueda_lineal(datos, clave)
    print(f"Busqueda lineal ({clave}): posición {pos_lineal}")

    ordenados = sorted(datos)
    pos_binaria = busqueda_binaria(ordenados, clave)
    print(f"Busqueda binaria ({clave}): posicion {pos_binaria} en {ordenados}")

    print("\n Metodos nativos de Python ")
    ordenados_nativo = sorted(datos)
    print("Ordenados con sorted():", ordenados_nativo)
    pos_bisect = bisect.bisect_left(ordenados_nativo, clave)
    print(f"Posicion de {clave} con bisect: {pos_bisect} en {ordenados_nativo}")
