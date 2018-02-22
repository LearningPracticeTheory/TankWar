import java.io.IOException;
import java.util.Properties;

class PropertiesManage {
		
	static Properties props = new Properties();

	static {
		try {
			props.load(PropertiesManage.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private PropertiesManage() {};
	
	public static String getProperty(String key) {
//		props.load(this.getClass().getClassLoader().getResourceAsStream("config/tank.properties"));
		return props.getProperty(key);
	}
}
