import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class textField extends JFrame{
    private JPanel panel1;
    private JTextField patientIdTF;
    private JTextField patientNameTF;
    private JTextField dateofBirthTF;
    private JButton submitButton;
    private JButton cancelButton;

    public textField() {
        setContentPane(panel1);
        setTitle("Patient Adder");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        //Submit Button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomPatient();
            }
        });

        //Back Button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMainMenu nmm = new newMainMenu();
                dispose();
            }
        });

        //This will make sure the user types only integers
        patientIdTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                patientIdTF.setEditable(Character.isDigit(c) || Character.isWhitespace(c) || Character.isISOControl(c));
                //super.keyPressed(e);
            }
        });
    }

    //Add Patient Operation
    public void addCustomPatient() {
        try {
            Connection connection = Database.connection;
            String query = "INSERT INTO Patients VALUES (?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);

            // 'Integer.parseInt(Insert String Here)' turns the 'String' between the parenthesis into an 'int' (unless there are letters inside of the String, then it will crash)
            stm.setInt(1, Integer.parseInt(patientIdTF.getText())); // patientIDTF.getText() gets the text that is inside of the patient id text field
            stm.setString(2, patientNameTF.getText()); // patientNameTF.getText() gets the text that is inside of the patient name text field
            stm.setString(3, dateofBirthTF.getText()); // dateOfBirthTF.getText() gets the text that is inside of the dateOfBirth text field
            stm.executeUpdate();
            // The line below is ran if the query executes successfully. It shows a JOptionPane (an alert) telling the user that the patient has been added to the database.
            JOptionPane.showMessageDialog(null, "The new patient was added to the database!", "Patient Added!", JOptionPane.DEFAULT_OPTION);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
