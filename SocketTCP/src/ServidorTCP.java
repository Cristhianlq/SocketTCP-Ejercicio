import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorTCP {
	// Declaramos el puerto con el cual se trabajara, en este caso el 8888 como
	// indica el ejercicio
	public static final int PORT = 8888;

	public static void main(String[] args) throws IOException {

		System.out.println("...INICIANDO SERVIDOR...");
		System.out.println("Esperando peticiones...");
		ServerSocket socketServidor = null;
		try {
			// Establece el puerto en el que escucha peticiones
			socketServidor = new ServerSocket(PORT);

		} catch (IOException e) {
			System.out.println("No puede escuchar en el puerto: " + PORT);
			System.exit(-1);
		}
		// Creamos el socketCliente, ademas de los canales de entrada y salida.
		Socket socketCliente = null;
		BufferedReader entrada = null;
		PrintWriter salida = null;
		// Mostramos un mensaje el cual nos dice que escuchamos a un soccket
		System.out.println("Escuchando: " + socketServidor);
		try {
			while (true) {
				// Se bloquea hasta que recibe alguna petición de un cliente
				// abriendo un socket para el cliente
				socketCliente = socketServidor.accept();
				System.out.println("Connexión acceptada: " + socketCliente);
				// Establece canal de entrada
				entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
				// Establece canal de salida
				salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())),
						true);
				boolean sw = true;
				// Despliega piedra papel o tijera, segun la opcion del usuario. Termina cuando
				// ingresa un 4
				while (sw) {
					// recibimos el mensaje del cliente
					String opcion = entrada.readLine();
					System.out.println("Cliente: " + opcion);
					// Si la opcion es 4, termina la comunicacion
					if (opcion.equals("4")) {
						sw = false;
					} else {
						String resp = "";
						// comparamos la opcion que mando el cliente
						switch (opcion) {
						case "1":
							System.out.println("selecciono la opcion 1");
							resp = "papel";
							break;
						case "2":
							System.out.println("selecciono la opcion 2");
							resp = "piedra";
							break;
						case "3":
							System.out.println("selecciono la opcion 3");
							resp = "tijera";
							break;
						default:
							System.out.println("selecciono numeros mayores a 4");
							resp = "Solo números entre 1 y 4";
						}
						// Mandamos la respuesta al cliente
						salida.println(resp);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		// Cerramos las librerias.
		salida.close();
		entrada.close();
		socketCliente.close();
		socketServidor.close();
	}
}
