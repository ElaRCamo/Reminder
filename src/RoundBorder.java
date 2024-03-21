import java.awt.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;

public class RoundBorder extends AbstractBorder {
    private static final long serialVersionUID = 1L;
    private Color color;
    private int arc;
    private int borderWidth;

    public RoundBorder(Color color, int arc, int borderWidth) {
        this.color = color;
        this.arc = arc;
        this.borderWidth = borderWidth;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(borderWidth)); // Establecer el grosor del trazo
        g2.drawRoundRect(x, y, width - 1, height - 1, arc, arc);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(borderWidth, borderWidth, borderWidth, borderWidth);
    }

    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = borderWidth;
        insets.top = insets.bottom = borderWidth;
        return insets;
    }
}
