import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

public class ProtocoloServidor {

	public static void procesar(DataInputStream pIn, DataOutputStream pOut, Servidor servidor, ObjectInputStream pOIn, ObjectOutputStream pOOut) throws IOException, InvalidKeyException, NoSuchAlgorithmException, SignatureException{

			int estado = 1;			
			
			while(estado <= 8 && estado != -1) {
				switch (estado){
				case 1:
					System.out.println("Entro a caso 1 sv");
					String inputLine = pIn.readUTF();
					System.out.println(inputLine);
					String[] data = inputLine.split(",");
					byte[] retoFirmado = Firmas.firmarSHA256(data[1], servidor.getPrivada());
					pOOut.writeObject(retoFirmado);
					estado++;
					break;
					
				case 2:

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
		}
	
    private static byte[] resolverReto(Long reto, PrivateKey privada) {
        byte[] retoCifrado = CifradoAsimetrico.cifrarLlave(privada, String.valueOf(reto));
        return retoCifrado;
    }

	
}

 