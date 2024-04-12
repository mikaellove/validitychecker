package org.example.validitychecks;

import org.example.validitychecks.interfaces.ValidityCheck;

public class CarRegistrationNumberCheck extends ValidityCheckBase implements ValidityCheck {

	@Override
	public boolean isDataValid(String data) {
		if (data == null) {
			logFailedCheck(className(), "Input is null.");
			return false;
		}

		if (!data.matches("^[A-Z]{3} \\d{3}$")) {
			logFailedCheck(className(), "Input is not a valid registration number.");
			return false;
		}

		String letters = data.substring(0, 3);
		if (!letters.matches("^[^IQVÅÄÖ]*$") || !letters.equals(letters.toUpperCase())) {
			logFailedCheck(className(), "Not a valid car registration number. Prohibited letter used.");
			return false;
		}

		return true;
	}
}
