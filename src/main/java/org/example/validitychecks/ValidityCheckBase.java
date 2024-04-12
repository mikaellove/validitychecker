package org.example.validitychecks;

import org.tinylog.Logger;

public abstract class ValidityCheckBase {
	protected void logFailedCheck(String className, String message) {
		Logger.info(className + " failed: '{}'", message);
	}

	protected String className() {
		return getClass().getSimpleName();
	}
}
