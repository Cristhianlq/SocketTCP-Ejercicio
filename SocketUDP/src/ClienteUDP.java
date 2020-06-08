import java.net.*;
import java.io.*;

public class ClienteUDP {
	public static String recuperar(String cad, int tam) {
		String res = "";
		for (int i = 0; i < tam; i++)
			res += cad.charAt(i);
		return res;
	}

	// Los argumentos proporcionan el mensaje y el nombre del servidor
	public static void main(String args[]) {
		System.out.println("....Cliente.....");
		try {
			DatagramSocket socketUDP = new DatagramSocket();
			int puertoServidor = 6789;
			InetAddress hostServidor = InetAddress.getByName("localhost");
			BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
			String cad;
			// Leemos la cadena que enviaremos al servidor
			// Terminara cuando se escriba la palabra "exit"
			while (true) {
				cad = sc.readLine();
				if (cad.equals("exit"))
					break;
				byte[] mensaje = cad.getBytes();
				// Construimos un datagrama para enviar el mensaje al servidor
				DatagramPacket peticion = new DatagramPacket(mensaje, cad.length(), hostServidor, puertoServidor);
				// Enviamos el datagrama
				socketUDP.send(peticion);
				// Construimos el DatagramPacket que contendrá la respuesta
				byte[] bufer = new byte[1000];
				DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
				socketUDP.receive(respuesta);
				// Recuperamos el mensaje recibido por parte del servidor
				String resp = recuperar(new String(respuesta.getData()), respuesta.getLength());
				// Enviamos la respuesta del servidor a la salida estandar
				System.out.println("Respuesta: " + resp);
			}
			// Cerramos el socket
			socketUDP.close();
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}

	}
}