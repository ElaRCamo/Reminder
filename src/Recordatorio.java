import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Recordatorio extends JFrame {
    private JButton okButton;
    private JButton toDoButton;
    private JButton tipsButton;
    private JTextField phraseText;
    private JPanel reminderPanel;
    private JPanel mainPanel;
    private JLabel icon;
    private JTextField autorText;
    static Connection connection;
    PreparedStatement ps; //para INSERT TO
    Statement st; //para SELECT
    ResultSet r; //ejecutar query
    static String reminderDescription;


    private static Connection conectar() {
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

    public static int numRegistros() throws SQLException {
        try (Connection connection = conectar();
             Statement st = connection.createStatement()) {
            //Obtenemos el numero total de registros y lo convertimos a int
            ResultSet rs = st.executeQuery("SELECT count(idReminder) FROM Reminder");
            // Mover al primer resultado (ya que es un solo valor)
            rs.next();
            // Obtener y retornar el valor como entero
            return rs.getInt(1);
        }
    }
    public static String[] consultaReminder(int i) throws SQLException {
        try (Connection connection = conectar();
             Statement st = connection.createStatement()) {
            int numRecordatorio = numRegistros();
            //Seleccionamos un número aleatorio de id
            int randomId = (int) (Math.floor(Math.random() * (numRecordatorio - 1 + 1 + 1) + 1));
            //System.out.println("Numero de randomid:"+randomId);
            ResultSet r = st.executeQuery("SELECT descriptionReminder,Autor FROM Reminder WHERE idReminder = " + randomId + ";");
            r.next();
            String reminderDescription = r.getString(1);
            String autorReminder = r.getString(2);
            System.out.println(reminderDescription+ ", "+autorReminder);
            return new String[] {reminderDescription,autorReminder};
        }
    }


    // min 28 https://www.youtube.com/watch?v=_cRp1qGVIkU
   /* public void insertarTip() throws SQLException {
        conectar();
        ps = connection.prepareStatement("INSERT INTO Tip VALUES (?)");
        ps.setString(1,".");
    }*/

    public Recordatorio() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(okButton,"Be happy!!!");
            }
        });
    }

    public static void main(String[] args) throws SQLException {
        Recordatorio recordatorio = new Recordatorio();
        recordatorio.setContentPane(recordatorio.mainPanel);
        recordatorio.setTitle("REMINDER");
        recordatorio.setSize(480,480);
        recordatorio.setVisible(true);
        recordatorio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        recordatorio.setLocationRelativeTo(null);
        //mandar reminder y autor
        String[] reminderValues = consultaReminder(1);
        String reminderDescription = reminderValues[0];
        String autorReminder = reminderValues[1];
        recordatorio.phraseText.setText(reminderDescription);
        recordatorio.autorText.setText(autorReminder);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
