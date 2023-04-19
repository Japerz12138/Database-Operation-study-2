import java.sql.*;
import javax.swing.JFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ComboBoxDemo {

	public JFrame frame;
	private static JComboBox<String> patientsCB;
	static DefaultComboBoxModel<String> patientsCBModel = new DefaultComboBoxModel<String>();

	public ComboBoxDemo() { initialize(); }

	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 281, 181);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createComboBox();
	}
	
	// Creates a combo box (aka a drop down menu)
	public void createComboBox() {
		patientsCB = new JComboBox<String>();
		patientsCB.setBounds(55, 62, 157, 27);
		frame.getContentPane().add(patientsCB);
		populateComboBox(); // Method is called to populate the combo box right after it's creation
	}
	
	// Performs a 'SELECT' query and populates a combo box (aka a drop down menu) with the results
	public void populateComboBox() {
		try {
			Connection connection = Database.connection; // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM Patients"; // Enter the query
			
			patientsCBModel = new DefaultComboBoxModel<String>();
			
			ResultSet result = stm.executeQuery(query); // Execute the query
			while (result.next()) {
				String patientName = result.getString("patient_name");
				patientsCBModel.addElement(patientName);
			}
			
			patientsCB.setModel(patientsCBModel);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
