
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.*;
import java.awt.*;

public class CustomScrollBar extends BasicScrollBarUI {

    private final int THUMB_SIZE = 50;
    private final int scrollBarWidth = 8;

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(scrollBarWidth, scrollBarWidth);
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        if(scrollbar.getOrientation()==JScrollBar.VERTICAL) {
            return new Dimension(0, THUMB_SIZE);
        }else{
            return  new Dimension(THUMB_SIZE,0);
        }
    }

    @Override
    protected Dimension getMaximumThumbSize() {
        if(scrollbar.getOrientation()==JScrollBar.VERTICAL) {
            return new Dimension(0, THUMB_SIZE);
        }else{
            return  new Dimension(THUMB_SIZE,0);
        }
    }

    @Override
    protected void paintTrack(Graphics grphcs, JComponent c, Rectangle rctngl) {
        Graphics2D g2=(Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int orientation = scrollbar.getOrientation();
        int size;
        int x;
        int y;
        int width;
        int height;
        if (orientation == JScrollBar.VERTICAL) {
            size = rctngl.width / 2;
            x = rctngl.x + ((rctngl.width - size) / 2);
            y = rctngl.y;
            width = size;
            height = rctngl.height;
        }else{
            size=rctngl.height/2;
            y=rctngl.y+((rctngl.height-size)/2);
            x=0;
            width=rctngl.width;
            height=size;
        }
        g2.setColor(new Color(234,224,218));
        g2.fillRect(x,y,width,height);
    }

    @Override
    protected void paintThumb(Graphics grphcs, JComponent c, Rectangle rctngl) {
        Graphics2D g2=(Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x=rctngl.x;
        int y=rctngl.y;
        int width = rctngl.width;
        int height = rctngl.height;
        if(scrollbar.getOrientation()==JScrollBar.VERTICAL){
            y+=8;
            height -= 16;
        }else{
            x+=8;
            width -= 16;
        }
        //g2.setColor(scrollbar.getForeground());
        g2.setColor(new Color(201, 191, 185));
        g2.fillRoundRect(x,y,width,height,8,8);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new ScrollBarButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new ScrollBarButton();
    }

    private class ScrollBarButton extends JButton{
        public ScrollBarButton(){
            setBorder(BorderFactory.createEmptyBorder());
        }
        @Override
        public void paint (Graphics grphcs){

        }
    }



}
