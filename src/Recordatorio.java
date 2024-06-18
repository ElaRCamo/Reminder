import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            //System.out.println("Conexión exitosa");
            // Devolver la conexión
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            // Manejar errores de conexión
            e.printStackTrace();
            return null; // Retorna null en caso de error
        }
    }

    public static void consultarRecordatorios(int user) throws SQLException {

        Recordatorio recordatorio = new Recordatorio(user);
        recordatorio.setContentPane(recordatorio.mainPanel);
        recordatorio.setTitle("R E M I N D E R");
        recordatorio.setSize(500, 500);
        recordatorio.setVisible(true);
        recordatorio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        recordatorio.setLocationRelativeTo(null);
        recordatorio.setResizable(false);
        //mandar reminder y autor
        String[] reminderValues = Recordatorio.consultaReminder(user);
        //String[] reminderValues = Recordatorio.consultaReminder(1);
        String reminderDescription = reminderValues[0];
        String autorReminder = reminderValues[1];
        recordatorio.phraseText.setText(reminderDescription);
        recordatorio.autorText.setText(autorReminder);
        //Quitar bordes
        recordatorio.phraseText.setBorder(BorderFactory.createEmptyBorder());
        recordatorio.autorText.setBorder(BorderFactory.createEmptyBorder());
        recordatorio.okButton.setBorder(BorderFactory.createEmptyBorder());
    }

    public static List registroIdReminders(int user) throws SQLException {
        String sql = "SELECT idReminder FROM Reminder WHERE userId = 1 OR userId = ?";
        List reminderIds = new ArrayList<>();

        try (Connection connection = conectar();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user);
            ResultSet rs= ps.executeQuery();
            while (rs.next()) {
                reminderIds.add(rs.getInt("idReminder"));
            }
            System.out.print(reminderIds);
            return reminderIds;
        }

    }

    public static String[] consultaReminder(int user) throws SQLException {
        String sql = "SELECT descriptionReminder,Autor FROM Reminder WHERE idReminder = ? AND (userId = 1 OR userId= ? );";
        try (Connection connection = Login.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            List reminderIds = registroIdReminders(user);

            //Seleccionamos un número aleatorio de id
            int countIDs = reminderIds.size(); // Contar los elementos en la lista
            System.out.println("Number of reminders: " + countIDs);

            if (countIDs > 0) { // Asegurarse de que la lista no esté vacía
                int randomIndex = (int) (Math.random() * countIDs); // Generar un índice aleatorio
                int randomId = (int) reminderIds.get(randomIndex); // Seleccionar el elemento en la lista con ese índice
                System.out.println("Randomly selected reminder ID: " + randomId);

                ps.setInt(1, randomId);
                ps.setInt(2, user);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String reminderDescription = rs.getString(1);
                String autorReminder = rs.getString(2);
                System.out.println(reminderDescription + ", " + autorReminder);
                return new String[]{reminderDescription, autorReminder};
            } else {
                System.out.println("No reminders found for the user.");
                return null;
            }
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    // https://www.youtube.com/watch?v=_cRp1qGVIkU


    public Recordatorio(int user) {

        this.user = user; // Guardar userId

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconReminders.png")));

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

        tipsButton.setBorder(new RoundBorder(new Color(234,224,218), 45,15));
        toDoButton.setBorder(new RoundBorder(new Color(234,224,218), 45,15));


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
                    Tip.verTips(user);
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
                    ToDo.gestionarListaTareas(user);
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
