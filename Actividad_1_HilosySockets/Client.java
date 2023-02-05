package Actividad_1_HilosySockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

//Client class
public class Client {

	public static void main(String[] args) {

		try {

			// Creamos un objeto tipo scanner que lea lo que pide el cliente.
			Scanner reader = new Scanner(System.in);

			// Objeto tipo InetAddress que proporciona el servidor al socket.
			InetAddress ip = InetAddress.getByName("localhost");

			// Objeto tipo socket que permite establecer la conexión.
			Socket socket = new Socket(ip, 9999);

			// Variables de entrada y salida de datos.
			DataInputStream dataIn = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

			while (true) {

				// El cliente leerá el mensaje del servidor pidiendole que elija una opción.
				System.out.println(dataIn.readUTF());

				// Guardamos en toSend lo que nos pida el cliente
				int toSend = reader.nextInt();

				// Enviamos al servidor la petición del cliente.
				dataOut.writeInt(toSend);

				// Si pide salir del servidor
				if (toSend == 6) {

					System.out.println("Cerrando esta conexión: " + socket);

					socket.close();

					System.out.println("-------------------------------");
					System.out.println("¡Conexión terminada con éxito.!");
					System.out.println("-------------------------------");

					break;

				}

				// Si pide alguna opción, leemos lo que nos manda el servidor.
				String received = dataIn.readUTF();
				System.out.println(received);

			}

			// Cerramos el scanner
			reader.close();

			// Cerramos la entrada de datos
			dataIn.close();

			// Cerramos la salida de datos.
			dataOut.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
