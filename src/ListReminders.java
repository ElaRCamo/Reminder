import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ListReminders extends JFrame{
    private final int user;
    private JPanel readRemindersPanel;
    private JPanel tablaPanel;
    private JLabel listaLabel;
    private JButton eliminarButton;
    private JButton nuevoButton;
    private JScrollPane sPanel;
    private JTable remindersTable;
    DefaultTableModel modeloTabla = new DefaultTableModel();
    private JButton reminderButton;
    private JButton tipsButton;
    private JButton xButton;

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
    public static void gestionarReminders(int user) throws SQLException {
        SwingUtilities.invokeLater(() -> {
            ListReminders listRem = new ListReminders(user);
            listRem.setContentPane(listRem.readRemindersPanel);
            listRem.setSize(500, 500);
            listRem.setVisible(true);
            listRem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            listRem.setLocationRelativeTo(null);
            listRem.setResizable(false);
        });
    }



    public void listReminders(int user) throws SQLException{

            String sql = "SELECT idReminder, descriptionReminder, Autor FROM Reminder WHERE userId = ?";

            try (Connection connection = conectar();
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                DefaultTableModel modeloTabla = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column != 0; // Permitir editar todas las columnas excepto la primera (ID)
                    }
                };
                modeloTabla.addColumn("ID");
                modeloTabla.addColumn("Reminder");
                modeloTabla.addColumn("Autor");
                remindersTable.setModel(modeloTabla);

                // Personalizar el encabezado de la tabla
                JTableHeader header = remindersTable.getTableHeader();
                header.setBackground(new java.awt.Color(255, 255, 255)); // Cambiar color de fondo
                header.setForeground(new java.awt.Color(46,36,9)); // Cambiar color del texto
                java.awt.Font headerFont = new java.awt.Font("Segoe Script", java.awt.Font.BOLD, 16);
                header.setBorder(new LineBorder(new java.awt.Color(234, 224, 218)));
                header.setFont(headerFont);

                //Ocultar columna id
                remindersTable.getColumnModel().getColumn(0).setMaxWidth(0);
                remindersTable.getColumnModel().getColumn(0).setMinWidth(0);
                remindersTable.getColumnModel().getColumn(0).setPreferredWidth(0);
                remindersTable.getColumnModel().getColumn(0).setResizable(false);
                remindersTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

                //Columna 2
                remindersTable.getColumnModel().getColumn(2).setMaxWidth(100);
                remindersTable.getColumnModel().getColumn(2).setMinWidth(100);
                remindersTable.getColumnModel().getColumn(2).setPreferredWidth(100);
                remindersTable.getColumnModel().getColumn(2).setResizable(false);

                sPanel.setBorder(BorderFactory.createEmptyBorder());
                sPanel.getVerticalScrollBar().setUI(new CustomScrollBar());
                sPanel.getHorizontalScrollBar().setUI(new CustomScrollBar());

                ps.setInt(1, user);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int idReminder = rs.getInt("idReminder");
                    String descriptionReminder = rs.getString("descriptionReminder");
                    String Autor = rs.getString("Autor");

                    // Añadir una fila con los valores correspondientes
                    modeloTabla.addRow(new Object[]{idReminder, descriptionReminder, Autor});
                }
/*
                remindersTable.getModel().addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        if (e.getType() == TableModelEvent.UPDATE) { // Verificar si hay un cambio en los datos de la tabla
                            int row = e.getFirstRow();
                            int column = e.getColumn();

                            // Verificar si el cambio se realizó en la columna de "Hecho"
                            if (column == 2) {
                                // Obtener el valor actualizado del checkbox
                                Boolean checked = (Boolean) TableToDo.getValueAt(row, column);
                                // Llamar al método actualizarTareas para guardar el cambio en la base de datos
                                try {
                                    actualizarTareas(row, checked, user);
                                } catch (SQLException ex) {
                                    System.out.println("Error al actualizar la tarea: " + ex.getMessage());
                                }
                            } else if (column == 1) { // Verificar si el cambio se realizó en la columna de "Task"
                                String newDescription = (String) TableToDo.getValueAt(row, column);
                                int idTarea = (int) TableToDo.getValueAt(row, 0); // Obtener el ID de la tarea
                                // Llamar al método actualizarTareas para guardar el cambio en la base de datos
                                try {
                                    actualizarDescripcionTarea(idTarea, newDescription, user);
                                } catch (SQLException ex) {
                                    System.out.println("Error al actualizar la descripción de la tarea: " + ex.getMessage());
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, "No hay nuevos cambios");
                            }
                        }
                    }
                });


    */

            } catch (SQLException ex) {
                System.out.println("Can't show :(");
                JOptionPane.showMessageDialog(null, ex.toString());
            }


    }

    private static void adjustRowHeight(JTable table, int row, int column) {
        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
        Component comp = table.prepareRenderer(cellRenderer, row, column);
        int preferredHeight = comp.getPreferredSize().height;

        // Ajustar altura de fila considerando todas las columnas
        for (int col = 0; col < table.getColumnCount(); col++) {
            cellRenderer = table.getCellRenderer(row, col);
            comp = table.prepareRenderer(cellRenderer, row, col);
            preferredHeight = Math.max(preferredHeight, comp.getPreferredSize().height);
        }

        table.setRowHeight(row, preferredHeight);
    }
    public ListReminders(int user){
        this.user = user;
        try {
            listReminders(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Botones
        remindersTable.setBorder(BorderFactory.createEmptyBorder());
        nuevoButton.setBorder(BorderFactory.createEmptyBorder());
        eliminarButton.setBorder(BorderFactory.createEmptyBorder());
        reminderButton.setBorder(new RoundBorder(new Color(234,224,218), 60,20));
        tipsButton.setBorder(new RoundBorder(new Color(234,224,218), 60,20));

        UIManager.put("OptionPane.background", new Color(234,224,218));
        UIManager.put("Panel.background", new Color(234,224,218));
        UIManager.put("OptionPane.messageFont", new Font("Segoe Script", Font.BOLD,18));
        UIManager.put("OptionPane.messageForeground", new Color(64,39,16));

        //Para quitar el borde donde se ubica el tittle
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        tipsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListReminders.this.setVisible(false);
                try {
                    Tip.verTips(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        reminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListReminders.this.setVisible(false);
                try {
                    Recordatorio.consultarRecordatorios(user);
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


        remindersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("se detectan dos clicks");
                    int row = remindersTable.rowAtPoint(e.getPoint());
                    int column = remindersTable.columnAtPoint(e.getPoint());
                    if (row != -1 && column != -1) {
                        System.out.println("valores validos");
                        adjustRowHeight(remindersTable, row, column);
                    }
                }
            }
        });
    }
}
