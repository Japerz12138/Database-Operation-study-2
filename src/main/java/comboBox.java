import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class comboBox extends JFrame{
    private JComboBox patientsCB;
    private JPanel panel1;
    private JButton doneButton;
    private JButton getPatientInfoButton;
    static DefaultComboBoxModel<String> patientsCBModel = new DefaultComboBoxModel<String>();
    public comboBox() {
        //Initialize JFrame
        setContentPane(panel1);
        setTitle("Patient List");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        populateComboBox();

        //Get Patient Information
        getPatientInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get the current patientName from comboBox selection
                String patientName = String.valueOf(patientsCBModel.getSelectedItem());
                //Using the patientName to find the corresponding data from database using query
                String query = "SELECT * FROM doctors_office.patients WHERE patient_name = \""+patientName+"\"";
                try{
                    Connection connection = Database.connection; // Connect to database
                    PreparedStatement stm = connection.prepareStatement(query); //Query Statement

                    ResultSet result = stm.executeQuery(query); //Store the data into result
                    if (result.next())
                    {
                        //Get patient ID from the result
                        int patientID = result.getInt("patient_id");
                        //Get DoB from the result
                        String patientDOB = result.getString("date_of_birth");
                        //Show up a message dialog that shows the patient information
                        JOptionPane.showMessageDialog(null,
                                "Patient Name: " + patientName +" \n" + "Patient ID: " + patientID +" \n" + "Patient DoB: " + patientDOB,
                                patientName + "'s Information",
                                JOptionPane.PLAIN_MESSAGE);
                    }

                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        //Back button
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMainMenu nmm = new newMainMenu();
                dispose();
            }
        });
    }

    //Get data from database and push it to the PatientsCBModel
    public void populateComboBox() {
        try {
            Connection connection = Database.connection; // Connect to database
            Statement stm = connection.createStatement(); // Create statement
            String query = "SELECT * FROM Patients"; // Enter the query


            System.out.println("[INFO] Table connected, showing in combo box.");

            patientsCBModel = new DefaultComboBoxModel<String>();

            ResultSet result = stm.executeQuery(query); // Execute the query


            while (result.next()) {
                String patientName = result.getString("patient_name");
                patientsCBModel.addElement(patientName);
            }

            patientsCB.setModel(patientsCBModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
