import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NewReminder extends JFrame{
    private JPanel newReminderPanel;
    private JButton btnGoBack;
    private JButton btnReadReminder;
    private JPanel reminderPanel;
    private JTextArea newPhraseText;
    private JTextField newAutorText;
    private JButton saveRButton;
    private JButton btnMoreReminder;
    private JLabel iconTip;
    private JButton xButton;
    private JLabel saveLabel;

    public static Connection conectar() {
        // Información de conexión a tu base de datos SQL Server
        String url = "jdbc:jtds:sqlserver://localhost:1433/Reminders;instance=MSSQLSERVER";
        String user = "sa";
        String password = "Grammer1";
        try {
            // Cargar el controlador JDBC
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            // Establecer la conexión
            //Connection connection = DriverManager.getConnection(url, user, password);
            Connection connection = DriverManager.getConnection(url, user, password);
            //System.out.println("Conexión exitosa");
            // Devolver la conexión
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            // Manejar errores de conexión
            e.printStackTrace();
            return null; // Retorna null en caso de error
        }
    }

    public static void saveNewReminder(int user) throws SQLException {
        NewReminder newReminder = new NewReminder(user);
        newReminder.setContentPane(newReminder.newReminderPanel);
        newReminder.setTitle("Save a reminder");
        newReminder.setSize(500, 500);
        newReminder.setVisible(true);
        newReminder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newReminder.setLocationRelativeTo(null);
    }

    public NewReminder(int user) {


        btnGoBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    NewReminder.this.setVisible(false);
                    try {
                        Recordatorio.consultarRecordatorios(user);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });
    }
}
