import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


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
	
	
	
	
	public static boolean main(String[] args) throws FileNotFoundException
	{
		
		String userInput = null;
		File file = new File("/Users/garrettbaker/eclipse-workspace/FaceRecPass.txt");
		Scanner scan = new Scanner(file);
		
		String password = scan.nextLine();
		
		try
		{
			
			Scanner keyboard = new Scanner(userInput);
			System.out.println("Please Enter Password: ");
			userInput = keyboard.nextLine();
			String fluff = "abcdefghij";
			if(toHexString(getSHA(userInput)) + fluff == toHexString(getSHA(password)) + fluff)
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


