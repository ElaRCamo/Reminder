import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static com.sun.deploy.trace.TraceLevel.UI;
import static javax.swing.UIManager.getIcon;

public class Recordatorio extends JFrame {
    JButton okButton;
    private JButton toDoButton;
    private JButton tipsButton;
    JTextArea phraseText;
    private JPanel reminderPanel;
    JPanel mainPanel;
    JTextField autorText;
    private JLabel iconTip;
    private JButton xButton;
    static Connection connection;
    public Statement st; //para SELECT
    ResultSet r; //ejecutar query
    static String reminderDescription;
    private String path;



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

    public static void consultarRecordatorios() throws SQLException {
        Recordatorio recordatorio = new Recordatorio();
        recordatorio.setContentPane(recordatorio.mainPanel);
        recordatorio.setTitle("R E M I N D E R");
        recordatorio.setSize(500, 500);
        recordatorio.setVisible(true);
        recordatorio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        recordatorio.setLocationRelativeTo(null);
        recordatorio.setResizable(false);
        //mandar reminder y autor
        String[] reminderValues = Recordatorio.consultaReminder(1);
        String reminderDescription = reminderValues[0];
        String autorReminder = reminderValues[1];
        recordatorio.phraseText.setText(reminderDescription);
        recordatorio.autorText.setText(autorReminder);
        //Quitar bordes
        recordatorio.phraseText.setBorder(BorderFactory.createEmptyBorder());
        recordatorio.autorText.setBorder(BorderFactory.createEmptyBorder());
        recordatorio.okButton.setBorder(BorderFactory.createEmptyBorder());
    }

    public static int numRegistros() throws SQLException {
        try (Connection connection = conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                //Obtenemos el numero total de registros y lo convertimos a int
                ResultSet rs = st.executeQuery("SELECT count(idReminder) FROM Reminder");
                // Mover al primer resultado (ya que es un solo valor)
                rs.next();
                // Obtener y retornar el valor como entero
                return rs.getInt(1);
            }
        }
    }

    public static String[] consultaReminder(int i) throws SQLException {
        try (Connection connection = conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                int numRecordatorio = numRegistros();
                //Seleccionamos un número aleatorio de id
                int randomId = (int) (Math.floor(Math.random() * (numRecordatorio - 1 + 1 + 1) + 1));
                //System.out.println("Numero de randomid:"+randomId);
                ResultSet r = st.executeQuery("SELECT descriptionReminder,Autor FROM Reminder WHERE idReminder = " + randomId + ";");
                r.next();
                String reminderDescription = r.getString(1);
                String autorReminder = r.getString(2);
                System.out.println(reminderDescription + ", " + autorReminder);
                return new String[]{reminderDescription, autorReminder};
            }
        }
    }


    // https://www.youtube.com/watch?v=_cRp1qGVIkU


    public Recordatorio() {

        reminderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        phraseText.setLineWrap(true);
        phraseText.setWrapStyleWord(true);
        phraseText.setEditable(false); //Para que no se pueda editar el texto

        // Configurar alineación del contenido en el JTextArea
        phraseText.setAlignmentX(Component.CENTER_ALIGNMENT);
        phraseText.setAlignmentY(Component.CENTER_ALIGNMENT);
        //Para que el textArea no se pueda editar
        autorText.setEditable(false);
        autorText.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);


        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon("C:\\Users\\Ex-Reyes-M\\IdeaProjects\\Reminder\\src\\carita_feliz.png");
                UIManager.put("OptionPane.background", new Color(234,224,218));
                UIManager.put("Panel.background", new Color(234,224,218));
                UIManager.put("OptionPane.messageFont", new Font("Segoe Script", Font.BOLD,20));
                UIManager.put("OptionPane.messageForeground", new Color(64,39,16));

                JOptionPane.showMessageDialog(okButton, " Be happy!!!", "",JOptionPane.PLAIN_MESSAGE, icon);

                String[] reminderValues = new String[0];
                try {
                    reminderValues = Recordatorio.consultaReminder(1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String reminderDescription = reminderValues[0];
                String autorReminder = reminderValues[1];
                phraseText.setText(reminderDescription);
                autorText.setText(autorReminder);
            }
        });

        tipsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recordatorio.this.setVisible(false);
                try {
                    Tip.verTips();
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
        toDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recordatorio.this.setVisible(false);
                try {
                    ToDo.gestionarListaTareas();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
