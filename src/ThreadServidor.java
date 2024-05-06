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
	private Socket stkServer= null;
	private Servidor servidor;
	int id;
	
	public ThreadServidor(Socket pSocket, int pId, Servidor servidor) {
		this.stkServer = pSocket;
		this.id = pId;
		this.servidor = servidor;
	}
	
	public void run(){
		try {
			ProtocoloServidor.procesar( this.stkServer, this.servidor);
			stkServer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
