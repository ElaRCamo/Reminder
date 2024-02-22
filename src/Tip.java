import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Locale;

public class Tip extends JFrame {
    public JPanel panelTip;
    private JLabel labelTip;
    private JLabel iconTip;
    JTextArea textTip;
    private JButton btSaveTip;
    private JButton btConsultTip;
    private JButton bnReminder;
    private JButton btToDo;
    private JPanel saveTipPanel;
    static Connection connection;
    PreparedStatement ps; //para INSERT TO
    Statement st; //para SELECT
    ResultSet r; //ejecutar query
    DefaultListModel mod = new DefaultListModel();

    public static void guardarTips() throws SQLException {
        Tip newTip = new Tip();
        newTip.setContentPane(newTip.panelTip);
        newTip.setTitle("Save a tip");
        newTip.setSize(500, 500);
        newTip.setVisible(true);
        newTip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newTip.setLocationRelativeTo(null);
    }


    public Tip() {
        saveTipPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        textTip.setLineWrap(true);
        textTip.setWrapStyleWord(true);

        bnReminder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tip.this.setVisible(false);
                try {
                    Recordatorio.consultarRecordatorios();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btSaveTip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tip = textTip.getText();
                System.out.println(tip);
                try (Connection connection = Tip.conectar()) {
                    assert connection != null;
                    try (Statement st = connection.createStatement()) {
                        PreparedStatement ps = connection.prepareStatement("INSERT INTO Tip(descriptionTip) VALUES(?)");
                        ps.setString(1, tip);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Tip guardado exitosamente");//null se refiere a la posicion del msj
                        textTip.setText("");//Para que el espacio quede vacio
                    }
                } catch (SQLException ex) {
                    System.out.println("No se pudo guardar :( ");
                    JOptionPane.showMessageDialog(null, e.toString());
                }
            }

        });
        btConsultTip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tip.this.setVisible(false);
                listaTips newListaTip = new listaTips();
                newListaTip.setContentPane(newListaTip.listPanel);
                newListaTip.setTitle("Tips guardados");
                newListaTip.setSize(500, 500);
                newListaTip.setVisible(true);
                newListaTip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                newListaTip.setLocationRelativeTo(null);

                 /*try (Connection connection = Tip.conectar()) {
                    assert connection != null;
                    try (Statement st = connection.createStatement()) {
                        saveTipPanel.setModel(mod);
                        ResultSet r = st.executeQuery("SELECT descriptionTip FROM Tip");
                        mod.removeAllElements();
                        while (r.next()) {
                            mod.addElement(r.getString(1) + " ");
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("Error al consultar :( ");
                    JOptionPane.showMessageDialog(null, e.toString());
                }*/
            }
        });
    }


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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelTip = new JPanel();
        panelTip.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelTip.setBackground(new Color(-1384230));
        Font panelTipFont = this.$$$getFont$$$("Bradley Hand ITC", Font.BOLD, 20, panelTip.getFont());
        if (panelTipFont != null) panelTip.setFont(panelTipFont);
        panelTip.setForeground(new Color(-1387547));
        panelTip.setMinimumSize(new Dimension(500, 500));
        saveTipPanel = new JPanel();
        saveTipPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 5, new Insets(0, 0, 0, 0), -1, -1));
        saveTipPanel.setBackground(new Color(-1));
        saveTipPanel.setForeground(new Color(-14477559));
        panelTip.add(saveTipPanel, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btSaveTip = new JButton();
        btSaveTip.setBackground(new Color(-1));
        btSaveTip.setBorderPainted(false);
        Font btSaveTipFont = this.$$$getFont$$$("Segoe Script", Font.BOLD, 20, btSaveTip.getFont());
        if (btSaveTipFont != null) btSaveTip.setFont(btSaveTipFont);
        btSaveTip.setForeground(new Color(-3555399));
        btSaveTip.setText("Save");
        saveTipPanel.add(btSaveTip, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(170, 40), null, null, 0, false));
        btConsultTip = new JButton();
        btConsultTip.setBackground(new Color(-1));
        btConsultTip.setBorderPainted(false);
        Font btConsultTipFont = this.$$$getFont$$$("Segoe Script", Font.BOLD, 20, btConsultTip.getFont());
        if (btConsultTipFont != null) btConsultTip.setFont(btConsultTipFont);
        btConsultTip.setForeground(new Color(-3555399));
        btConsultTip.setText("Consult");
        saveTipPanel.add(btConsultTip, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(170, 40), null, null, 0, false));
        labelTip = new JLabel();
        labelTip.setBackground(new Color(-1));
        Font labelTipFont = this.$$$getFont$$$("Segoe Print", Font.BOLD, 16, labelTip.getFont());
        if (labelTipFont != null) labelTip.setFont(labelTipFont);
        labelTip.setForeground(new Color(-14477559));
        labelTip.setText("Tip para tu yo del futuro...");
        saveTipPanel.add(labelTip, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(340, 40), null, new Dimension(340, 40), 0, false));
        textTip = new JTextArea();
        textTip.setBackground(new Color(-1384230));
        Font textTipFont = this.$$$getFont$$$("Segoe Print", Font.BOLD, 18, textTip.getFont());
        if (textTipFont != null) textTip.setFont(textTipFont);
        textTip.setForeground(new Color(-14477559));
        saveTipPanel.add(textTip, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 110), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        saveTipPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(15, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        saveTipPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(15, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        saveTipPanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(15, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        saveTipPanel.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(30, -1), null, null, 0, false));
        bnReminder = new JButton();
        bnReminder.setBackground(new Color(-7192));
        bnReminder.setBorderPainted(false);
        Font bnReminderFont = this.$$$getFont$$$("Segoe Script", Font.BOLD, 20, bnReminder.getFont());
        if (bnReminderFont != null) bnReminder.setFont(bnReminderFont);
        bnReminder.setForeground(new Color(-14477559));
        bnReminder.setText("Reminder");
        panelTip.add(bnReminder, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, 50), null, null, 0, false));
        iconTip = new JLabel();
        iconTip.setHorizontalAlignment(0);
        iconTip.setHorizontalTextPosition(0);
        iconTip.setIcon(new ImageIcon(getClass().getResource("/sparkle.png")));
        iconTip.setText("");
        panelTip.add(iconTip, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(95, 95), new Dimension(95, 95), new Dimension(95, 95), 1, false));
        btToDo = new JButton();
        btToDo.setBackground(new Color(-7192));
        btToDo.setBorderPainted(false);
        Font btToDoFont = this.$$$getFont$$$("Segoe Script", Font.BOLD, 20, btToDo.getFont());
        if (btToDoFont != null) btToDo.setFont(btToDoFont);
        btToDo.setForeground(new Color(-14477559));
        btToDo.setText("To Do");
        panelTip.add(btToDo, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, 50), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 30), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(30, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 30), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelTip;
    }

}
