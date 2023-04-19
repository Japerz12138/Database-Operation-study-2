import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class table extends JFrame{
    private JTable patientsTable;
    private JButton doneButton;
    private JPanel panel1;

    public table(){
        setContentPane(panel1);
        setTitle("Database");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        populateTable();

        //Back button
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMainMenu nmm = new newMainMenu();
                dispose();
            }
        });
    }

    //Get data from database and display in the patientsTable
    public void populateTable() {
        try {
            Connection connection = Database.connection;
            String query = "SELECT * FROM Patients";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet result = stm.executeQuery(query);
            patientsTable.setModel(DbUtils.resultSetToTableModel(result));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
