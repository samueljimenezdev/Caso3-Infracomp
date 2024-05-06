import java.math.BigInteger;
import java.security.SecureRandom;

public class DHParameters {
	
    private static final BigInteger p = new BigInteger(
            "00a63396e0217b3544775fe5195f2aadb271d1b423e2360edb4bce4ad89cd992d8449e715f313eee4a6926d02cfd010fdb85293c45853ccac04781a0825ad1656778d9f9273899e7eb56fa279543ad2109dd040a8998880fe964174ec826e0f192d8ca7ccc33573f19453406e3639e85fd1e1d58c0fdaf2707c39b57e19aadb58f",16);
    
    private static final BigInteger g = BigInteger.valueOf(2);

    public BigInteger llaveMaestra_generador(BigInteger X) {
        BigInteger y = g.modPow(X, p);
        return y;
    }
    
    public static BigInteger getP() {
        return p;
    }

    public static BigInteger getG() {
        return g;
    }
    
    public static byte[] generarIV() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return iv;
    }
    
}
