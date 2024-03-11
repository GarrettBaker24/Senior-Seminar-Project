import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class PassValidation {
	
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
	
	
	public static boolean main(String[] args)
	{
		
		String userInput = null;
		try
		{
			Scanner keyboard = new Scanner(userInput);
			System.out.println("Please Enter Password: ");
			userInput = keyboard.nextLine();
			String password = "Password";
			if(toHexString(getSHA(userInput)) == toHexString(getSHA(password)))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(NoSuchAlgorithmException e)
		{
			System.out.println("Exception thrown for an incorrect algorithm: " + e);
			return false;
		}
			
		
		}
}


