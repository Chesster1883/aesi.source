package aesi;

import java.util.ArrayList;
import org.jgap.*;

// argument can hold
// * function call (such as getBearing())
// * operator
// * operatands??
// * variables
public class Argument 
{
    private ArrayList<String> arguments = new ArrayList<String> ();

    private MethodCall method;
    private ExpressionTree root;

    public Argument(MethodCall iMethod)
    {
        method = iMethod;
        root = new ExpressionTree();
    }    

    /////////// methods

    // I don't know if change is an acceptable name or the arguments (none)
    // are sufficient.
    public void change()
    {
        //int top = 2 + ((arguments.size() > 0)?1:0);
        /*
        int i = method.getGene().getProgram().getRandom().nextInt(top):
        if (i == 0) // add
            arguments.add(calls[method.getGene().getProgram().nextInt(call.length));
        else if // change, in this case "replace"
            arguments.get(method.getGene().getProgram().getRandom().nextInt(arguments.size())) =  calls[method.getGene().getProgram().nextInt(calls.length);
        else // remove 
            arguments.remove(method.getGene().getProgram().getRandom().nextInt(arguments.size()));
        */
    }

    /////////// public properties

    public String toString()
    {
        String result = "";
        for (String s : arguments)
            result += s;
        return result; 
    }





}
