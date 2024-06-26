import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame{
    private JPanel loginPanel;
    private JPanel reminderPanel;
    private JButton loginButton;
    private JLabel iconTip;
    private JButton xButton;
    private JTextField emailTextF;
    private JTextField passwordTF;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    static Connection connection;
    public int userId;

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
            connection = DriverManager.getConnection(url, user, password);
            //System.out.println("Conexión exitosa");
            // Devolver la conexión
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            // Manejar errores de conexión
            e.printStackTrace();
            return null; // Retorna null en caso de error
        }
    }

    public Login() throws SQLException {
        setContentPane(loginPanel);
        setUndecorated(true);
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconReminders.png")));


        // Quitar bordes
        emailTextF.setBorder(BorderFactory.createEmptyBorder());
        passwordTF.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setBorder(BorderFactory.createEmptyBorder());

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailTextF.getText();
                String password = passwordTF.getText();
                //String password = new String(passwordTF.getPassword());

                try {
                    Integer user = readUser(email, password);
                    Login.this.setVisible(false);

                    if (user != null) {
                        Recordatorio.consultarRecordatorios(user);
                    } else {
                        System.out.println("User not found");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static Integer  readUser(String email, String password) throws SQLException {
        try (Connection connection = conectar()) {
            assert connection != null;
            String query = "SELECT idUser FROM Users WHERE email = ? AND password= ? ;";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, email);
                pst.setString(2, password);

                try (ResultSet r = pst.executeQuery()) {
                    if (r.next()) {
                        return r.getInt("idUser");
                    } else {
                        return null; // No se encontró el usuario
                    }
                }
            }
        }
    }
}
