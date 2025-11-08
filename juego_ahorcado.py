import random
from collections import deque

# Primer punto: IMPLEMENTACIÓN MANUAL

class EmptyStackException(Exception):
    pass

class StackManual:
    def __init__(self):
        self.items = []

    def push(self, value):
        self.items.append(value)

    def pop(self):
        if self.isEmpty():
            raise EmptyStackException("La pila está vacía, no se puede deshacer.")
        return self.items.pop()

    def peek(self):
        if self.isEmpty():
            raise EmptyStackException("La pila está vacía.")
        return self.items[-1]

    def isEmpty(self):
        return len(self.items) == 0

    def size(self):
        return len(self.items)

    def clear(self):
        self.items = []


class EmptyQueueException(Exception):
    pass

class QueueManual:
    def __init__(self):
        self.items = []

    def enqueue(self, value):
        self.items.append(value)

    def dequeue(self):
        if self.isEmpty():
            raise EmptyQueueException("La cola está vacía.")
        return self.items.pop(0)

    def front(self):
        if self.isEmpty():
            raise EmptyQueueException("La cola está vacía.")
        return self.items[0]

    def isEmpty(self):
        return len(self.items) == 0

    def size(self):
        return len(self.items)

    def clear(self):
        self.items = []



# Imporporacion de colas y pilas al juego ahorcado


def obtener_palabra_aleatoria():
    palabras = ["materia", "comida", "agua", "gato", "felicidad", "saltar", "futbol"]
    return random.choice(palabras)

def mostrar_tablero(palabra_secreta, letras):
    tablero = ""
    for letra in palabra_secreta:
        tablero += letra if letra in letras else "_"
    print(tablero)

def jugar_ahorcado():
    palabra_secreta = obtener_palabra_aleatoria()
    letras_adivinadas = []
    pila_undo = StackManual()  # Guardar letras ingresadas
    cola_jugadores = QueueManual()

    # Registrar jugadores
    cola_jugadores.enqueue("Jugador 1")
    cola_jugadores.enqueue("Jugador 2")

    intentos_restantes = 10
    print("\nJUEGO DEL AHORCADO")

    while intentos_restantes > 0:
        jugador_actual = cola_jugadores.dequeue()
        cola_jugadores.enqueue(jugador_actual)

        print(f"\nTurno de: {jugador_actual}")
        mostrar_tablero(palabra_secreta, letras_adivinadas)
        letra = input("Empieza cachon! Escribe una letra, o escribe UNDO para deshacer última letra: ").lower()

        if letra == "undo":
            try:
                ultima = pila_undo.pop()
                letras_adivinadas.remove(ultima)
                print(f"Se deshizo la letra: {ultima}")
            except:
                print("No hay jugadas por deshacer.")
            continue

        if letra in letras_adivinadas:
            print("Tu si eres cachon, ya habías ingresado esa letra.")
            continue

        pila_undo.push(letra)

        if letra in palabra_secreta:
            letras_adivinadas.append(letra)
            if set(palabra_secreta).issubset(set(letras_adivinadas)):
                print(f"¡Tu si sirbes ganaste! La palabra era: {palabra_secreta}")
                return
        else:
            intentos_restantes -= 1
            print(f"Esa no era cachon. Intentos restantes: {intentos_restantes}")

    print(f"No te dio el coco. La palabra era: {palabra_secreta}")



# Segunta parte: version con codigos nativos

def pila_nativa():
    pila = []  # list como pila
    pila.append("a")
    pila.append("b")
    pila.append("c")
    print("\nPila nativa:", pila)
    print("Pop:", pila.pop())
    print("Después:", pila)

def cola_nativa():
    cola = deque()  # deque como cola
    cola.append("Jugador 1")
    cola.append("Jugador 2")
    print("\nCola nativa:", cola)
    print("Dequeue:", cola.popleft())
    print("Después:", cola)


# Ejecutar el codigo completo 
if __name__ == "__main__":
    jugar_ahorcado()
    pila_nativa()
    cola_nativa()
