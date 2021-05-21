package mx.daro.himnario.properties.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.util.StringUtils;

public class PropertiesUtil {

	private static final String DATA_FILE="dataFile";

	public static void addPropertyToFile(String key, String value) {
		if(!StringUtils.hasText(key)) {
			Properties p = getProperties();
			p.put(key, value);
			savePropertyFile(p);
		}
	}
	
	private static void savePropertyFile(Properties p) {
		if(p!=null) {
			try {
				FileOutputStream fos = new FileOutputStream(System.getProperty(DATA_FILE));
				p.store(fos, "DO NOT CHANGE THE CONTENT OF THIS FILE");
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getProperty(String key) {
		Properties p = getProperties();
		return p.getProperty(key);
	}
	
	public static Properties getProperties() {
		Properties p = new Properties();
		FileInputStream fis;
		try {
			File f = new File(System.getProperty(DATA_FILE));
			if(f.exists()) {
				fis = new FileInputStream(f);
				p.load(fis);
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
}
