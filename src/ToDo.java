import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;


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
    private JScrollPane sPanel;
    private JLabel index;
    private JTextField taskName;
    private JButton done;
    private boolean checked;
    DefaultTableModel modeloTabla = new DefaultTableModel();
    private int user;

    public static Connection conectar() {
        String url = "jdbc:jtds:sqlserver://localhost:1433/Reminders;instance=MSSQLSERVER";
        String user = "sa";
        String password = "Grammer1";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //System.out.println("Conexión exitosa");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void gestionarListaTareas(int user) throws SQLException {
        SwingUtilities.invokeLater(() -> {
            try {
                ToDo toDo = new ToDo(user);
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
        // Obtener la columna específica
        TableColumn tc = table.getColumnModel().getColumn(column);

        // Establecer el editor y el renderizador de celdas como JCheckBoxCustom
        JCheckBoxCustom checkBox = new JCheckBoxCustom();
        tc.setCellEditor(new DefaultCellEditor(new JCheckBoxCustom()));
        tc.setCellRenderer(new CheckBoxRenderer());
    }

    public void consultarTareas(int user) throws SQLException {
        String sql = "SELECT idToDo, descriptionToDo, done FROM ToDo WHERE userId = ?";

        try (Connection connection = conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

                modeloTabla = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column != 0; // Permitir editar todas las columnas excepto la primera (ID)
                    }
                };
                modeloTabla.addColumn("ID");
                modeloTabla.addColumn("Task");
                modeloTabla.addColumn("Done");
                TableToDo.setModel(modeloTabla);

                // Desactivar el ajuste automático inicial
                TableToDo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                // Activar el ajuste automático después de ajustar el ancho de las columnas
                TableToDo.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

                //Ocultar columna id
                TableToDo.getColumnModel().getColumn(0).setMaxWidth(0);
                TableToDo.getColumnModel().getColumn(0).setMinWidth(0);
                TableToDo.getColumnModel().getColumn(0).setPreferredWidth(0);
                TableToDo.getColumnModel().getColumn(0).setResizable(false);

                //Columna 2
                TableToDo.getColumnModel().getColumn(2).setMaxWidth(50);
                TableToDo.getColumnModel().getColumn(2).setMinWidth(50);
                TableToDo.getColumnModel().getColumn(2).setPreferredWidth(50);
                TableToDo.getColumnModel().getColumn(2).setResizable(false);


            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();

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
        } catch (SQLException ex) {
            System.out.println("No se puede mostrar :( ");
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    public void actualizarDescripcionTarea(int idTarea, String nuevaDescripcion, int user) throws SQLException {
        String sql = "UPDATE ToDo SET descriptionToDo=? WHERE idToDo=? ";

        try (Connection connection = conectar()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, nuevaDescripcion);
                ps.setInt(2, idTarea);
                ps.executeUpdate();
            }
        }
    }

    public void actualizarTareas(int fila, boolean checked, int user) throws SQLException {
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
                consultarTareas(user);
            } catch (SQLException ex) {
                System.out.println("No se puede actualizar :( ");
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }
    }

    public void eliminarTareas(int user) throws SQLException {
        modeloTabla = (DefaultTableModel) TableToDo.getModel();
        int fila = TableToDo.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un elemento para eliminar.");
            return;
        }else{

            int idTarea = Integer.parseInt(this.TableToDo.getValueAt(fila, 0).toString());
            String sql = "DELETE FROM ToDo WHERE idToDo= " + idTarea + ";";

            try (Connection connection = ListaTips.conectar()) {
                assert connection != null;
                try (Statement st = connection.createStatement()) {
                    st.executeUpdate(sql);
                    modeloTabla.removeRow(fila);
                    JOptionPane.showMessageDialog(null, "Tarea eliminada exitosamente");
                }
            } catch (SQLException ex) {
                System.out.println("No se pudo eliminar :( ");
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }
    }

    public static int numTareas(int user) throws SQLException {
        try (Connection connection = conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                //Obtenemos el numero total de registros y lo convertimos a int
                ResultSet rs = st.executeQuery("SELECT count(idToDo) FROM ToDo");
                // Mover al primer resultado (ya que es un solo valor)
                rs.next();
                // Obtener y retornar el valor como entero
                return rs.getInt(1);
            }
        }
    }

    public void agregarTareas(int user) throws SQLException {
        modeloTabla = (DefaultTableModel) TableToDo.getModel();

        // Añadir una fila con los valores correspondientes
        modeloTabla.addRow(new Object[modeloTabla.getColumnCount()]);
        int filaNueva = modeloTabla.getRowCount() - 1;

        TableToDo.editCellAt(filaNueva, 1); //Editar la columna donde está la tarea
        TableToDo.requestFocusInWindow(); // Poner el foco en la celda editada

        // Agregar un TableModelListener para detectar cambios en el modelo de la tabla
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) { // Verificar si el cambio fue una actualización de celda
                    int fila = e.getFirstRow();
                    int columna = e.getColumn();
                    if (fila == filaNueva && columna == 1) { // Verificar que se haya modificado la celda de la columna deseada
                        guardarTarea(filaNueva, user);
                    }
                }
            }
        });
    }

    private void guardarTarea(int fila, int user) {
        String tarea = TableToDo.getValueAt(fila, 1).toString(); // Índice 1

        try (Connection connection = Tip.conectar()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO ToDo (descriptionToDo, done, userId) VALUES (?, ?, ?)")) {
                ps.setString(1, tarea);
                ps.setInt(2, 0);
                ps.setInt(3, user);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Tarea agregada exitosamente");
                consultarTareas(user);
            } catch (SQLException ex) {
                System.out.println("No se pudo agregar :( ");
                JOptionPane.showMessageDialog(null, ex.toString());
                throw new RuntimeException(ex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ToDo(int user) throws SQLException {
        this.user = user;

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconReminders.png")));

        // Personalizar el encabezado de la tabla
        JTableHeader header = TableToDo.getTableHeader();
        header.setBackground(new java.awt.Color(255, 255, 255)); // Cambiar color de fondo
        header.setForeground(new java.awt.Color(46,36,9)); // Cambiar color del texto
        java.awt.Font headerFont = new java.awt.Font("Segoe Script", java.awt.Font.BOLD, 16);
        header.setBorder(new LineBorder(new java.awt.Color(234, 224, 218)));
        header.setFont(headerFont);

        tablaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        nuevoButton.setBorder(BorderFactory.createEmptyBorder());
        eliminarButton.setBorder(BorderFactory.createEmptyBorder());
        tipsButton.setBorder(new RoundBorder(new Color(234,224,218), 60,20));
        reminderButton.setBorder(new RoundBorder(new Color(234,224,218), 60,20));

        UIManager.put("OptionPane.background", new Color(234,224,218));
        UIManager.put("Panel.background", new Color(234,224,218));
        UIManager.put("OptionPane.messageFont", new Font("Segoe Script", Font.BOLD,18));
        UIManager.put("OptionPane.messageForeground", new Color(64,39,16));

        //Para quitar el borde donde se ubica el tittle
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        try {
            consultarTareas(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sPanel.setBorder(BorderFactory.createEmptyBorder());
        sPanel.getVerticalScrollBar().setUI(new CustomScrollBar());
        sPanel.getHorizontalScrollBar().setUI(new CustomScrollBar());


        reminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDo.this.setVisible(false);
                try {
                    Recordatorio.consultarRecordatorios(user);
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
                    Tip.verTips(user);
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

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminarTareas(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    agregarTareas(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
