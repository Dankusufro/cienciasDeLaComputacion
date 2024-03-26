package cifradoRSA;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        int N = 1024;
        RSA key = new RSA(N);
        System.out.println(key);

        // create message by converting string to integer
        String s = "hola mundo";
        BigInteger encryptedMessage = key.encrypt(s);
        System.out.println("encrypted = " + encryptedMessage);

        // Decrypt the message
        String decryptedMessage = key.decrypt(encryptedMessage);
        System.out.println("message   = " + s);
        System.out.println("decrypted = " + decryptedMessage);
    }
}
