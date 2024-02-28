import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ToDo extends JFrame{
    private JPanel toDoPanel;
    private JPanel tablaPanel;
    private JLabel listaLabel;
    private JButton eliminarButton;
    private JButton nuevoButton;
    private JButton reminderButton;
    private JButton tipsButton;
    private JButton xButton;
    private JTable TableToDo;
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

    void Tarea() {
        this.setPreferredSize(new Dimension(40, 20));
        this.setBackground(Color.red);
        this.setLayout(new BorderLayout());

        checked = false;

        index = new JLabel("");
        index.setPreferredSize((new Dimension(20, 20)));
        index.setHorizontalAlignment(JLabel.CENTER);
        this.add(index, BorderFactory.createEmptyBorder());
        taskName.setBackground(Color.green);

        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("done");
        done.setPreferredSize(new Dimension(40, 20));
        done.setBorder(BorderFactory.createEmptyBorder());

        this.add(done, BorderLayout.EAST);
    }

    public static void gestionarListaTareas(){

        ToDo toDo = new ToDo();
        toDo.setContentPane(toDo.toDoPanel);
        toDo.setSize(500, 500);
        toDo.setVisible(true);
        toDo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        toDo.setLocationRelativeTo(null);
        toDo.setResizable(false);
    }

    public ToDo(){
        tablaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        //Quitar bordes
        tipsButton.setBorder(BorderFactory.createEmptyBorder());
        reminderButton.setBorder(BorderFactory.createEmptyBorder());
        nuevoButton.setBorder(BorderFactory.createEmptyBorder());
        eliminarButton.setBorder(BorderFactory.createEmptyBorder());

        //Para quitar el borde donde se ubica el tittle
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

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
