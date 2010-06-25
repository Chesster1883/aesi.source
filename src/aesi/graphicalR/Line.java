package aesi.graphicalR;

import java.awt.*;
import java.util.*;
import aesi.*;

/** Class that represnts a line by using two points. These two points are actually
* the x, y, height and width of the DComponent class! There while this object mostly
* holds variables (and fills in those of the subclass), the constructor only accepts 
* a color and a fitness value, as a line only becomes a line when it is initialized 
* with reInit.*/
public class Line extends DComponent
{

    private Color color = Color.red; 
    private double value; 

    public int getX1() { return x; }
    public void setX1(int value) { x = value; }
    public int getX2() { return width; }
    public void setX2(int value) { width = value; }
    public int getY1() { return y; }
    public void setY1(int value) { y = value; }
    public int getY2() { return height; }
    public void setY2(int value) { height = value; }

    public Color getColor() {return color; }
    public void setcolor(Color value) { color = value; }
    public void setValue(double v) { value = v; }
    public double getValue() { return value; }

    public Line(double iValue, Color iColor)
    {   
        value = iValue;
        color = iColor;
    }

    public void reInit(int x, int dHeight)
    {
        x = 0;
        y = Program.SCREEN_HEIGHT; 
        width = x; 
        height = (int)( Program.SCREEN_HEIGHT - Program.TOTAL_BORDER_WIDTH*2 - (value*dHeight)); 
    }

    public void reInit(int fromX, int fromY, int iX, int dHeight)
    {
        //Program.print("fromX: " + fromX + ", fromY: " + fromY + ", x: " + iX + ", dHeight: " + dHeight + "\n"); 
        x = fromX;
        y = fromY;
        width = iX;
        height = (int)( Program.SCREEN_HEIGHT - Program.TOTAL_BORDER_WIDTH*2 - (value*dHeight)); 
    }

    public void paint(Graphics g)
    {
        //Program.print("x1: " + x + ", y1:" + y + ", x2: " + width + ", y2: " + height + "\n"); 
        Color c = g.getColor();
        g.setColor(color);
        int b = Program.TOTAL_BORDER_WIDTH/2; // b of border
        g.drawLine(x + b, y, width + b, height);
        g.setColor(c);
    }
   
}
