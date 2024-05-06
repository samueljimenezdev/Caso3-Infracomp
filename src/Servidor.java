import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

public class Servidor extends Thread{
	
	public static final int PUERTO = 65533;
	ServerSocket ss;
    private PublicKey publica;
    private PrivateKey privada;
    private Random random;
    private int x;
	
	public Servidor() throws IOException, NoSuchAlgorithmException{
		try {
			this.ss = new ServerSocket(PUERTO);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        this.random = new Random();
        KeyPair keyPair = generator.generateKeyPair();
        this.publica = keyPair.getPublic();
        this.privada = keyPair.getPrivate();
        this.x = random.nextInt(16);
	}
	
	public PublicKey getPublica() {
		return publica;
	}
	
	public PrivateKey getPrivada() {
		return privada;
	}
	
    public String generarDatosDH(BigInteger p, BigInteger g) {

        System.out.println("valor de x: " + this.x);
        BigInteger gALaX = g.pow(this.x);
        System.out.println("Calculado");
        String gToThePowerX = gALaX.toString();

        String data = p + "$";
        data += g;
        data += "$";
        data += gToThePowerX;
        return data;
    }
	
	public void run() {
		boolean continuar = true;
		int numeroThreads = 0;
		try {
			while(continuar) {
				Socket socket = this.ss.accept();
				ThreadServidor thread = new ThreadServidor(socket, numeroThreads, this);
				numeroThreads++;
				thread.start();
			}
			ss.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	

	


}
