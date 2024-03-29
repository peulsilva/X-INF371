package typo;

import java.awt.Graphics;
import java.util.LinkedList;

public abstract class Group extends Box {

    protected double ascent, descent, width, stretchingCapacity;
    protected final LinkedList<Box> list = new LinkedList<Box>();  

    @Override
    public abstract boolean doDraw(
        Graphics graph, 
        double x, 
        double y, 
        double w
    );

    @Override
    public double getAscent(){
        return this.ascent;
    }

    @Override
    public double getDescent(){
        return this.descent;
    };

    @Override
    public double getStretchingCapacity(){
        return this.stretchingCapacity;
    };

    @Override
    public double getWidth(){
        return this.width;
    };

    public void add(Box b){
        this.list.addLast(b);
    }

    @Override
    public String toString(){
        String str = super.toString() + "{";
		for (Box temp: list) {
			str += "\n"+ temp.toString() + ",";
		}
		str = str.replaceAll("\n", "\n\t");
		str += "\n";
        str += "}";
		return str;
	}
    
}
