import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static javax.swing.UIManager.getString;

public class ToDo extends JFrame{
    public JPanel toDoPanel;
    private JPanel tablaPanel;
    private JLabel listaLabel;
    private JButton eliminarButton;
    private JButton nuevoButton;
    private JButton reminderButton;
    private JButton tipsButton;
    private JButton xButton;
    public JTable TableToDo;
    private JButton actualizarButton;
    private JLabel index;
    private JTextField taskName;
    private JButton done;
    private boolean checked;


    public static Connection conectar() {
        String url = "jdbc:jtds:sqlserver://localhost:1433/Reminders;instance=MSSQLSERVER";
        String user = "sa";
        String password = "Grammer1";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void gestionarListaTareas() throws SQLException {
        SwingUtilities.invokeLater(() -> {
            try {
                ToDo toDo = new ToDo();
                toDo.setContentPane(toDo.toDoPanel);
                toDo.setSize(500, 500);
                toDo.setVisible(true);
                toDo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                toDo.setLocationRelativeTo(null);
                toDo.setResizable(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

   public void consultarTareas() throws SQLException {
       String sql = "SELECT descriptionToDo, done FROM ToDo";
       System.out.println(sql);

       try (Connection connection = conectar()) {
           assert connection != null;

           try (Statement st = connection.createStatement()) {
               DefaultTableModel modeloTabla = new DefaultTableModel();
               modeloTabla.addColumn("Task");
               modeloTabla.addColumn("Done");
               TableToDo.setModel(modeloTabla);
               ResultSet rs = st.executeQuery(sql);

               while (rs.next()) {
                   String tarea = rs.getString("descriptionToDo");
                   int hecho = rs.getInt("done");

                   System.out.println("Tarea: " + tarea + ", Hecho: " + hecho);

                   modeloTabla.addRow(new String[]{tarea, String.valueOf(hecho)});
               }
           }
       } catch (SQLException ex) {
           System.out.println("No se puede mostrar :( ");
           JOptionPane.showMessageDialog(null, ex.toString());
       }
   }
    public void actualizarTareas() throws SQLException {
        //identificar fila seleccionada
        int fila = TableToDo.getSelectedRow();

        String tarea = TableToDo.getValueAt(fila, 0).toString();
        int hecho = Integer.parseInt(this.TableToDo.getValueAt(fila, 1).toString());

        String sql = "UPDATE ToDo SET descriptionToDo='" + tarea + "', done ='" + hecho + "' WHERE idToDo =2";
        System.out.println(sql);


        try (Connection connection = conectar()) {
            assert connection != null;

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.executeUpdate();
                consultarTareas();

            } catch (SQLException ex) {
                System.out.println("No se puede actualizar :( ");
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }

    }



    public ToDo() throws SQLException {
        tablaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        //Quitar bordes
        tipsButton.setBorder(BorderFactory.createEmptyBorder());
        reminderButton.setBorder(BorderFactory.createEmptyBorder());
        nuevoButton.setBorder(BorderFactory.createEmptyBorder());
        eliminarButton.setBorder(BorderFactory.createEmptyBorder());



        //Para quitar el borde donde se ubica el tittle
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        try {
            consultarTareas();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        reminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDo.this.setVisible(false);
                try {
                    Recordatorio.consultarRecordatorios();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        tipsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDo.this.setVisible(false);
                try {
                    Tip.verTips();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Tarea tarea = new Tarea();
                listToDo.add(tarea);
                revalidate();*/
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizarTareas();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
