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

public class ProtocoloCliente {

	public static void procesar(int numToComunicate, ThreadCliente cliente, Socket stkCliente)
			throws IOException, ClassNotFoundException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		int estado = 1;
        DataOutputStream escritor = new DataOutputStream(stkCliente.getOutputStream());
        ObjectInputStream lectorObjetos;
        BigInteger g;
        BigInteger p;
        BigInteger gx;
        byte[] iv;
        //ObjectOutputStream escritorObjetos = new ObjectOutputStream(stkCliente.getOutputStream());
        //DataInputStream lector = new DataInputStream(stkCliente.getInputStream());
		
		String reto = "SECURE INIT,"+cliente.getReto().toString();
		escritor.writeUTF(reto);
		
		while (estado <= 10 && estado != -1) {
			switch (estado) {
			case 1:
				lectorObjetos = new ObjectInputStream(stkCliente.getInputStream());
				byte[] arregloBytes = (byte[]) lectorObjetos.readObject();
				String respuesta = Firmas.verifyFirmaSHA256(cliente.getPublica(),arregloBytes, reto);
				escritor.writeUTF(respuesta);
				estado++;
				break;
				
			case 2:
				lectorObjetos = new ObjectInputStream(stkCliente.getInputStream());
				g = (BigInteger) lectorObjetos.readObject();
				estado++;
				break;
				
			case 3:
				lectorObjetos = new ObjectInputStream(stkCliente.getInputStream());
				p = (BigInteger) lectorObjetos.readObject();
				estado++;
				break;
				
			case 4:
				lectorObjetos = new ObjectInputStream(stkCliente.getInputStream());
				gx = (BigInteger) lectorObjetos.readObject();
				estado++;
				break;
			
			case 5:
				lectorObjetos = new ObjectInputStream(stkCliente.getInputStream());
				iv = (byte[]) lectorObjetos.readObject();
				estado++;
				System.out.println("Llego correctamente iv");
				break;
				
			case 6:

				break;
			case 7:

				break;
			case 8:

				break;
			case 9:

				break;
			case 10:

				break;
			}
			estado++;
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
