import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


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
            //System.out.println("Conexión exitosa");
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
    public void addCheckBox(int column, JTable table){
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    public void consultarTareas() throws SQLException {
        String sql = "SELECT idToDo, descriptionToDo, done FROM ToDo";

        try (Connection connection = conectar()) {
            assert connection != null;

            try (Statement st = connection.createStatement()) {
                DefaultTableModel modeloTabla = new DefaultTableModel() {
                    // Hacer que todas las celdas sean editables, excepto la columna de ID
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column != 0; // Permitir editar todas las columnas excepto la primera (ID)
                    }
                };
                modeloTabla.addColumn("ID");
                modeloTabla.addColumn("Task");
                modeloTabla.addColumn("Done");
                TableToDo.setModel(modeloTabla);
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    int idTarea = rs.getInt("idToDo");
                    String tarea = rs.getString("descriptionToDo");
                    boolean hecho = rs.getInt("done") == 1; // Convertir el valor entero a booleano

                    //System.out.println("ID: " + idTarea + ", Tarea: " + tarea + ", Hecho: " + hecho);

                    // Añadir una fila con los valores correspondientes
                    modeloTabla.addRow(new Object[]{idTarea, tarea, hecho});
                }

                // Llamar a addCheckBox después de que se haya configurado el modelo de tabla
                addCheckBox(2, TableToDo); // La columna "Hecho" es la tercera columna (índice 2)


                TableToDo.getModel().addTableModelListener(new TableModelListener() {
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
                                    actualizarTareas(row, checked);
                                } catch (SQLException ex) {
                                    System.out.println("Error al actualizar la tarea: " + ex.getMessage());
                                }
                            } else if (column == 1) { // Verificar si el cambio se realizó en la columna de "Task"
                                String newDescription = (String) TableToDo.getValueAt(row, column);
                                int idTarea = (int) TableToDo.getValueAt(row, 0); // Obtener el ID de la tarea
                                // Llamar al método actualizarTareas para guardar el cambio en la base de datos
                                try {
                                    actualizarDescripcionTarea(idTarea, newDescription);
                                } catch (SQLException ex) {
                                    System.out.println("Error al actualizar la descripción de la tarea: " + ex.getMessage());
                                }
                            }
                        }
                    }
                });
            }
        } catch (SQLException ex) {
            System.out.println("No se puede mostrar :( ");
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    public void actualizarDescripcionTarea(int idTarea, String nuevaDescripcion) throws SQLException {
        String sql = "UPDATE ToDo SET descriptionToDo=? WHERE idToDo=?";

        try (Connection connection = conectar()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, nuevaDescripcion);
                ps.setInt(2, idTarea);
                ps.executeUpdate();
            }
        }
    }

    public void actualizarTareas(int fila, boolean checked) throws SQLException {
        String tarea = TableToDo.getValueAt(fila, 1).toString(); // Índice 1
        int hecho = checked ? 1 : 0; // Convertir el estado del checkbox a entero (1 si está marcado, 0 si no lo está)

        String sql = "UPDATE ToDo SET descriptionToDo=?, done=? WHERE idToDo=?";

        try (Connection connection = conectar()) {
            assert connection != null;

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, tarea);
                ps.setInt(2, hecho);
                int idTarea = Integer.parseInt(this.TableToDo.getValueAt(fila, 0).toString()); // Índice 0 para el ID
                ps.setInt(3, idTarea);

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
        nuevoButton.setBorder(BorderFactory.createEmptyBorder());
        eliminarButton.setBorder(BorderFactory.createEmptyBorder());
        tipsButton.setBorder(new RoundBorder(new Color(234,224,218), 60,20));
        reminderButton.setBorder(new RoundBorder(new Color(234,224,218), 60,20));

        //Para quitar el borde donde se ubica el tittle
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        try {
            consultarTareas();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        TableToDo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desactivar el ajuste automático inicial

        // Ajustar el ancho de las columnas al contenido
        for (int column = 0; column < TableToDo.getColumnCount(); column++) {
            TableColumn tableColumn = TableToDo.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < TableToDo.getRowCount(); row++) {
                TableCellRenderer cellRenderer = TableToDo.getCellRenderer(row, column);
                Component c = TableToDo.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + TableToDo.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                // No permitir que el ancho de la columna exceda su tamaño máximo
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }
        // Activar el ajuste automático después de ajustar el ancho de las columnas
        TableToDo.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);


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
