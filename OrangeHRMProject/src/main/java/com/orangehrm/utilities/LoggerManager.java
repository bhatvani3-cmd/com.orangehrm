package com.orangehrm.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {

	//This method returns a logger isntance for the provided class
	public static Logger getLoggerManager(Class<?> claz) {
		return LogManager.getLogger();
	}
}
