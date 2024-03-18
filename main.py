import tkinter as tk


def cifrar(mensaje, k, alfabeto):
    if not alfabeto or not all(letra in alfabeto for letra in mensaje):
        return "Error: el alfabeto está vacío o el mensaje contiene letras no presentes en el alfabeto"
    mensaje_cifrado = ""
    for letra in mensaje:
        indice = (alfabeto.index(letra) + k) % len(alfabeto)
        mensaje_cifrado += alfabeto[indice]
    return mensaje_cifrado


def descifrar(mensaje_cifrado, k, alfabeto):
    mensaje = ""
    for letra in mensaje_cifrado:
        if letra in alfabeto:
            indice = (alfabeto.index(letra) - k) % len(alfabeto)
            mensaje += alfabeto[indice]
        else:
            mensaje += letra
    return mensaje


def cifrar_descifrar():
    mensaje = mensaje_entry.get()
    k = int(k_entry.get())
    alfabeto = alfabeto_entry.get()
    if var.get() == 1:
        result = cifrar(mensaje, k, alfabeto)
    else:
        result = descifrar(mensaje, k, alfabeto)
    result_entry.delete(0, tk.END)
    result_entry.insert(tk.END, result)


root = tk.Tk()
root.title("  César Afín  ")

mensaje_label = tk.Label(root, text="Mensaje")
mensaje_entry = tk.Entry(root)
k_label = tk.Label(root, text="Clave de desplazamiento")
k_entry = tk.Entry(root)
alfabeto_label = tk.Label(root, text="Alfabeto")
alfabeto_entry = tk.Entry(root)
var = tk.IntVar()
cifrar_radio = tk.Radiobutton(root, text="Cifrar", variable=var, value=1)
descifrar_radio = tk.Radiobutton(root, text="Descifrar", variable=var, value=2)
button = tk.Button(root, text="Ejecutar", command=cifrar_descifrar)
result_label = tk.Label(root, text="Resultado")
result_entry = tk.Entry(root)

mensaje_label.grid()
mensaje_entry.grid()
k_label.grid()
k_entry.grid()
alfabeto_label.grid()
alfabeto_entry.grid()
cifrar_radio.grid()
descifrar_radio.grid()
button.grid()
result_label.grid()
result_entry.grid()

root.mainloop()
