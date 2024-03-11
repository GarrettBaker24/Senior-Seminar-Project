import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassEnc {
	
	
		public static byte[]getSHA(String input) throws NoSuchAlgorithmException
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			
			return md.digest(input.getBytes(StandardCharsets.UTF_8));
		}
		
		public static String toHexString(byte[]hash)
		{
			BigInteger number = new BigInteger(1, hash);
			
			StringBuilder hexString = new StringBuilder(number.toString(16));
			
			while(hexString.length() < 32)
			{
				hexString.insert(0, '0');
			}
			
			return hexString.toString();
			
		}

	public static void main(String[] args)
	{
		try
		{
			String string1 = "Password";
			String password = toHexString(getSHA(string1));
		}
		
		catch(NoSuchAlgorithmException e)
		{
			System.out.println("Exception for incorrect algorithm: " + e);
		}
		

	
		}
}
