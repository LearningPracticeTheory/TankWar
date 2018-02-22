import java.io.IOException;
import java.util.Properties;
/**
 * 配置文件管理
 * Use Singleton to implement loading property file.
 * And increase read efficiency
 * @author PianoLion
 */
public class PropertiesManage {
		
	static Properties props = new Properties();

	static {
		try {
			props.load(PropertiesManage.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private PropertiesManage() {};
	
	/**
	 * 获取配置文件内容的key值
	 * @param key 配置文件的key值
	 * @return key 返回key对应的value
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
}