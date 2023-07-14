// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class test_FakeStackTrace
{

	public static void main(String[] args)
	{
		String exceptionName = "FakeException";
		int numTraces = 5;

		List< String > stackTraces = generateFakeStackTraces(exceptionName, numTraces);
		for (String stackTrace : stackTraces)
		{
			System.out.println(stackTrace);
			System.out.println("--------------------------------");
		}
	}

	public static List< String > generateFakeStackTraces(String exceptionName, int numTraces)
	{
		List< String > stackTraces = new ArrayList<>();

		for (int i = 0; i < numTraces; i++)
		{
			StringBuilder stackTrace = new StringBuilder(exceptionName + ": ");

			int numFrames = new Random().nextInt(10) + 1;
			for (int j = 0; j < numFrames; j++)
			{
				String functionName = generateRandomName(5, 10);
				String moduleName = generateRandomName(8, 15);
				int lineNumber = new Random().nextInt(9999) + 1;

				stackTrace.append("\tat ").append(functionName).append("(").append(moduleName).append(".java:")
						.append(lineNumber).append(")\n");
			}

			stackTraces.add(stackTrace.toString());
		}

		return stackTraces;
	}

	public static String generateRandomName(int minLength, int maxLength)
	{
		Random random = new Random();
		int length = random.nextInt(maxLength - minLength + 1) + minLength;
		StringBuilder name = new StringBuilder();

		for (int i = 0; i < length; i++)
		{
			char randomChar = (char) (random.nextInt(26) + 'a');
			name.append(randomChar);
		}

		return name.toString();
	}
}
