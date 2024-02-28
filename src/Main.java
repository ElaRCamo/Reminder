import com.sun.deploy.panel.RuleSetViewerDialog;
import javax.swing.*;
import java.sql.SQLException;

public class Main extends JFrame{
        public static RuleSetViewerDialog recordatorio;
        public static RuleSetViewerDialog newTip;
    public static void main(String[] args) throws SQLException {
            Recordatorio.consultarRecordatorios();
}

}