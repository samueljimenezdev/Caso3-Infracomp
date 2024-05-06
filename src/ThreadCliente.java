import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Random;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.*;
import java.nio.charset.StandardCharsets;


public class ThreadCliente extends Thread {
	private Socket stkCliente= null;
	private int id;
	private int numToComunicate;
	
    static Random aleatorio = new Random();
    private static final String valid_char = String.join("", 
    	    "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 
    	    "abcdefghijklmnopqrstuvwxyz", 
    	    "0123456789"
    	);
    private Long reto;
    private String login;
    private String contrasenia;
	private PublicKey publica;
	
	public ThreadCliente(Socket pSocket, int pId, int numToComunicate, PublicKey publica) {
		this.stkCliente = pSocket;
		this.id = pId;
		this.numToComunicate = numToComunicate;
        SecureRandom random = new SecureRandom();
        this.reto = (long) random.nextInt(10000);
        this.contrasenia = generateRandomString(8);
        this.publica = publica;
	}
	
    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(valid_char.length());
            char randomChar = valid_char.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    
    }
	
    public Long getReto() {
        return this.reto;
    }
    
    public int getIdCliente() {
    	return this.id;
    }
    
    public PublicKey getPublica() {
    	return this.publica;
    }
    
    public BigInteger generarDatosGY(BigInteger p, BigInteger g, int y) {
        return g.pow(y).mod(p);
    }
    
    public BigInteger calcularK(BigInteger gx, int y, BigInteger  p) {
    	return gx.pow(y).mod(p);
    }
	public void run() {
		try {
			ProtocoloCliente.procesar(numToComunicate, this, stkCliente);
			stkCliente.close();
			
		}catch(IOException | InvalidKeyException | ClassNotFoundException | SignatureException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
