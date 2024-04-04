import com.sun.prism.Graphics;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

import static com.sun.management.jmx.Trace.isSelected;

public class JCheckBoxCustom extends JCheckBox{
    private final int border=4;
    public JCheckBoxCustom() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setBackground(new Color(69,124,235));
    }

    public void paint(Graphics grphcs){
        super.paint((java.awt.Graphics) grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int ly = (getHeight() -16) / 2;
        if (isSelected()){
            if (isEnabled()){
                g2.setColor(getBackground());
            }else{
                g2.setColor(Color.BLUE);
            }
            g2.fillRoundRect(1,ly,16,16,border,border);

            // Draw Check icon
            int px[] = {4, 8, 12, 8, 6};
            int py[] = {ly+8, ly+14,ly+5, ly+3,ly+10, ly+6 };
            g2.setColor(Color.CYAN);
            g2.fillPolygon(px,py,px.length);
        }else{
            g2.setColor(Color.DARK_GRAY);
            g2.fillRoundRect(1,ly,16,16,border,border);
            g2.setColor(Color.white);
            g2.fillRoundRect(2,ly+1,14,14,border,border);
        }
    }
}
