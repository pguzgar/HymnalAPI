package mx.daro.himnario.properties.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

	public static String encrypt(String password) throws NoSuchAlgorithmException, NoSuchPaddingException,
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		byte[] KeyData = System.getProperty("crypt.key").getBytes();
		SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, KS);
		return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8")));
	}

	public static String decrypt(String encryptedtext) throws NoSuchAlgorithmException, NoSuchPaddingException,
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] KeyData = System.getProperty("crypt.key").getBytes();
		SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
		byte[] ecryptedtexttobytes = Base64.getDecoder().decode(encryptedtext);
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, KS);
		byte[] decrypted = cipher.doFinal(ecryptedtexttobytes);
		return new String(decrypted, Charset.forName("UTF-8"));
	}
	
}
