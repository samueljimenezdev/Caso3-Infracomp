import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ProtocoloCliente {

	public static void procesar(int numToComunicate, ThreadCliente cliente, Socket stkCliente)
			throws IOException, ClassNotFoundException, InvalidKeyException, SignatureException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		int estado = 1;
        
        ObjectOutputStream escritor = new ObjectOutputStream(stkCliente.getOutputStream());
        ObjectInputStream lectorObjetos = new ObjectInputStream(stkCliente.getInputStream());
        BigInteger g = null;
        BigInteger p = null;
        BigInteger gx = null;
        Random random = new Random();
        BigInteger k = null;
        String[] keys = null;
        int y = random.nextInt(15);
        byte[] iv = null;
		
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
				keys = Digest.digWithSHA512(k);
				
				estado++;
				break;
				
			case 7:
				String input = (String) lectorObjetos.readObject();
				if(input.equalsIgnoreCase("CONTINUAR")) {
					
					byte[] loginCifrado = Cifrado.cifrarPKCS5(keys[0], iv, "user");
					escritor.writeObject(loginCifrado);
					
					byte[] passCifrado = Cifrado.cifrarPKCS5(keys[0], iv, "pass");
					escritor.writeObject(passCifrado);
				}
				estado++;
				break;
				
			case 8:
				String inputResponse = (String) lectorObjetos.readObject();
				if(inputResponse.equalsIgnoreCase("OK")) {
					byte[] consultaCifrada = Cifrado.cifrarPKCS5(keys[0], iv, String.valueOf(numToComunicate));
					escritor.writeObject(consultaCifrada);
					String hMac = HMac.doHMac(keys[1], String.valueOf(numToComunicate));
					escritor.writeObject(hMac); 
				}
				estado++;
				break;
				
			case 9:

				break;
			case 10:

				break;
			}
		}

	}




}
