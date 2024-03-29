import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;

public class Tip extends JFrame {
    public JPanel panelTip;
    private JLabel labelTip;
    private JLabel iconTip;
    JTextArea textTip;
    private JButton btSaveTip;
    private JButton btTip;
    private JButton bnReminder;
    private JButton btToDo;


    public Tip() {

        textTip.setLineWrap(true);
        textTip.setWrapStyleWord(true);

        bnReminder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tip.this.setVisible(false);
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setContentPane(recordatorio.mainPanel);
                recordatorio.setTitle("R E M I N D E R");
                recordatorio.setSize(480, 480);
                recordatorio.setVisible(true);
                recordatorio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                recordatorio.setLocationRelativeTo(null);
                //mandar reminder y autor
                String[] reminderValues = new String[0];
                try {
                    reminderValues = Recordatorio.consultaReminder(1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String reminderDescription = reminderValues[0];
                String autorReminder = reminderValues[1];
                recordatorio.phraseText.setText(reminderDescription);
                recordatorio.autorText.setText(autorReminder);
                //Quitar bordes
                recordatorio.phraseText.setBorder(BorderFactory.createEmptyBorder());
                recordatorio.autorText.setBorder(BorderFactory.createEmptyBorder());
                recordatorio.okButton.setBorder(BorderFactory.createEmptyBorder());
            }
        });
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
        panelTip.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 14, new Insets(0, 0, 0, 0), -1, -1));
        Font panelTipFont = this.$$$getFont$$$("Bradley Hand ITC", Font.BOLD, 20, panelTip.getFont());
        if (panelTipFont != null) panelTip.setFont(panelTipFont);
        panelTip.setForeground(new Color(-1384230));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-1));
        panel1.setForeground(new Color(-14477559));
        panelTip.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 10, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btSaveTip = new JButton();
        btSaveTip.setBackground(new Color(-1));
        btSaveTip.setBorderPainted(false);
        Font btSaveTipFont = this.$$$getFont$$$("Bradley Hand ITC", Font.BOLD, 20, btSaveTip.getFont());
        if (btSaveTipFont != null) btSaveTip.setFont(btSaveTipFont);
        btSaveTip.setForeground(new Color(-6205467));
        btSaveTip.setText("Save");
        panel1.add(btSaveTip, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(170, 40), null, null, 0, false));
        btTip = new JButton();
        btTip.setBackground(new Color(-1));
        btTip.setBorderPainted(false);
        Font btTipFont = this.$$$getFont$$$("Bradley Hand ITC", Font.BOLD, 20, btTip.getFont());
        if (btTipFont != null) btTip.setFont(btTipFont);
        btTip.setForeground(new Color(-6205467));
        btTip.setText("Consult");
        panel1.add(btTip, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(170, 40), null, null, 0, false));
        labelTip = new JLabel();
        labelTip.setBackground(new Color(-1));
        Font labelTipFont = this.$$$getFont$$$("Italic", Font.PLAIN, 16, labelTip.getFont());
        if (labelTipFont != null) labelTip.setFont(labelTipFont);
        labelTip.setForeground(new Color(-14477559));
        labelTip.setText("Tip para tu yo del futuro...");
        panel1.add(labelTip, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(340, 40), null, new Dimension(340, 40), 0, false));
        textTip = new JTextArea();
        textTip.setBackground(new Color(-1387547));
        Font textTipFont = this.$$$getFont$$$("Bradley Hand ITC", Font.PLAIN, 16, textTip.getFont());
        if (textTipFont != null) textTip.setFont(textTipFont);
        textTip.setForeground(new Color(-6205467));
        panel1.add(textTip, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(340, 65), new Dimension(340, 80), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 15), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 15), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(15, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 7, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(15, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 7, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(15, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(30, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 12, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(15, -1), null, null, 0, false));
        bnReminder = new JButton();
        bnReminder.setBackground(new Color(-1387547));
        bnReminder.setBorderPainted(false);
        Font bnReminderFont = this.$$$getFont$$$("Italic", Font.BOLD, 15, bnReminder.getFont());
        if (bnReminderFont != null) bnReminder.setFont(bnReminderFont);
        bnReminder.setForeground(new Color(-14477559));
        bnReminder.setText("Reminder");
        panelTip.add(bnReminder, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 7, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(165, 40), null, null, 0, false));
        iconTip = new JLabel();
        iconTip.setHorizontalAlignment(0);
        iconTip.setHorizontalTextPosition(0);
        iconTip.setIcon(new ImageIcon(getClass().getResource("/logo.png")));
        iconTip.setText("");
        panelTip.add(iconTip, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 10, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(95, 95), new Dimension(95, 95), new Dimension(95, 95), 1, false));
        btToDo = new JButton();
        btToDo.setBackground(new Color(-1387547));
        btToDo.setBorderPainted(false);
        Font btToDoFont = this.$$$getFont$$$("Italic", Font.BOLD, 15, btToDo.getFont());
        if (btToDoFont != null) btToDo.setFont(btToDoFont);
        btToDo.setForeground(new Color(-14477559));
        btToDo.setText("To Do");
        panelTip.add(btToDo, new com.intellij.uiDesigner.core.GridConstraints(4, 10, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(165, 40), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 12, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 30), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 12, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 30), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer10 = new com.intellij.uiDesigner.core.Spacer();
        panelTip.add(spacer10, new com.intellij.uiDesigner.core.GridConstraints(3, 11, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(30, -1), null, null, 0, false));
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
