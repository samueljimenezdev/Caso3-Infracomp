import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

public class ProtocoloServidor {

	public static void procesar(Socket stkServer, Servidor servidor) throws IOException, InvalidKeyException, NoSuchAlgorithmException, SignatureException{
			DataInputStream lector;
			int estado = 1;
	        DataOutputStream escritor = new DataOutputStream(stkServer.getOutputStream());
            //ObjectInputStream lectorObjetos = new ObjectInputStream(stkServer.getInputStream());
            ObjectOutputStream escritorObjetos = new ObjectOutputStream(stkServer.getOutputStream());
			
			while(estado <= 8 && estado != -1) {
				switch (estado){
				case 1:
					lector = new DataInputStream(stkServer.getInputStream());
					String inputLine = lector.readUTF();
					String[] data = inputLine.split(",");
					byte[] retoFirmado = Firmas.firmarSHA256(data[1], servidor.getPrivada());
					escritorObjetos.writeObject(retoFirmado);
					estado++;
					break;
					
				case 2:
					lector = new DataInputStream(stkServer.getInputStream());
					inputLine = lector.readUTF();
					if(inputLine.equalsIgnoreCase("OK")) {
						
	                    BigInteger g = DHParameters.getG();
	                    escritorObjetos.writeObject(g);
	                    
	                    BigInteger p = DHParameters.getP();
	                    escritorObjetos.writeObject(p);
	                    
	                    BigInteger gx = servidor.generarDatosGX(p, g);
	                    escritorObjetos.writeObject(gx);
	                    
	                    byte[] iv = DHParameters.generarIV();
	                    escritorObjetos.writeObject(iv);
	                    
	                    byte[] dhFirmado = Firmas.firmarSHA256(String.valueOf(g)+ String.valueOf(p) + String.valueOf(gx) , servidor.getPrivada());
	                    escritorObjetos.writeObject(dhFirmado);
					}
					estado++;
					break;
					
				case 3:

					break;
				case 4:

					break;
				case 5:

					break;
				case 6:

					break;
				case 7:

					break;
				case 8:

					break;
				}
				
			}
			escritor.close();
			//lector.close();
			//lectorObjetos.close();
			escritorObjetos.close();	
	}
}

 