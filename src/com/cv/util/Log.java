/**
 * 
 */
package com.cv.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.cv.main.Defaults;

/**
 * @author Daniel Fesalbon
 *
 */
@SuppressWarnings("unused")
public class Log {
	public static Logger getLogger(String clss) {
		Logger logger = Logger.getLogger(clss);
		try {

			logger.setUseParentHandlers(false);
			Handler handler = new ConsoleHandler();
//			FileHandler fh = new FileHandler(Defaults.LOG);

			Formatter format = new Formatter() {
				public String format(LogRecord record) {
					DateFormat simple = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
					String format = simple.format(new Date(record.getMillis()));
					return "[" + format + "] " + "[" + record.getLevel() + "] : " + record.getMessage() + "\n";
				}
			};

//			fh.setFormatter(format);
			handler.setFormatter(format);

			logger.addHandler(handler);
//			logger.addHandler(fh);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return logger;
	}

	public static void LoadProject() {
		Logger logger = getLogger(Log.class.getName());
		logger.info("         ______   ");
		logger.info("        /   ___|              Integrable Facial Recognition System");
		logger.info("      __|  |___   _ ______");
		logger.info("     |__    ___| | /  ____|   Version 1.0");
		logger.info("        |  |     |   /");
		logger.info("        |  |     |  |         Year " + Defaults.ESTYEAR);
		logger.info("        |  |     |  |");
		logger.info("        |__|     |__|         Author: " + Defaults.AUTHOR);
		logger.info(" ");
	}

}
