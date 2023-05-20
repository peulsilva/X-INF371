package typo;

import java.awt.Graphics;

public class Hbox extends Group {

    public Hbox(){
        super();
    }

    @Override
    public void add(Box b){
        super.add(b);

        this.ascent = Math.max(b.getAscent(), this.ascent);
        this.descent = Math.max(b.getDescent(), this.descent);
        this.width += b.getWidth();
        this.stretchingCapacity += b.getStretchingCapacity();
    }

    @Override
    public boolean doDraw(Graphics graph, double x, double y, double w) {
        double minWidth = this.getWidth();
        if (w <  minWidth){
            for (Box temp: this.list){
                temp.draw(
                    graph, 
                    x, 
                    y + this.getAscent() - temp.getAscent(),  
                    temp.getWidth() * w/minWidth
                );
                x += temp.getWidth() * w/minWidth;
            }
            return false;
        }
        else { // w >= minWidth
            double diffWidth = w - minWidth;
            for (Box temp: this.list){
                temp.draw(
                    graph, 
                    x, 
                    y + this.getAscent() - temp.getAscent(), 
                    temp.getWidth() + diffWidth/this.getStretchingCapacity()* temp.getStretchingCapacity()
                );
                x+= temp.getWidth() + diffWidth/this.getStretchingCapacity()* temp.getStretchingCapacity();
            }
            return true;
        }
    }
    
    @Override
    public String toString() {
        return "Hbox" + super.toString();
    }
}
