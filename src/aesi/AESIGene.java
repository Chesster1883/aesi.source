package aesi;

import java.util.ArrayList;

public class AESIGene 
{

    private ArrayList<MethodCall> methods = new ArrayList<MethodCall> (); 
    private Program program;
    private String name;
    private String[] events; 
	
    public AESIGene(String iName, String[] iEvents, Program iProgram) 
    {
        name = iName;
        program = iProgram;
        events = iEvents;
    }	
    
    public AESIGene(AESIGene gene)
    {
	name = new String(gene.getName());
	program = gene.getProgram();
	// copy the events, like clone, so we don't just copy the references
	events = new String [gene.getEvents().length];
	for (int i = 0; i < events.length; i ++)
	    events[i] = new String(gene.getEvents()[i]);
	// copy, don't reference, once more
	for (MethodCall mc : gene.getMethods())
	    methods.add((MethodCall)mc.clone());
    }

    /////////// public properties

    /** Returns the code this gene represents. */
    public String toString()
    {
        
        String result = "public void " + name + "\n {\n";    
        
        // set events in variables


        if (events != null)
        {
            for (int i = 0; i < events.length; i ++)
            {
                result += events[i] + " = ";
                i ++;
                result += events[i] + ";\n";
            }
            result += "\n";
        }


        for(MethodCall m : methods)
            result += m;
        result += "}\n";

        return result;
    }

    public Program getProgram()
    {
        return program;
    }

    public String getName()
    {
	return name;
    }
    
    public String[] getEvents()
    {
	return events;
    }
    
    public ArrayList<MethodCall> getMethods()
    {
	return methods;
    }

////////////// override

   	public void applyMutation() 
	{
   	    // I'm pretty sure we have to call "addSomething, "removeSomething" or "change Something" here.
   	    // This is most likely one of the most important methods to implement.

   	    // Applies a mutation of a certain intensity (indicated by a value between -1 and 1: percentage)
   	    // onto the atomic element at given index.
   	    // how mister JGAP defines the index is unknown... :S

   	    // all 3 possible mutations (add, change, remove) have the same chance of being picked
   	    // but "add" is becomes less likely the more methods are already in there. 
   	    //int r = Program.random.nextInt((Program.CHROMOSOME_DEPTH*2) + (Program.CHROMOSOME_DEPTH - methods.size()));
   	    int r = Program.random.nextInt(2);

   	    // no matter what we always need at least one method to change 
   	    // or remove anything! So we add if there's nothing there(!)
   	    if ((methods.size() < Program.chromosome_depth) && (methods.size() == 0 || r == 0)) // add
   	    {
   		methods.add(new MethodCall());
   		return;
   	    }
   	    else if (r == 1) // change

   		methods.get(Program.random.nextInt(methods.size())).change();
   	    else // (r == 2) // remove
   		methods.remove(Program.random.nextInt(methods.size()));
	}
}
