import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Register  extends JFrame{
    private JPanel registerPanel;
    private JPanel reminderPanel;
    private JButton registerUserBtn;
    private JLabel emailLabel;
    private JTextField emailInput;
    private JLabel passwordLabel;
    private JPasswordField passwordInput;
    private JButton btnBack;
    private JLabel iconTip;
    private JButton xButton;
    private JLabel instLabel;
    private JTextField nameInput;
    private JLabel nameLabel;
    private JPanel mainPanel;

    public static Connection conectar() {
        String url = "jdbc:jtds:sqlserver://localhost:1433/Reminders;instance=MSSQLSERVER";
        String user = "sa";
        String password = "Grammer1";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void registrase() {

        Register register = new Register();
        register.setContentPane(register.mainPanel);
        register.setSize(500, 500);
        register.setVisible(true);
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        register.setLocationRelativeTo(null);
        register.setResizable(false);
    }

    public Register() {

        // Quitar bordes
        nameInput.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
        emailInput.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
        passwordInput.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
        registerUserBtn.setBorder(BorderFactory.createEmptyBorder());
        btnBack.setBorder(BorderFactory.createEmptyBorder());

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register.this.setVisible(false);
                Login login = null;
                try {
                    login = new Login();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                login.setVisible(true);
            }
        });
    }

    public void createUIComponents(){

    }
}
