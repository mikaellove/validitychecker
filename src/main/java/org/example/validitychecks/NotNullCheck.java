package org.example.validitychecks;

import org.example.validitychecks.interfaces.ValidityCheck;

public class NotNullCheck extends ValidityCheckBase implements ValidityCheck {

	@Override
	public boolean isDataValid(String data) {
		if (data == null) {
			logFailedCheck(className(), "Inputed data is null");
			return false;
		}

		return true;
	}
}
