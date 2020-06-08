import java.net.*;
import java.io.*;

public class ServidorUDP {
	public static String recuperar(String cad, int tam) {
		String res = "";
		for (int i = 0; i < tam; i++)
			res += cad.charAt(i);
		return res;
	}

	public static int contarPalabras(String cad) {
		int tam = cad.length();
		if (tam == 0)
			return 0;
		int c = 1;
		for (int i = 0; i < tam; i++) {
			if (cad.charAt(i) == ' ')
				c++;
		}
		return c;
	}

	public static void main(String args[]) {
		System.out.println(".....SERVIDOR.....");
		System.out.println("Esperando peticiones...");
		try {
			DatagramSocket socketUDP = new DatagramSocket(6789);
			byte[] bufer = new byte[1000];
			while (true) {
				// Construimos el DatagramPacket para recibir peticiones
				DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
				// Leemos una petición del DatagramSocket
				socketUDP.receive(peticion);

				System.out.print("Datagrama recibido del host: " + peticion.getAddress());
				System.out.println(" desde el puerto remoto: " + peticion.getPort());
				// recuperamos el mensaje
				String resp = recuperar(new String(peticion.getData()), peticion.getLength());
				// concatenamos la cantidad de palabras a un string
				String env = "La cadena tiene " + contarPalabras(resp) + " palabra(s)";
				System.out.println("Datos: " + env);
				byte[] envia = env.getBytes();
				// Construimos el DatagramPacket para enviar la respuesta
				DatagramPacket respuesta = new DatagramPacket(envia, env.length(), peticion.getAddress(),
						peticion.getPort());
				// Enviamos la respuesta, que indica la cantidad de palabras
				socketUDP.send(respuesta);
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}