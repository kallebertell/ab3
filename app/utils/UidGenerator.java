package utils;

import java.util.Random;

public class UidGenerator {
	public static final String chars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int charsLength = chars.length();
	
	public static String newId() {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<6; i++) {
			sb.append(chars.charAt(rnd.nextInt(charsLength)));
		}
		return sb.toString();
	}
	
}
