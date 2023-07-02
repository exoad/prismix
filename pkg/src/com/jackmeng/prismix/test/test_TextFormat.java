package com.jackmeng.prismix.test;

// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

public class test_TextFormat
{

	public recfor

	public static String formatText(String column1Format,  String column2, String text)
	{
		int maxLength = 8;

		StringBuilder formattedText = new StringBuilder();
		for (int i = 0; i < text.length(); i += maxLength)
		{
			int endIndex = Math.min(i + maxLength, text.length());
			formattedText.append("|").append(column1).append("| ").append("|").append(column2).append("|    ")
					.append(text.substring(i, endIndex));
			if (endIndex != text.length())
			{
				formattedText.append("\n");
			}
		}

		return formattedText.toString();
	}

	public static void main(String[] args)
	{
		String column1 = "XXXX";
		String column2 = "XXXXXX";
		String text = "EEEEEEEEEEEEE";

		String formattedText = formatText(column1, column2, text);
		System.out.println(formattedText);
	}
}
