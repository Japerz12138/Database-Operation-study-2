import java.sql.*;
import java.util.Scanner;

public class VariousQueriesAndScanner {

	public static void main(String[] args) {
		Database.connect();
		setupClosingDBConnection();
		
		// getPatients();
		// addPatient();
		// addAppointment();
		// updateAppointmentTime();
		// deleteAppointmentTime();
		addCustomPatient(); // This method uses the Scanner to take input from the user
	}
	
	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            try { Database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
				} catch (SQLException e) { e.printStackTrace(); }
	        }
	    }, "Shutdown-thread"));
	}
	
	public static void getPatients() {
		try {
			Connection connection = Database.connection; // Connect to database
			String query = "SELECT * FROM Patients WHERE patient_name = '" + "Thomas Tank" + "'"; // Enter the query
			Statement stm = connection.createStatement(); // Create statement
			ResultSet result = stm.executeQuery(query); // Execute the query
			
			while (result.next()) {
				System.out.println("Patient ID: " + result.getInt("patient_id"));
				System.out.println("Patient Name: " + result.getString("patient_name"));
				System.out.println("Patient Date of Birth: " + result.getString("date_of_birth"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void addPatient() {
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO Patients VALUES (?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setInt(1, 9);
			stm.setString(2, "Cosmo Patio");
			stm.setString(3, "05/22/2000");
			stm.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void addAppointment() {
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO Appointments VALUES (?, ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setInt(1, 4); // appointment_id
			stm.setString(2, "04/28/2023 11:00 AM"); // appointment_time
			stm.setInt(3, 1); // patient_id (FK)
			stm.setInt(4, 7); // doctor_id (FK)
			stm.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void updateAppointmentTime() {
		try {
			Connection connection = Database.connection;
			String query = "UPDATE Appointments SET appointment_time = ? WHERE appointment_id = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, "05/12/2023 8:30 AM"); // appointment_time (first question mark)
			stm.setInt(2, 3); // appointment_id (second question mark)
			stm.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void deleteAppointmentTime() {
		try {
			Connection connection = Database.connection;
			String query = "DELETE FROM Appointments WHERE doctor_id = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setInt(1, 5); // doctor_id (only question mark)
			stm.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void addCustomPatient() {
		Scanner getInput = new Scanner(System.in);
		System.out.println("Enter Patient ID: ");
		int patientID = getInput.nextInt();
		System.out.println("Enter Patient Name: ");
		getInput.nextLine(); // We need to have this before the first 'nextLine()' so it doesn't skip any of the later '.nextLine()'
		String patientName = getInput.nextLine();
		System.out.println("Enter Patient Date of Birth: ");
		String dateOfBirth = getInput.nextLine();
		getInput.close();
		
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO Patients VALUES (?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setInt(1, patientID);
			stm.setString(2, patientName);
			stm.setString(3, dateOfBirth);
			stm.executeUpdate();
			System.out.println("The new patient was added to the database!");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
