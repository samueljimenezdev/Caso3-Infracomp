import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Random;

public class ProtocoloCliente {

	public static void procesar(int numToComunicate, ThreadCliente cliente, Socket stkCliente)
			throws IOException, ClassNotFoundException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		
		int estado = 1;
        
        ObjectOutputStream escritor = new ObjectOutputStream(stkCliente.getOutputStream());
        ObjectInputStream lectorObjetos = new ObjectInputStream(stkCliente.getInputStream());
        BigInteger g = null;
        BigInteger p = null;
        BigInteger gx = null;
        Random random = new Random();
        BigInteger k = null;
        int y = random.nextInt(15);
        byte[] iv;
		
		String reto = cliente.getReto().toString();
		escritor.writeObject("SECURE INIT," + reto);
		
		while (estado <= 10 && estado != -1) {
			switch (estado) {
			case 1:
				byte[] arregloBytes = (byte[]) lectorObjetos.readObject();
				String respuesta = Firmas.verifyFirmaSHA256(cliente.getPublica(),arregloBytes, reto);
				escritor.writeObject(respuesta);
				estado++;
				break;
				
			case 2:
				g = (BigInteger) lectorObjetos.readObject();
				estado++;
				break;
				
			case 3:
				p = (BigInteger) lectorObjetos.readObject();
				estado++;
				break;
				
			case 4:
				gx = (BigInteger) lectorObjetos.readObject();
				estado++;
				break;
			
			case 5:
				iv = (byte[]) lectorObjetos.readObject();
				estado++;
				break;
				
			case 6:
				byte[] dhFirmado = (byte[]) lectorObjetos.readObject();	
				String prueba = String.valueOf(g)+ String.valueOf(p) + String.valueOf(gx);
				respuesta = Firmas.verifyFirmaSHA256(cliente.getPublica(),dhFirmado, prueba);
				escritor.writeObject(respuesta);
				BigInteger gy = cliente.generarDatosGY(p, g, y);
				escritor.writeObject(gy);
				k = cliente.calcularK(gx, y, p);
				System.out.println("El k es en el cliente: " + k);
				estado++;
				break;
				
			case 7:
				estado++;
				break;
			case 8:

				break;
			case 9:

				break;
			case 10:

				break;
			}
		}

	}


	public static String verificarReto(byte[] retoCifrado, PublicKey llavePublica, Long reto) {

		byte[] descifrado = CifradoAsimetrico.descifrarLlave(llavePublica, retoCifrado);

		String retoDescifrado = new String(descifrado, StandardCharsets.UTF_8);
		String retoString = String.valueOf(reto);

		if (retoDescifrado.equals(retoString)) {
			return "OK";
		} else {
			return "ERROR";
		}
	}

}
