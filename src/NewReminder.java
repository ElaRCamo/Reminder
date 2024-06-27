import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class NewReminder extends JFrame{
    private JPanel newReminderPanel;
    private JButton btnGoBack;
    private JButton btnReadReminder;
    private JPanel reminderPanel;
    private JTextArea newPhraseText;
    private JTextField newAutorText;
    private JButton saveRButton;
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
        newReminder.setResizable(false);
    }

    public void  guardarReminder(int user) throws SQLException{

        String reminder = newPhraseText.getText();
        String author = newAutorText.getText();

        //System.out.println(" reminder:"+reminder +" autor:"+ author);
        try (Connection connection = Tip.conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Reminder(descriptionReminder, Autor,userId) VALUES(?,?,?) ");
                ps.setString(1, reminder);
                ps.setString(2, author);
                ps.setInt(3, user);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Reminder saved successfully");//null se refiere a la posicion del msj
                newPhraseText.setText("");//Para que el espacio quede vacio
                newAutorText.setText("");

            } catch (SQLException ex) {
                System.out.println("Could not save :(");
                JOptionPane.showMessageDialog(null, ex.toString());
                throw new RuntimeException(ex);
            }
        }
    }

    public NewReminder(int user) {

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconReminders.png")));

        //Botones
        newAutorText.setBorder(BorderFactory.createEmptyBorder());
        saveRButton.setBorder(BorderFactory.createEmptyBorder());
        btnGoBack.setBorder(new RoundBorder(new Color(234,224,218), 60,20));
        btnReadReminder.setBorder(new RoundBorder(new Color(234,224,218), 60,20));

        UIManager.put("OptionPane.background", new Color(234,224,218));
        UIManager.put("Panel.background", new Color(234,224,218));
        UIManager.put("OptionPane.messageFont", new Font("Segoe Script", Font.BOLD,18));
        UIManager.put("OptionPane.messageForeground", new Color(64,39,16));

        //Para quitar el borde donde se ubica el tittle
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        newAutorText.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);


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
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    System.exit(0);
            }
        });
        saveRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarReminder(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnReadReminder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewReminder.this.setVisible(false);
                try {
                    ListReminders.gestionarReminders(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
