import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class Firmas {

    public static byte[] firmarSHA256(String data, PrivateKey privada) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
    	
        Signature firma = Signature.getInstance("SHA256withRSA");
        firma.initSign(privada);
        firma.update(data.getBytes());
        
        return firma.sign();

    }
    
    public static String verifyFirmaSHA256 (PublicKey publica, byte[] arregloBytes, String reto) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException{
        Signature firma = Signature.getInstance("SHA256withRSA");
        firma.initVerify(publica);
        firma.update(reto.getBytes());
        boolean isValid = firma.verify(arregloBytes);
        if (isValid) {
        	return "OK";
        }else {
        	return "ERROR";
        }
    }

}
