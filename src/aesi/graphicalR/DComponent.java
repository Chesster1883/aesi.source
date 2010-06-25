package aesi.graphicalR;

import java.awt.*;
import javax.swing.*;

public abstract class DComponent
{

    protected int x, y, width, height;

    public int[] getCorners()
    {
        int[] result = {x, y,
                        x + width, y,
                        x, y + height,
                        x = width, y + height};
        return result;

    }

    /////// abstraction!
    public abstract void paint(Graphics g);



    /////// public properties

    // X
    public int getX()
    {
        return x;
    }
    
    public void setX(int value)
    {
        x = value;
    }

    // Y
    public int getY()
    {
        return y;
    }

    public void setY(int value)
    {
        y = value;
    }

    // WIDTH
    public int getWidth()
    {
        return width;
    }

    public void setWidth(int value)
    {
        width = value; 
    }

    // HEIGHT
    public int getHeight()
    {
        return height;
    } 

    public void setHeight(int value)
    {
        height = value;
    }
        
    
}


