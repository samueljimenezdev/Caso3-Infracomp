import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMac {

    public static String doHMac(String hK, String consulta) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException{
  
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(hK.getBytes("UTF-8"), "HmacSHA256");
        mac.init(secret_key);
        byte[] withoutFormat = mac.doFinal(consulta.getBytes("UTF-8"));
        StringBuilder stringToCreate = new StringBuilder(withoutFormat.length*2);

        for (byte byt: withoutFormat) {
        	stringToCreate.append(String.format("%02x", byt));
        }

        return stringToCreate.toString();
    }
}
