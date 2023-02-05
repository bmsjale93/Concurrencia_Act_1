package Actividad_1_HilosySockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Class Server
public class Server {

	public static void main(String[] args) throws IOException {

		ServerSocket server = new ServerSocket(9999); // Creamos el servidor

		while (true) {

			System.out.println("Conexión establecida con el servidor.");

			// Inicializamos el socket.
			Socket socket = null;

			try {

				// Si accede un cliente, le pedimos al socket que acepte la petición.
				socket = server.accept();
				System.out.println("Un nuevo cliente se ha conectado: " + socket);

				// Creamos entrada y salida de datos en el servidor.
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());

				// Asignamos un nuevo hilo para el cliente dentro del servidor.
				// Este hilo atenderá las peticiones del cliente.
				System.out.println("Asignando un nuevo hilo para este cliente");

				Thread hiloNuevo = new ClientHandler(socket, in, out); // Creamos el hilo

				// Comenzamos con el nuevo hilo.
				hiloNuevo.start();

			} catch (IOException e) {

				socket.close();

				e.printStackTrace();

			}

		}

	}
}

//Class ClientHandler -> Controlador del Hilo del Cliente
class ClientHandler extends Thread {

	// Instanciamos las variables de entrada y salida.
	DataInputStream dataIn;
	DataOutputStream dataOut;

	// Instanciamos el socket
	Socket socket;

	// Creamos el constructor de la clase controladora del hilo del cliente
	public ClientHandler(Socket socket, DataInputStream dataIn, DataOutputStream dataOut) {

		this.socket = socket;

		this.dataIn = dataIn;

		this.dataOut = dataOut;

	}

	@Override
	public void run() {

		// Instanciamos los String que vamos a recibir del cliente
		int received; // Recibido
		String toReturn; // Devolver al cliente.

		// Bucle while que nos permite ejecutar las opciones para el cliente.
		while (true) {

			try {

				// Preguntamos al cliente que quiere hacer.
				dataOut.writeUTF("------------------------------\n" + "BIENVENIDO AL CHATBOT DE ALEWEB\n"
						+ "------------------------------\n" + "Elija una de las siguientes opciones:\n"
						+ "[1] Precio de Landing Page.\n" + "[2] Precio de Web Empresa.\n"
						+ "[3] Precio de E-Commerce.\n" + "[4] Tiempo de trabajo.\n" + "[5] Formato de Pago.\n"
						+ "[6] Terminar la conexión.\n");

				// Guardamos en received lo que nos mande el cliente.
				received = dataIn.readInt();

				// Si el cliente pide salir.
				if (received == 6) {

					System.out.println("¡El Cliente " + socket + " ha salido!");

					System.out.println("¡Cerrando Conexión!");

					socket.close(); // Cerramos el socket que atiende al cliente.

					System.out.println("Conexión cerrada.");

					dataIn.close();
					dataOut.close();

					break;

				}

				switch (received) {

				case 1:

					System.out.println("El cliente ha escogido la opción " + received);

					toReturn = "\n---------------------------------------------------------------\n"
							+ "El precio establecido para una landing page es de 600€ + IVA."
							+ "\n---------------------------------------------------------------\n";

					dataOut.writeUTF(toReturn);

					break;

				case 2:

					System.out.println("El cliente ha escogido la opción " + received);

					toReturn = "\n---------------------------------------------------------------\n"
							+ "El precio establecido para una página web de empresa es de 800€ + IVA."
							+ "\n---------------------------------------------------------------\n";

					dataOut.writeUTF(toReturn);

					break;

				case 3:

					System.out.println("El cliente ha escogido la opción " + received);

					toReturn = "\n---------------------------------------------------------------\n"
							+ "El precio establecido para un ecommerce es de 1400€ + IVA."
							+ "\n---------------------------------------------------------------\n";

					dataOut.writeUTF(toReturn);

					break;

				case 4:

					System.out.println("El cliente ha escogido la opción " + received);

					toReturn = "\n---------------------------------------------------------------\n"
							+ "Según el tipo de sitio web que escojas, el tiempo de duración será:"
							+ "\nLanding page: entre 6 y 12 días." + "\nSitio Web Empresa: entre 15 y 20 días."
							+ "\nE-Commerce: entre 20 y 30 días."
							+ "\n---------------------------------------------------------------\n";

					dataOut.writeUTF(toReturn);

					break;

				case 5:

					System.out.println("El cliente ha escogido la opción " + received);

					toReturn = "\n---------------------------------------------------------------\n"
							+ "Las formas de pago serán:" + "\nTransferencia a cuenta." + "\nTarjeta de Crédito."
							+ "\nPaypal."
							+ "\nSe realizarán 2 pagos: el 50% antes de empezar y el 50% restante al cierre del proyecto."
							+ "\n---------------------------------------------------------------\n";

					dataOut.writeUTF(toReturn);

					break;

				default:

					dataOut.writeUTF("\n---------------------------------------------------------------\n"
							+ "\"Por favor, seleccione una opción válida [1 a 6]."
							+ "\n---------------------------------------------------------------\n");

					break;

				}

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

}
