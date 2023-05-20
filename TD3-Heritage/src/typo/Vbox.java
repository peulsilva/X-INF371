package typo;

import java.awt.Graphics;

public class Vbox extends Group{
    double lineSkip;

    public Vbox(double lineSkip){
        super();
        this.lineSkip = lineSkip;
    }

    @Override
    public void add(Box b){
        if (list.isEmpty())
            this.ascent = b.getAscent();
        else
            this.ascent += this.descent + b.getAscent() + this.lineSkip;
        super.add(b);

        this.descent = b.getDescent();
        this.stretchingCapacity = Math.max(this.stretchingCapacity, b.getStretchingCapacity());
        this.width = Math.max(this.width, b.getWidth()); 
        
    }

    @Override
    public String toString() {
        return "Vbox" + super.toString();
    }

    @Override
    public boolean doDraw(Graphics graph, double x, double y, double w) {
        for (Box temp: list){
            temp.doDraw(
                graph, 
                x, 
                y, 
                w
            );
            
            y += temp.getAscent() + temp.getDescent() + this.lineSkip;
        }
        return true;
    }
}
