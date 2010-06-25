package aesi.graphicalR;


import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import aesi.*;


/** A graphical fitness value is a window that can plot fitness values throughout generations. It extends JFrame but 
* uses own components (DComponents) to render and store anything that happens. This may definitely not be the best
* solution, it certainly was the easiest as most of it came from a different project. 
* <p>
* The fitness values are plotted with a maximum scale. Different lines internally are identified only by color.
* <p>
* I'm not sure if we want to manage the lines in this class or not.. but we can always change that!
* @author Maarten Inja */
public class GraphicsFitnessValue extends JFrame
{
    private Program program;

    private ArrayList<DComponent> components = new ArrayList<DComponent> ();
    private Hashtable<Color, ArrayList<Line>> lines = new Hashtable<Color, ArrayList<Line>> (); 

    private int deltaPixelsPerValue;
    private int deltaHeightPerValue;


    public GraphicsFitnessValue(Program iProgram)
    {
        program = iProgram;
        initializeThis();
        Program.print("Graphical Representation of the Fitness Value created!\n");
    }

    private void initializeThis()
    {
        setSize(Program.SCREEN_WIDTH, Program.SCREEN_HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setBackground(Color.white);
    }

    /** Necessarily override the paint method!*/
    public void paint(Graphics g)
    {
        g.setColor(Program.BACKGROUND_COLOR);
        g.fillRect(0, 0, (int)getSize().getWidth(), (int)getSize().getHeight());
        int length = components.size();
        for(int i = 0; i < length; i ++)
            components.get(i).paint(g); 
    }

    /** Adds a fitness value that the window this object represent should start
    * drawing from (and to). We assume fromRound is the last round we want to 
    * represent in the window. In the future we might want to change this and make
    * this optional (by choosing Program.MAX_EVOLUTIONS as the number of values
    * to <i>always</i> represent. 
    * 
    * @todo:    <li>Create the option to have a fixed size (number of values) to draw
    * in this window (but also the height). 
    */
    public void addFitnessValue(double fitnessValue, int fromRound, Color color)
    {
        // change fromRound in Program.MAX_EVOLUTIONS in the next line ;)
        deltaPixelsPerValue = (int)Math.floor(((getSize().getWidth() - Program.TOTAL_BORDER_WIDTH)/(fromRound+1))); 
        deltaHeightPerValue = (int)(Math.floor((getSize().getHeight() - Program.TOTAL_BORDER_WIDTH))/Math.ceil(getMaxFitnessValue()));

        Line line = new Line(fitnessValue, color);
        ArrayList<Line> lineList = lines.get(color);
        if (lineList == null)
        {   
            lineList = new ArrayList<Line> ();
            lineList.add(new Line(0, color));
            lines.put(color, lineList);
        }
        lineList.add(line);
        add(line);
        reInitComponents();
    }

    /** Iterates through all lines and reInits them. Since the lines are 
    * stored in a hashtable we first iterate over that and then use 
    * (@link #reInitComponents(ArrayList<Line> lines) to finish the job. */
    public void reInitComponents()
    {
        //Program.print("delta pixel per value " + deltaPixelsPerValue + "\n");
        //Program.print("delta height per value" + deltaHeightPerValue + "\n");

        // iterate through the hashmap and reInit every different line
        Enumeration e = lines.keys(); 
        while(e.hasMoreElements())
            reInitComponents(lines.get(e.nextElement()));
    } 

    public void reInitComponents(ArrayList<Line> lines)
    {
        lines.get(0).reInit(deltaPixelsPerValue, deltaHeightPerValue);
        for (int i = 1; i < lines.size(); i ++)
            lines.get(i).reInit(lines.get(i-1).getX2(), lines.get(i-1).getY2(), i * deltaPixelsPerValue, deltaHeightPerValue); 
    }

    /** Finds out the maximum value stored in any of the line objects. */
    public double getMaxFitnessValue()
    {
        double max = 0;
        for (DComponent c : components)
            if (c instanceof Line)
            {
                Line l = (Line)c;
                if (l.getValue() >= max)
                    max = l.getValue();
            }     
        return max;
    }


    /** Checks if a line is already added. The method used is inconclusive 
    * however, the chance for a false positive is very, very small.
    * @deprecated There currently is no replacement. As the description said
    * it can be used but there is a chance for false positives. */
    public boolean contains(double value)
    {
        Enumeration e = lines.keys(); 
        while(e.hasMoreElements())
            for (Line l : lines.get(e.nextElement()))
                if (l.getValue() == value)
                    return true;
        return false;
    }

    public Line add(Line line)
    {
        components.add(line);
        repaint();
        return line;
    } 

    // regular add (add on top)
    public DComponent add(DComponent comp) 
    {
        components.add(comp);
        repaint();
        return comp; 
    }

    // we might want to add this at the bottom
    public DComponent add(DComponent comp, boolean addToBottom)
    {  
        if (!addToBottom)
            return add(comp);

        components.add(0, comp);
        repaint();
        return comp;
    }

   
    // public properties
    public FontMetrics getFontMetrics()
    {
        return getFontMetrics(getFont()); 
    }
}





                                     
