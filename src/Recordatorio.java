import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Locale;

public class Recordatorio extends JFrame {
    JButton okButton;
    private JButton toDoButton;
    private JButton tipsButton;
    JTextArea phraseText;
    private JPanel reminderPanel;
    JPanel mainPanel;
    JTextField autorText;
    private JLabel iconTip;
    static Connection connection;
    Statement st; //para SELECT
    ResultSet r; //ejecutar query
    static String reminderDescription;


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

    public static int numRegistros() throws SQLException {
        try (Connection connection = conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                //Obtenemos el numero total de registros y lo convertimos a int
                ResultSet rs = st.executeQuery("SELECT count(idReminder) FROM Reminder");
                // Mover al primer resultado (ya que es un solo valor)
                rs.next();
                // Obtener y retornar el valor como entero
                return rs.getInt(1);
            }
        }
    }
//linewipe
    //wrapStyleWord

    public static String[] consultaReminder(int i) throws SQLException {
        try (Connection connection = conectar()) {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                int numRecordatorio = numRegistros();
                //Seleccionamos un número aleatorio de id
                int randomId = (int) (Math.floor(Math.random() * (numRecordatorio - 1 + 1 + 1) + 1));
                //System.out.println("Numero de randomid:"+randomId);
                ResultSet r = st.executeQuery("SELECT descriptionReminder,Autor FROM Reminder WHERE idReminder = " + randomId + ";");
                r.next();
                String reminderDescription = r.getString(1);
                String autorReminder = r.getString(2);
                System.out.println(reminderDescription + ", " + autorReminder);
                return new String[]{reminderDescription, autorReminder};
            }
        }
    }


    // min 28 https://www.youtube.com/watch?v=_cRp1qGVIkU
   /* public void insertarTip() throws SQLException {
        conectar();
        ps = connection.prepareStatement("INSERT INTO Tip VALUES (?)");
        ps.setString(1,".");
    }*/

    public Recordatorio() {

        reminderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        phraseText.setLineWrap(true);
        phraseText.setWrapStyleWord(true);
        phraseText.setEditable(false); //Para que no se pueda editar el texto
        phraseText.setOpaque(false);

        // Configurar alineación del contenido en el JTextArea
        phraseText.setAlignmentX(Component.CENTER_ALIGNMENT);
        phraseText.setAlignmentY(Component.CENTER_ALIGNMENT);

        autorText.setEditable(false);
        autorText.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(okButton, "Be happy!!!");

                String[] reminderValues = new String[0];
                try {
                    reminderValues = Recordatorio.consultaReminder(1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String reminderDescription = reminderValues[0];
                String autorReminder = reminderValues[1];
                phraseText.setText(reminderDescription);
                autorText.setText(autorReminder);
            }
        });
        tipsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recordatorio.this.setVisible(false);
                Tip newTip = new Tip();
                newTip.setContentPane(newTip.panelTip);
                newTip.setTitle("Save a tip");
                newTip.setSize(500, 500);
                newTip.setVisible(true);
                newTip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                newTip.setLocationRelativeTo(null);
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBackground(new Color(-1384230));
        mainPanel.setMaximumSize(new Dimension(500, 500));
        mainPanel.setMinimumSize(new Dimension(480, 480));
        tipsButton = new JButton();
        tipsButton.setBackground(new Color(-7192));
        tipsButton.setBorderPainted(false);
        Font tipsButtonFont = this.$$$getFont$$$("Segoe Script", Font.BOLD, 20, tipsButton.getFont());
        if (tipsButtonFont != null) tipsButton.setFont(tipsButtonFont);
        tipsButton.setForeground(new Color(-12572912));
        tipsButton.setText("Tips");
        mainPanel.add(tipsButton, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 50), null, null, 0, false));
        toDoButton = new JButton();
        toDoButton.setBackground(new Color(-7192));
        toDoButton.setBorderPainted(false);
        Font toDoButtonFont = this.$$$getFont$$$("Segoe Script", Font.BOLD, 20, toDoButton.getFont());
        if (toDoButtonFont != null) toDoButton.setFont(toDoButtonFont);
        toDoButton.setForeground(new Color(-12572912));
        toDoButton.setText("To do");
        mainPanel.add(toDoButton, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 50), null, null, 0, false));
        reminderPanel = new JPanel();
        reminderPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        reminderPanel.setBackground(new Color(-1));
        mainPanel.add(reminderPanel, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, -1), null, null, 1, true));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-1));
        Font label1Font = this.$$$getFont$$$("Segoe Script", Font.BOLD, 22, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-14477559));
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("R E M I N D E R ");
        reminderPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(380, -1), new Dimension(-1, 40), new Dimension(-1, 40), 0, false));
        phraseText = new JTextArea();
        phraseText.setAlignmentX(0.0f);
        phraseText.setBackground(new Color(-1));
        Font phraseTextFont = this.$$$getFont$$$("Segoe Print", Font.PLAIN, 14, phraseText.getFont());
        if (phraseTextFont != null) phraseText.setFont(phraseTextFont);
        phraseText.setForeground(new Color(-7895909));
        phraseText.setLineWrap(true);
        phraseText.setText("");
        phraseText.setWrapStyleWord(true);
        reminderPanel.add(phraseText, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(350, -1), null, null, 0, false));
        autorText = new JTextField();
        autorText.setAlignmentX(0.0f);
        autorText.setAlignmentY(0.0f);
        autorText.setBackground(new Color(-1));
        Font autorTextFont = this.$$$getFont$$$("Segoe Print", Font.PLAIN, 16, autorText.getFont());
        if (autorTextFont != null) autorText.setFont(autorTextFont);
        autorText.setText("");
        reminderPanel.add(autorText, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(350, 30), new Dimension(-1, 30), new Dimension(-1, 30), 0, false));
        okButton = new JButton();
        okButton.setBackground(new Color(-1));
        Font okButtonFont = this.$$$getFont$$$("ItalicC", Font.BOLD, 14, okButton.getFont());
        if (okButtonFont != null) okButton.setFont(okButtonFont);
        okButton.setForeground(new Color(-4014119));
        okButton.setText("Ok!");
        reminderPanel.add(okButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(380, 30), new Dimension(-1, 30), new Dimension(-1, 30), 0, false));
        iconTip = new JLabel();
        iconTip.setHorizontalAlignment(0);
        iconTip.setHorizontalTextPosition(0);
        iconTip.setIcon(new ImageIcon(getClass().getResource("/sparkle.png")));
        iconTip.setText("");
        mainPanel.add(iconTip, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(90, 90), new Dimension(90, 90), new Dimension(90, 90), 1, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 6, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(30, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 6, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(30, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        label1.setLabelFor(autorText);
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
        return mainPanel;
    }

}
