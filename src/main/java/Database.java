import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;

class Database {
	public static Connection connection;

	public static void connect() {

		String DatabaseUserName = "root";
		String DatabasePassword = "ENTER YOUR DATABASE PASSWORD HERE";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/doctors_office?serverTimezone=EST", DatabaseUserName, DatabasePassword);
			System.out.println("[INFO] Connected to Database " + DatabaseUserName + "!");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}