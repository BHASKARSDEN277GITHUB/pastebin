package snap.bhaskar.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class Bundle {
	Locale loc = null;
	ResourceBundle bun = null;
	
	public Bundle(){
		loc = new Locale("en","UK");
		bun = ResourceBundle.getBundle("config",loc);
	}
	public String getUserName(){
		String userName=bun.getString("user");
		return userName;
	}
	public String getIp(){
		String userName=bun.getString("ip");
		return userName;
	}
	
	public String getPassword(){
		String password=bun.getString("password");
		return password;
	}
	public String getDB(){
		String db=bun.getString("db");
		return db;
	}
	public String getDriver(){
		String driver=bun.getString("driver");
		return driver;
	}
}
