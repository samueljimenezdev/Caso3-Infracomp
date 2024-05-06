import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cifrado {
	
	public static byte[] cifrarPKCS5(String eK, byte[] iv, String toCifrate) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		IvParameterSpec ivCorrecto = new IvParameterSpec(iv);
	    Cipher cifrador = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    SecretKeySpec kK = new SecretKeySpec(eK.getBytes(), "AES");
	    cifrador.init(Cipher.ENCRYPT_MODE, kK, ivCorrecto);
	    byte[] cifrate = cifrador.doFinal(toCifrate.getBytes()); 
	    return cifrate;
	}

	public static String descifrarPKCS5(String eK, byte[] iv, byte[] toDescifrate) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		IvParameterSpec ivCorrecto = new IvParameterSpec(iv);
        Cipher cifrador = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec kK = new SecretKeySpec(eK.getBytes(), "AES");
        cifrador.init(Cipher.DECRYPT_MODE, kK, ivCorrecto);
        byte[] charDescifrate = cifrador.doFinal(toDescifrate);
        String response = new String(charDescifrate); 
        return response;
	}
}



