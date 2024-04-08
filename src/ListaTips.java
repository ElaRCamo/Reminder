import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
    private JButton xButton;
    private JScrollPane sPanel;
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

    public void eliminarTip() throws SQLException {
        int indice = lisTip.getSelectedIndex();

        if (indice == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un elemento para eliminar.");
            return;
        }

        String elementoSeleccionado = modeloLista.getElementAt(indice).toString();
        String sqlSelect = "SELECT TOP 1 idTip FROM Tip WHERE descriptionTip ='"+elementoSeleccionado+"';";
        System.out.println(sqlSelect);

        try (Connection connection = ListaTips.conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery(sqlSelect);
                rs.next();
                int idSeleccionado = rs.getInt(1);

                String sqlDelete = "DELETE FROM Tip WHERE idTip = " + idSeleccionado + ";";
                System.out.println(sqlSelect);
                st.executeUpdate(sqlDelete);
                modeloLista.removeElementAt(indice);
                JOptionPane.showMessageDialog(null, "Tip eliminado exitosamente");
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo eliminar :( ");
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }


    public ListaTips() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconReminders.png")));
        tablaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        toDoButton.setBorder(new RoundBorder(new Color(234,224,218), 60,20));
        reminderButton.setBorder(new RoundBorder(new Color(234,224,218), 60,20));
        //Quitar bordes
        nuevoButton.setBorder(BorderFactory.createEmptyBorder());
        eliminarButton.setBorder(BorderFactory.createEmptyBorder());
        sPanel.setBorder(BorderFactory.createEmptyBorder());

        sPanel.getVerticalScrollBar().setUI(new CustomScrollBar());
        sPanel.getHorizontalScrollBar().setUI(new CustomScrollBar());

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        //determinar tamaño del panel para que el scroll se adapte
        setPreferredSize(new Dimension(300,600));

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
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminarTip();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        toDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaTips.this.setVisible(false);
                try {
                    ToDo.gestionarListaTareas();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
