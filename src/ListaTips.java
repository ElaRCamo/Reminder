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
    private int user;

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

    public void  cargarTips(int user) throws SQLException{
        lisTip.setModel(modeloLista);
        String sql= "SELECT descriptionTip FROM Tip WHERE userId = ?;";
        //System.out.println(sql);
        try (Connection connection = ListaTips.conectar();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,user);
            ResultSet rs = ps.executeQuery();

            modeloLista.removeAllElements();
            while(rs.next()) {
                modeloLista.addElement(rs.getString("descriptionTip"));
            }
        } catch (SQLException ex) {
            System.out.println("No se puede mostrar :( ");
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public void eliminarTip(int user) throws SQLException {
        int indice = lisTip.getSelectedIndex();

        if (indice == -1) {
            JOptionPane.showMessageDialog(null, "Please select an item to delete.");
            return;
        }

        String elementoSeleccionado = modeloLista.getElementAt(indice).toString();
        String sqlSelect = "SELECT idTip FROM Tip WHERE descriptionTip = ? AND userId = ?;";

        try (Connection connection = ListaTips.conectar();
             PreparedStatement psSelect = connection.prepareStatement(sqlSelect)) {

            psSelect.setString(1, elementoSeleccionado);
            psSelect.setInt(2, user);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                int idSeleccionado = rs.getInt("idTip");
                String sqlDelete = "DELETE FROM Tip WHERE idTip = ?;";

                try (PreparedStatement psDelete = connection.prepareStatement(sqlDelete)) {
                    psDelete.setInt(1, idSeleccionado);
                    int rowsAffected = psDelete.executeUpdate();

                    if (rowsAffected > 0) {
                        modeloLista.removeElementAt(indice);
                        JOptionPane.showMessageDialog(null, "Tip successfully removed!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete tip.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No tip found for deletion.");
            }
        } catch (SQLException ex) {
            System.out.println("Error deleting tip: " + ex);
            JOptionPane.showMessageDialog(null, "Error deleting tip: " + ex.getMessage());
        }
    }



    public ListaTips(int user) {
        this.user = user;

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
            cargarTips(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        reminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaTips.this.setVisible(false);
                try {
                    Recordatorio.consultarRecordatorios(user);
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
                    Tip.guardarTips(user);
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
                    eliminarTip(user);
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
                    ToDo.gestionarListaTareas(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}