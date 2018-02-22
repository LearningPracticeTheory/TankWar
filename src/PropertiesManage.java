import java.io.IOException;
import java.util.Properties;

class PropertiesManage {
		
	static Properties props = new Properties();

	static {
		try {
			props.load(PropertiesManage.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private PropertiesManage() {};
	
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
}
