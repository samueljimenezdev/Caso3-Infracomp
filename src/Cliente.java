import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;

public class Cliente {
	public static final int PUERTO = 65533;
	public static final String SERVIDOR = "localhost";
	private ThreadCliente[] clientes;
	
	public Cliente(int numClientes, int numToComunicate, PublicKey publica) throws IOException{
		
		this.clientes = new ThreadCliente[numClientes];
		for (int i = 0; i < numClientes; i++) {
			Socket socket = null;
			try {
				socket = new Socket(SERVIDOR, PUERTO);

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			ThreadCliente thread = new ThreadCliente(socket, i, numToComunicate, publica);
			clientes[i] = thread;
		}
	}
	
	public void runClientes () {
		for (ThreadCliente cliente : clientes) {
			cliente.start();
		}
	}
	
}
