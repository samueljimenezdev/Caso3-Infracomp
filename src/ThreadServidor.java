import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadServidor extends Thread{
	private Socket stkCliente= null;
	private Servidor servidor;
	int id;
	
	public ThreadServidor(Socket pSocket, int pId, Servidor servidor) {
		this.stkCliente = pSocket;
		this.id = pId;
		this.servidor = servidor;
	}
	
	public void run(){

		try {
	        DataInputStream lector = new DataInputStream(stkCliente.getInputStream());; 
	        DataOutputStream escritor = new DataOutputStream(stkCliente.getOutputStream());
            ObjectInputStream lectorObjetos = new ObjectInputStream(stkCliente.getInputStream());
            ObjectOutputStream escritorObjetos = new ObjectOutputStream(stkCliente.getOutputStream());
            System.out.println("PRUEBAA");
			ProtocoloServidor.procesar( lector, escritor, this.servidor, lectorObjetos, escritorObjetos);
			escritor.close();
			lector.close();
			lectorObjetos.close();
			escritorObjetos.close();
			stkCliente.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
