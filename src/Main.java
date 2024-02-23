import com.sun.deploy.panel.RuleSetViewerDialog;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends JFrame{
        public static RuleSetViewerDialog recordatorio;
        public static RuleSetViewerDialog newTip;
    public static void main(String[] args) throws SQLException {
            Recordatorio.consultarRecordatorios();
}

}