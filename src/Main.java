import com.sun.deploy.panel.RuleSetViewerDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.sql.SQLException;

public class Main extends JFrame{
        public static RuleSetViewerDialog recordatorio;
        public static RuleSetViewerDialog newTip;

    public static void main(String[] args) throws SQLException {

            Recordatorio recordatorio = new Recordatorio();
            recordatorio.setContentPane(recordatorio.mainPanel);
            recordatorio.setTitle("R E M I N D E R");
            recordatorio.setSize(480,480);
            recordatorio.setVisible(true);
            recordatorio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            recordatorio.setLocationRelativeTo(null);
            //recordatorio.setResizable(false);
            //mandar reminder y autor
            String[] reminderValues = Recordatorio.consultaReminder(1);
            String reminderDescription = reminderValues[0];
            String autorReminder = reminderValues[1];
            recordatorio.phraseText.setText(reminderDescription);
            recordatorio.autorText.setText(autorReminder);
            //Quitar bordes
            recordatorio.phraseText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            recordatorio.autorText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            recordatorio.okButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());



}

}