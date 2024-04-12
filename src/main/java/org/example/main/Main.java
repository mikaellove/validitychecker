package org.example.main;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.cli.ParseException;
import org.example.validitychecks.exceptions.EmptyProgramInputException;
import org.tinylog.Logger;

public class Main {
	public static void main(String[] args) {
		CommandLineOption inputDataOption = new CommandLineOption("input", "data", "Specify data for input.");
		CommandLineOption checksOrderOption = new CommandLineOption("order", "order of validity checks", "Configure the order by separating each check with a space. The following checks are available: CarRegistrationNumberCheck, NotNullCheck, SocialSecurityCheck");

		try {
			ValidityChecker checker = new ValidityChecker(args, inputDataOption, checksOrderOption);
			checker.initValidityChecks(checksOrderOption.name(), inputDataOption.name());

			if (checker.runValidityChecks()) {
				System.out.println("\nInput data successfully validated!");
			} else{
				System.out.println("\nInput data failed validation!");
			}
		} catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException  e) {
			Logger.error("Configuration error of the order of validity checks.");
		} catch (ParseException e) {
			Logger.error(e.getMessage());
		} catch (EmptyProgramInputException e) {
			//Shows help menu
		}

		System.out.println("Program exited...");
	}
}