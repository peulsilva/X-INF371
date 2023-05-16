package typo;

import java.awt.Graphics;

public class Space extends Box {
    private double width;
    private double stretchingCapacity;

    public Space(double minWidth, double stretchingCapacity){
        this.width = minWidth;
        this.stretchingCapacity = stretchingCapacity;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getStretchingCapacity() {
        return this.stretchingCapacity;
    }

    @Override
    public double getAscent() {
        return 0;
    }

    @Override
    public double getDescent() {
        return 0;
    }

    public String toString(){
        return "Space" + super.toString(); 
    }

    @Override
    public boolean doDraw(Graphics graph, double x, double y, double w) {
        return true;
    }
}
