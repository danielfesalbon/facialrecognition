/**
 * 
 */
package com.cv.main;

/**
 * @author Daniel Fesalbon
 *
 */
public interface Defaults {

	static final String PATH = System.getProperty("user.dir");

	static final String ROOT = PATH + "\\root";

	static final String FILES = ROOT + "\\files";

	static final String LBPCSCDE_FRNTLFACE_CLASSFR = FILES + "\\lbpcascade_frontalface.xml";

	static final String CLASSIFIED = ROOT + "\\classified";

	static final String IMAGES = ROOT + "\\images";

	static final String COPYRIGHT = "© 2019 Daniel Fesalbon";

	static final String AUTHOR = "Daniel Fesalbon";

	static final String DEVELOPER = "Daniel Fesalbon";

	static final String ESTYEAR = "2019";

	static final String FACES = FILES + "\\faces.xml";
	
	static final String LOG = ROOT + "\\logs.log";

}
