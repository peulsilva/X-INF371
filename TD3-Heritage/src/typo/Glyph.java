package typo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;


public class Glyph extends Box {
    final private static FontRenderContext frc = 
        new FontRenderContext(null, false, false);
    final private Font font;
    final private char[] chars;
    final private Rectangle2D bounds;

    public Glyph(Font font, char c){
        this.chars = new char[1];
        this.chars[0] = c;        

        this.font = font;

        TextLayout layout = new TextLayout("" + this.chars[0], font, frc);
        this.bounds = layout.getBounds();    
    }

    @Override
    public double getStretchingCapacity() {
        return 0;
    }

    @Override
    public double getAscent() {
        return - bounds.getY();
    }

    @Override
    public double getDescent() {
        return bounds.getHeight() + bounds.getY();
    }

    @Override
    public double getWidth() {
        return bounds.getWidth();
    }

    @Override
    public boolean doDraw(
        Graphics graph, 
        double x, 
        double y, 
        double w
    ) {
        graph.setFont(this.font);
        try{
            graph.drawChars(
                this.chars, 
                0, 
                1, 
                (int) (x- this.bounds.getX()), 
                (int) (y - this.bounds.getY())
            );
        }
        catch (Exception e){
            // System.out.println(e);
            return false;
        }
        
        
        return true;
    }
  
    // classe à compléter (question 2)
  
    public String toString() {
        return String.format(
            "Glyph(%s)" + super.toString(),
            chars[0]
        );
    }
  }
