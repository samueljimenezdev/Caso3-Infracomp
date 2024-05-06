import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

public class Digest {
	public static String[] digWithSHA512(BigInteger x) {
        String[] response = new String[2];
        
        try {
            MessageDigest dig = MessageDigest.getInstance("SHA-512");
            byte[] cadenaBytes = dig.digest(String.valueOf(x).getBytes());
            
            byte[] keyForEncryption = Arrays.copyOf(cadenaBytes, 16);
            byte[] keyForHMAC = Arrays.copyOfRange(cadenaBytes, 16, 32); 
            
            BigInteger encryptionKey = new BigInteger(1, keyForEncryption);
            BigInteger hmacKey = new BigInteger(1, keyForHMAC);
            
            response[0] = encryptionKey.toString(16);
            response[1] = hmacKey.toString(16);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return response;
    }
}
