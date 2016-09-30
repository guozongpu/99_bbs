package com.util;

public class ValidationUtil {
	public static boolean validateLength(String validatedString, int length) {
		if (validatedString.length() > length) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean validateNull(String validateString) {
		if (validateString == null || validateString.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}
}
