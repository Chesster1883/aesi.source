package aesi;

import java.util.ArrayList;
import java.util.Random;
import org.jgap.*;

// methodcall holds the name of a method and the arguments loaded 
// with it. Possibly also a function to change arguments.
public class MethodCall implements Cloneable
{
    // name is fire or turnGun
    private String name;
    // arguments for name, as in name( argument ). Argument is now
    // an expressiontree
    private ExpressionTree argument;

    private static String[] possibleMethods = {"setAhead", "setBack", "setFire", "setMaxTurnRate", "setMaxVelocity", "setTurnLeft", "setTurnGunLeft", "setTurnGunRight", "setTurnRight", "execute", "execute"};
    // deleted "setTurnRadarLeft", "setTurnRadarRight",to see if we can do awesome stuff.
    //private static String[] possibleMethods = {"ahead", "back", "fire", "turnGunLeft", "turnGunRight", "turnLeft", "turnRight" }; // "turnRadarLeft", "turnRadarRight"
    
    
    public MethodCall()
    {
        name = possibleMethods[Program.random.nextInt(possibleMethods.length)];
        if (!name.equals("execute"))
            argument = new ExpressionTree(); 
    }
 
    public Object clone()
    {
	MethodCall result = new MethodCall();
	result.setName(new String(name));
	if (!name.equals("execute"))
	    result.setArgument((ExpressionTree) argument.clone());
	return result;
    }

    public void change()
    {
        if (name.equals("execute"))
            return;
        int r = Program.random.nextInt(2);
        if (r == 0) // add
            argument.addChild();
        else if (r == 1) // change 
            argument.changeTree();
        else // remove
            argument.removeChild();
    }

    /////////// public properties

    public String toString()
    {
        if (name.equals("execute"))
            return name + "();\n";
        return name + "(" + argument.toString() + ");\n";
    }
    
    public String getName()
    {
	return name;
    }
    
    public void setName(String value)
    {
	name = value;
    }
    
    public ExpressionTree getArgument()
    {
	return argument;
    }
    
    public void setArgument(ExpressionTree value)
    {
	argument = value;
    }

	
}
