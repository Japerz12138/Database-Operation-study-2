import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class newMainMenu extends JFrame{
    private JPanel panel1;
    private JButton textFieldButton;
    private JButton databaseTableButton;
    private JButton comboBoxButton;
    private JButton EXITButton;
    private JButton updatePatientButton;

    public newMainMenu() {
        setContentPane(panel1);
        setTitle("Database Main Menu");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        setLookAndFeel();

        Database.connect(); // Establish connection to database
        setupClosingDBConnection(); // Handles closing the database connection if the user closes the program

        //textField button
        textFieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField tf = new textField();
                dispose();
            }
        });

        //comboBox button
        comboBoxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox cb = new comboBox();
                dispose();
            }
        });

        //Patient Update Button
        updatePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientUpdate pu = new patientUpdate();
                dispose();
            }
        });

        //databaseTable button
        databaseTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table t = new table();
                dispose();
            }
        });

        //Exit button
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


    }

    public static void main(String[] args) {
        newMainMenu mainMenu = new newMainMenu();
    }

    //Set modern windows view
    public void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) { }
    }

    //Establish database connection
    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try { Database.connection.close(); System.out.println("[INFO] Application Closed - DB Connection Closed");
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }, "Shutdown-thread"));
    }



}
