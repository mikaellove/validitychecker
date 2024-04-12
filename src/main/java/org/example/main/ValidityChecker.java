package org.example.main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.example.validitychecks.exceptions.EmptyProgramInputException;
import org.example.validitychecks.interfaces.ValidityCheck;

public class ValidityChecker {
	CommandLine programInput;
	String inputData = "";
	List<ValidityCheck> validityChecks = new ArrayList<>();

	/**
	 * Constructor will parse the arguments against the commandline options given.
	 */
	public ValidityChecker(String[] args, CommandLineOption... commandLineOptions) throws ParseException, EmptyProgramInputException {
		this.programInput = parseArguments(args, commandLineOptions);
	}

	/**
	 * Runs the configured Validity Checks.
	 * <p>
	 * Before this method is executed initialization of the Validity Checks
	 * needs to have been done with {@link #initValidityChecks(String, String)}
	 */
	public boolean runValidityChecks() {
		var result = validityChecks.stream().map(check -> check.isDataValid(inputData)).toList();
		return !result.contains(false);
	}

	/**
	 * Initialize the configured Validity Checks.
	 */
	public void initValidityChecks(String checksOrderOptionName, String inputDataOptionName)
			throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		inputData = programInput.getOptionValue(inputDataOptionName);
		String checksOrder = programInput.getOptionValue(checksOrderOptionName);

		String[] checks = checksOrder.split(" ");
		for (String check: checks) {
			Class validityCheckClass = Class.forName("org.example.validitychecks." + check.strip());
			ValidityCheck validityCheck = (ValidityCheck) validityCheckClass.getDeclaredConstructor().newInstance();
			validityChecks.add(validityCheck);
		}
	}

	private CommandLine parseArguments(String[] args, CommandLineOption[] commandLineOptions) throws ParseException, EmptyProgramInputException {
		Options options = createProgramOptions(commandLineOptions);

		CommandLineParser parser = new DefaultParser();
		CommandLine programInput = parser.parse(options, args);

		if (programInput.getOptions().length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(
					" ",
					"Validates input with configured checks.",
					options,
					"Examples: \n validitychecker -input \"FJD 483\" -order \"NotNullCheck CarRegistrationNumberCheck SocialSecurityCheck\""
							+ "\n validitychecker -input \"19820411-2380\" -order \"NotNullCheck SocialSecurityCheck\"");
			throw new EmptyProgramInputException();
		}

		for (Option option: options.getOptions()) {
			if (!programInput.hasOption(option)) {
				throw new ParseException("Missing option: " + " -" + option.getOpt());
			}
		}

		return programInput;
	}

	private Options createProgramOptions(CommandLineOption[] options) {
		Options programOptions = new Options();
		for (CommandLineOption option: options) {
			Option newOption = createOption(option.name(), option.argName(), option.description());
			programOptions.addOption(newOption);
		}
		return programOptions;
	}

	private Option createOption(String name, String argName, String description) {
		return Option.builder(name)
				.argName(argName)
				.hasArg()
				.desc(description)
				.build();
	}
}
