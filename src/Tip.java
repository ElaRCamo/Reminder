import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Tip extends JFrame {
    public JPanel panelTip;
    private JLabel labelTip;
    private JLabel iconTip;
    JTextArea textTip;
    private JButton btSaveTip;
    private JButton btConsultTip;
    private JButton bnReminder;
    private JButton btToDo;
    private JPanel saveTipPanel;
    private JButton xButton;
    static Connection connection;
    PreparedStatement ps; //para INSERT TO
    Statement st; //para SELECT
    ResultSet r; //ejecutar query
    DefaultListModel mod = new DefaultListModel();
    private int user; // Campo para guardar el userId

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
            System.out.println("Conexión exitosa");
            // Devolver la conexión
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            // Manejar errores de conexión
            e.printStackTrace();
            return null; // Retorna null en caso de error
        }
    }

    public static void guardarTips(int user) throws SQLException {
        Tip newTip = new Tip(user);
        newTip.setContentPane(newTip.panelTip);
        newTip.setTitle("Save a tip");
        newTip.setSize(500, 500);
        newTip.setVisible(true);
        newTip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newTip.setLocationRelativeTo(null);
    }

    public static void verTips(int user) throws SQLException {
        ListaTips newTip = new ListaTips(user);
        newTip.setContentPane(newTip.listPanel);
        newTip.setTitle("tips Guardados");
        newTip.setSize(500, 500);
        newTip.setVisible(true);
        newTip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newTip.setLocationRelativeTo(null);
        newTip.setResizable(false);
    }

    public void  guardarTip(int user) throws SQLException{

        String tip = textTip.getText();
        System.out.println(tip);
        try (Connection connection = Tip.conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Tip(descriptionTip, userId) VALUES(?,?) ");
                ps.setString(1, tip);
                ps.setInt(2, user);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Tip guardado exitosamente");//null se refiere a la posicion del msj
                textTip.setText("");//Para que el espacio quede vacio

            } catch (SQLException ex) {
                System.out.println("No se pudo guardar :( ");
                JOptionPane.showMessageDialog(null, ex.toString());
                throw new RuntimeException(ex);
            }
        }
    }


    public Tip(int user) {
        this.user = user; // Guardar userId

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconReminders.png")));

        saveTipPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        textTip.setLineWrap(true);
        textTip.setWrapStyleWord(true);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        bnReminder.setBorder(new RoundBorder(new Color(234,224,218), 45,15));
        btToDo.setBorder(new RoundBorder(new Color(234,224,218), 45,15));


        UIManager.put("OptionPane.background", new Color(234,224,218));
        UIManager.put("Panel.background", new Color(234,224,218));
        UIManager.put("OptionPane.messageFont", new Font("Segoe Script", Font.BOLD,20));
        UIManager.put("OptionPane.messageForeground", new Color(64,39,16));

        bnReminder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tip.this.setVisible(false);
                try {
                    Recordatorio.consultarRecordatorios(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btSaveTip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarTip(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btConsultTip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tip.this.setVisible(false);
                try {
                    verTips(user);
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
        btToDo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tip.this.setVisible(false);
                try {
                    ToDo.gestionarListaTareas(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
