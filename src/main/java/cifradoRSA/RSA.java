package cifradoRSA;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private final static BigInteger one = new BigInteger("1");
    final static SecureRandom random = new SecureRandom();

    private final BigInteger privateKey;
    private final BigInteger publicKey;
    private final BigInteger modulus;
    private final BigInteger p;
    private final BigInteger q;

    // generate an N-bit (roughly) public and private key
    RSA(int N) {
        p = BigInteger.probablePrime(N/2, random);
        q = BigInteger.probablePrime(N/2, random);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = new BigInteger("65537");     // common value in practice = 2^16 + 1
        privateKey = publicKey.modInverse(phi);
    }

    BigInteger encrypt(String message) {
        // Convert message to a sequence of numbers using ASCII
        byte[] bytes = message.getBytes();
        BigInteger messageAsNumbers = new BigInteger(bytes);

//        return encrypt(convertFromBaseN(messageAsNumbers, BigInteger.TEN));

        // Encrypt the message
        return messageAsNumbers.modPow(publicKey, modulus);
    }

    String decrypt(BigInteger encrypted) {
        // Decrypt the message using CRT
        BigInteger decryptedAsNumbers = decryptWithCRT(encrypted, p, q, privateKey);

        // Convert the sequence of numbers back to a string using ASCII
        byte[] bytes = decryptedAsNumbers.toByteArray();
        return new String(bytes);
    }


    BigInteger decryptWithCRT(BigInteger encrypted, BigInteger p, BigInteger q, BigInteger d) {
        BigInteger dp = d.mod(p.subtract(one));
        BigInteger dq = d.mod(q.subtract(one));
        BigInteger qInv = q.modInverse(p);

        BigInteger m1 = encrypted.modPow(dp, p);
        BigInteger m2 = encrypted.modPow(dq, q);

        BigInteger h = qInv.multiply(m1.subtract(m2)).mod(p);

        return m2.add(h.multiply(q));
    }


    BigInteger convertFromBaseN(BigInteger number, BigInteger n) {
        BigInteger result = BigInteger.ZERO;
        BigInteger multiplier = BigInteger.ONE;

        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger remainder = number.mod(BigInteger.TEN);
            number = number.divide(BigInteger.TEN);
            result = result.add(remainder.multiply(multiplier));
            multiplier = multiplier.multiply(n);
        }

        return result;
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey  + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }
}
