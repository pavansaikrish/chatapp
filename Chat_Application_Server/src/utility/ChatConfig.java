package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ChatConfig {
	public static final Properties PROPERTIES;
	public static InputStream inputStream = null;
	static {
		try {
			inputStream = new FileInputStream("./src/utility/configuration.properties");
		}
		catch (FileNotFoundException e) {
			System.out.println("Exception occured while inputstreaming \"configuration.properties\"");
		}
		PROPERTIES = new Properties();
		try {
			PROPERTIES.load(inputStream);
		}
		catch (IOException e) {
			System.out.println("Exception occured while loading properties attribute of chatconfig");
		}
	}
}
