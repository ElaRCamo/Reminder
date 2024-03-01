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

   /* public void consultarTareas() throws SQLException {
        TableToDo.setModel(modeloTabla);
        String sql="SELECT descriptionToDo, done FROM ToDo";
        try (Connection connection = conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                modeloTabla.setRowCount(0);// Limpiar la tabla

                while (rs.next()) {
                    String descripcion = rs.getString("descriptionToDo");
                    int done = rs.getInt("done");
                    System.out.println("Descripción: " + descripcion + ", Done: " + done);
                    // Agregar una fila al modelo de la tabla
                    modeloTabla.addRow(new Object[]{descripcion, done});

                }
            }
        }catch (SQLException ex) {
            System.out.println("No se puede mostrar :( ");
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }*/
  /* public void consultarTareas() throws SQLException {
       String sql="SELECT descriptionToDo, done FROM ToDo";
       System.out.println(sql);
       try (Connection connection = conectar()) {
           assert connection != null;
           try (Statement st = connection.createStatement()) {
               DefaultTableModel modeloTabla = new DefaultTableModel();
               modeloTabla.addColumn("Task");
               modeloTabla.addColumn("Done");
               TableToDo.setModel(modeloTabla);
               ResultSet rs = st.executeQuery(sql);

               String [] datos = new String[2];
               while (rs.next()) {
                   datos[0] = getString(1);
                   datos[1] = getString(2);
                   System.out.println(datos);
                   modeloTabla.addRow(datos);
               }
           }
       }catch (SQLException ex) {
           System.out.println("No se puede mostrar :( ");
           JOptionPane.showMessageDialog(null, ex.toString());
       }
   }*/
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
    }
}
