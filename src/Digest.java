import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

public class Digest {
    public static String[] digWithSHA512 ( BigInteger x){
    	String[] response = new String[2];
        try {
        	
            MessageDigest dig = MessageDigest.getInstance("SHA-512");
            byte[] cadenaBytes = dig.digest(String.valueOf(x).getBytes());
            
            byte[] keyForEncryption = Arrays.copyOfRange(cadenaBytes, 0, 32); 
            byte[] keyForHMAC = Arrays.copyOfRange(cadenaBytes, 32, 64); 
            BigInteger encryptionKey = new BigInteger(1, keyForEncryption); 
            BigInteger hmacKey = new BigInteger(1, keyForHMAC);
            String eK= encryptionKey.toString(16);
            String mK = hmacKey.toString(16);
            System.out.println("Clave para cifrado: " + eK);
            System.out.println("Clave para HMAC: " + mK);
            response[0] = eK;
            response[1] = mK;            
   
        } catch (Exception e) {
            e.printStackTrace();
        }
    return response;   
    }
}
