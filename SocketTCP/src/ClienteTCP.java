import java.net.*;
import java.io.*;

public class ClienteTCP {

	public static void main(String[] args) throws IOException {
		System.out.println("...Cliente...");
		Socket socketCliente = null;
		BufferedReader entrada = null;
		PrintWriter salida = null;
		// Creamos un socket en el lado cliente, enlazado con un
		// servidor que está en la misma máquina que el cliente
		// y que escucha en el puerto 8888 como indica el ejercicio
		try {
			socketCliente = new Socket("localhost", 8888);
			// Obtenemos el canal de entrada con ayuda de BufferReader
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			// Obtenemos el canal de salida con ayuda de PrintWriter
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
		} catch (IOException e) {
			System.err.println("No puede establer canales de Entrada/Salida para la conexión");
			System.exit(-1);
		}
		BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));

		String opcion;
		//El usuario ingresara una opcion, si es la 4 acabara la comunicación
		try {
			boolean sw = true;
			// Desplegamos el menu de opciones
			System.out.println("\n\tMENU");
			System.out.println("1. Opcion 1");
			System.out.println("2. Opcion 2");
			System.out.println("3. Opcion 3");
			System.out.println("4. Salir");
			while (sw) {
				// Leo la entrada del usuario
				opcion = leer.readLine();
				// La envia al servidor
				salida.println(opcion);
				// Si es "4" es que finaliza la comunicación
				if (opcion.equals("4")) {
					sw = false;
				} else {
					opcion = entrada.readLine();
					// Mostramos la respuesta del servidor
					System.out.println("Respuesta servidor: " + opcion);
				}
			}
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}

		// Cerramos las liberas de recursos
		salida.close();
		entrada.close();
		leer.close();
		socketCliente.close();
	}
}
