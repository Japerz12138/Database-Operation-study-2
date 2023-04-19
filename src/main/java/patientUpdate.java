import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class patientUpdate extends JFrame{
    private JRadioButton byIDRadioButton;
    private JRadioButton byNameRadioButton;
    private JComboBox searchResultCB;
    private JTextField patientIdTF;
    private JTextField patientNameTF;
    private JTextField patientDoBTF;
    private JButton updateButton;
    private JButton cancelButton;
    private JTextField searchTF;
    private JButton searchButton;
    private JRadioButton byDoBRadioButton;
    private JPanel panel1;
    private JButton DELETEButton;

    public patientUpdate() {
        setContentPane(panel1);
        setTitle("Patient Update");
        setSize(450,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        patientIdTF.setEditable(false);

        //Back to main menu
    cancelButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            newMainMenu nmm = new newMainMenu();
            dispose();
        }
    });

    ButtonGroup group = new ButtonGroup();
    group.add(byDoBRadioButton);
    group.add(byIDRadioButton);
    group.add(byNameRadioButton);

        //Search function
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchColum = null;

                //Determine what radio button was selected.
                if (byIDRadioButton.isSelected())
                {
                    searchColum = "patient_id";
                } else if (byNameRadioButton.isSelected()) {
                    searchColum = "patient_name";
                } else if (byDoBRadioButton.isSelected()) {
                    searchColum = "date_of_birth";
                }
                String searchText = searchTF.getText();

                //If the radio button and the search text field is not empty
                if(searchColum != null && !searchText.isEmpty())
                {
                    try {
                        Connection connection = Database.connection; // Connect to database
                        Statement stm = connection.createStatement();
                        String query = "SELECT patient_id, patient_name, date_of_birth FROM doctors_office.patients WHERE "+ searchColum +" = '" + searchText + "'";
                        ResultSet rs = stm.executeQuery(query);

                        //Store the names into an array list
                        ArrayList<String> names = new ArrayList<String>();
                        while (rs.next()){
                            String id = rs.getString("patient_id");
                            String name = rs.getString("patient_name");
                            String dob = rs.getString("date_of_birth");
                            System.out.println("[INFO] Search operation done.");
                            patientIdTF.setText(id);
                            patientNameTF.setText(name);
                            patientDoBTF.setText(dob);
                            names.add("[" + id + "] " + name);
                        }
                        //if the name data returns nothing, then it means no data was returned. show message dialog to notify user
                        if (names.isEmpty()) {
                            JOptionPane.showMessageDialog(patientUpdate.this,
                                    "NO DATA FOUND!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            DefaultComboBoxModel model = new DefaultComboBoxModel(names.toArray());
                            searchResultCB.setModel(model);
                        }

                        //reset the combo box and reset the model
                        searchResultCB.removeAllItems();
                        searchResultCB.setModel(new DefaultComboBoxModel<String>(names.toArray(new String[0])));
                        rs.close();
                        stm.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(patientUpdate.this,
                            "Please select a search option and enter a search term.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        //Update query operation
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = Database.connection; // Connect to database
                    Statement stm = connection.createStatement();

                    //Get what is in the text field and store them into different variable
                    String patientID = patientIdTF.getText();
                    String patientName = patientNameTF.getText();
                    String patientDoB = patientDoBTF.getText();

                    String query = "UPDATE doctors_office.patients SET patient_name = '" + patientName + "', date_of_birth = '" + patientDoB + "' WHERE patient_id = '" + patientID + "'";
                    int result = stm.executeUpdate(query);

                    //If success, show a message dialog, reset the comboBox and reset the text field to blank.
                    if (result > 0) {
                        JOptionPane.showMessageDialog(patientUpdate.this, "Patient information updated successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        searchResultCB.removeAllItems();
                        patientIdTF.setText("");
                        patientNameTF.setText("");
                        patientDoBTF.setText("");
                    } else {
                        JOptionPane.showMessageDialog(patientUpdate.this, "Unable to update patient information!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    stm.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Delete query operation
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ask the user if they really want to delete this patient
                int confirm = JOptionPane.showConfirmDialog(patientUpdate.this, "Are you sure you want to delete this patient?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                //When user chose "YES"
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Connection connection = Database.connection; // Connect to database
                        Statement stm = connection.createStatement();

                        //Get the patientID from patient ID text field
                        String patientID = patientIdTF.getText();

                        String query = "DELETE FROM doctors_office.patients WHERE patient_id = '" + patientID + "'";
                        int result = stm.executeUpdate(query);

                        //If success, show a message dialog, reset the comboBox and reset the text field to blank.
                        if (result > 0) {
                            JOptionPane.showMessageDialog(patientUpdate.this,
                                    "Patient information deleted successfully!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            searchResultCB.removeAllItems();
                            patientIdTF.setText("");
                            patientNameTF.setText("");
                            patientDoBTF.setText("");
                        } else {
                            JOptionPane.showMessageDialog(patientUpdate.this,
                                    "Unable to delete patient information!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        stm.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
