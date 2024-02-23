import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ListaTips extends JFrame {
    public JPanel listPanel;
    private JLabel listaLabel;
    private JButton toDoButton;
    private JButton reminderButton;
    private JButton nuevoButton;
    private JButton eliminarButton;
    private JPanel tablaPanel;
    public JList lisTip;
    DefaultListModel modeloLista = new DefaultListModel();

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
            System.out.println("Conexión exitosa");
            // Devolver la conexión
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            // Manejar errores de conexión
            e.printStackTrace();
            return null; // Retorna null en caso de error
        }
    }

    public void  cargarTips() throws SQLException{
        lisTip.setModel(modeloLista);
        String sql= "SELECT descriptionTip FROM Tip;";
        //System.out.println(sql);
        try (Connection connection = ListaTips.conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                modeloLista.removeAllElements();
                while(rs.next())
                    modeloLista.addElement(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("No se puede mostrar :( ");
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public ListaTips() {
        tablaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        //Quitar bordes
        toDoButton.setBorder(BorderFactory.createEmptyBorder());
        reminderButton.setBorder(BorderFactory.createEmptyBorder());
        nuevoButton.setBorder(BorderFactory.createEmptyBorder());
        eliminarButton.setBorder(BorderFactory.createEmptyBorder());

        try {
            cargarTips();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        reminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaTips.this.setVisible(false);
                try {
                    Recordatorio.consultarRecordatorios();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaTips.this.setVisible(false);
                try {
                    Tip.guardarTips();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
