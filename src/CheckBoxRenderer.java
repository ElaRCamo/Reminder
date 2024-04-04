import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
    public CheckBoxRenderer() {
        // Configurar el renderizador de la casilla de verificación
        setHorizontalAlignment(JCheckBox.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Configurar el estado de la casilla de verificación según el valor de la celda
        setSelected(value != null && (Boolean) value);

        // Configurar el color de fondo y el color de primer plano según el estado de la fila
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this; // Devolver la casilla de verificación como componente de renderizado
    }
}
