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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
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
    private Long reto;
	private PublicKey publica;
	
	public ThreadCliente(Socket pSocket, int pId, int numToComunicate, PublicKey publica) {
		this.stkCliente = pSocket;
		this.id = pId;
		this.numToComunicate = numToComunicate;
        SecureRandom random = new SecureRandom();
        this.reto = (long) random.nextInt(10000);
        this.publica = publica;
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
			
		}catch(IOException | InvalidKeyException | ClassNotFoundException | SignatureException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}

}
