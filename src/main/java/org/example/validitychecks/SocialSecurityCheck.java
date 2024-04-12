package org.example.validitychecks;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.example.validitychecks.interfaces.ValidityCheck;

public class SocialSecurityCheck extends ValidityCheckBase implements ValidityCheck {
	@Override
	public boolean isDataValid(String data) {
		if (data.isBlank() || data == null) {
			logFailedCheck(className(), "Input data is empty or null.");
			return false;
		}

		String parsedSocialSecurityNumber = data.replace("-", "");
		if (parsedSocialSecurityNumber.length() != 12) {
			logFailedCheck(className(), "Social security number is invalid.");
			return false;
		}

		String code = parsedSocialSecurityNumber.substring(2);
		LuhnCheckDigit check = new LuhnCheckDigit();
		if (!check.isValid(code)) {
			logFailedCheck(className(), "Invalid social security control number.");
			return false;
		}

		return true;
	}
}
