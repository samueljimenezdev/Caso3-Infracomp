import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ProtocoloServidor {

	public static void procesar(Socket stkServer, Servidor servidor) throws IOException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidAlgorithmParameterException{
			int estado = 1;
            ObjectInputStream lector = new ObjectInputStream(stkServer.getInputStream());
            ObjectOutputStream escritorObjetos = new ObjectOutputStream(stkServer.getOutputStream());
            BigInteger p = null;
            String[] keys = null;
            String login = "user";
            byte[] loginCifrado = null;
            byte[] passCifrado = null;
            String password = "pass";
            byte[] iv = null;
            
			
			while(estado <= 8 && estado != -1) {
				
				switch (estado){
				
				case 1:
					String inputLine = (String) lector.readObject();
					String[] data = inputLine.split(",");
					byte[] retoFirmado = Firmas.firmarSHA256(data[1], servidor.getPrivada());
					escritorObjetos.writeObject(retoFirmado);
					estado++;
					break;
					
				case 2:
					inputLine = (String) lector.readObject();
					if(inputLine.equalsIgnoreCase("OK")) {
						
	                    BigInteger g = DHParameters.getG();
	                    escritorObjetos.writeObject(g);
	                    
	                    p = DHParameters.getP();
	                    escritorObjetos.writeObject(p);
	                    
	                    BigInteger gx = servidor.generarDatosGX(p, g);
	                    escritorObjetos.writeObject(gx);
	                    
	                    iv = DHParameters.generarIV();
	                    escritorObjetos.writeObject(iv);
	                    
	                    byte[] dhFirmado = Firmas.firmarSHA256(String.valueOf(g)+ String.valueOf(p) + String.valueOf(gx) , servidor.getPrivada());
	                    escritorObjetos.writeObject(dhFirmado);
					}
					estado++;
					break;
					
				case 3:
					inputLine = (String) lector.readObject();
					if(inputLine.equalsIgnoreCase("OK")) {
					estado++;
					}	
					//TODO: Cerrar con error
					break;
					
				case 4:
					BigInteger gy = (BigInteger) lector.readObject();
					BigInteger k = servidor.calcularK(gy, p);
					keys = Digest.digWithSHA512(k);
					escritorObjetos.writeObject("CONTINUAR");
					estado++;
					break;
					
				case 5:
					loginCifrado = (byte[]) lector.readObject();
					estado++;
					break;
					
				case 6:
					passCifrado = (byte[]) lector.readObject();
					String loginDescifrado = Cifrado.descifrarPKCS5(keys[0], iv, loginCifrado);
					String passDescifrado = Cifrado.descifrarPKCS5(keys[0], iv, passCifrado);
					estado++;
					break;
				case 7:

					break;
				case 8:

					break;
				}
				
			}
			//lector.close();
			//lectorObjetos.close();
			escritorObjetos.close();	
	}
}

 