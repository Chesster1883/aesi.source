package aesi;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import aesi.io.*;


/**
 * Holding some genes and minor other information tha as a whole represents a robot in Java code.
 * 
 * @author Maarten Inja */

public class AESIChromosome 
{
    private AESIGene[] genes;
    private String name;
    private double fitnessValue;
    private Program program;
    
    public AESIChromosome (AESIGene[] iGenes, String iName, Program iProgram) 
    {
	genes = new AESIGene[iGenes.length];
	for (int i = 0; i < genes.length; i ++)
	    genes[i] = new AESIGene(iGenes[i]);
        name = new String(iName);
        program = iProgram;
    }

    public AESIChromosome(AESIChromosome in, String iName)
    {
	// copy the genes, not the references
	genes = new AESIGene[in.getGenes().length];
	for (int i = 0; i < genes.length; i ++)
	    genes[i] = new AESIGene(in.getGenes()[i]);
        name = new String(iName);
        fitnessValue = new Double(in.getFitnessValue());
        program = in.getProgram();
    }

    public AESIChromosome(AESIChromosome in)
    {
	// copy the genes, not the references
	genes = new AESIGene[in.getGenes().length];
	for (int i = 0; i < genes.length; i ++)
	    genes[i] = new AESIGene(in.getGenes()[i]);
	name = new String(in.getName());
	fitnessValue = new Double(in.getFitnessValue());
	program = in.getProgram();
    }

    public void mutate()
    {
        genes[Program.random.nextInt(genes.length)].applyMutation();
    }

    public void writeAndCompile(String javaFilePath, String classFilePath)
    {
        // Perhaps we should not declare these variables as local but rather as a property
        // of this instance or even static?
        String dotJavaFile = javaFilePath + name + ".java";
        File file = new File(dotJavaFile);

        IO.writeFile(dotJavaFile, toString(), true);

        try
        {
            // compile returns a boolean, but meh.. 
            compile( new JavaSourceFileObject(file, toString()));
        } 
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    
    public Boolean compile(JavaFileObject... source)
    {
        java.util.ArrayList<String> optionsArray = new java.util.ArrayList<String>();
    	optionsArray.add("-d");
    	optionsArray.add("robots/");
    	return compile(optionsArray, source);	
    }

    public Boolean compile(ArrayList<String> optionsArray, JavaFileObject... source)
    {
    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    	JavaCompiler.CompilationTask task = compiler.getTask( /* default System.err */ null,
            /* standard file manager,
              If we wrote our own JavaFileManager, we could control the location
              of the generated  class files.
              By default they will go in the CWD, which MUST be on the classpath */ null,
            /* standard DiagnosticListener */  null,
            /* no options */  optionsArray,
            /* no annotation classes */  null,
            /* We must convert JavaFileObject... source code list  to Iterable<? extends JavaFileObject> */
            Arrays.asList( source )
            );
        return task.call();	
    }




    /** Returns the code for what is stored in this chromosome as genes.*/
	public String toString() 
    {
        String code = Program.beginFile;

    	code += "public class " + name + " extends AdvancedRobot\n"; 
    	code += "{\n";

        for (String s : Program.privateVariableList)
            code += "private double " + s + ";\n"; 
        code += "\n";

    	    // creating midFile, simple means whatever is in the genes.
	    for (AESIGene g : genes)
		    code += g +"\n\n";
		
	    // creating endFile, }
	    code += "}";
	    
	    return code;
    } 

    public AESIGene[] getGenes()
    {
        return genes;
    }
    
    public void setGenes(AESIGene[] value)
    {
	genes = value;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String value)
    {
        name = value;
    }

    public double getFitnessValue()
    {
        return fitnessValue;
    }
    
    public void setFitnessValue(double value)
    {
        fitnessValue = value;
    }

    public Program getProgram()
    {
        return program;
    }

    /** Returns a copy of itself with a number of mutations, should be used to
    * initialize the beginning population with the default chromosome only. 
    * SHOULD BE STATIC! */
    public AESIChromosome startMutation()
    {
        AESIChromosome ac = new AESIChromosome(this);
        // TODO: set a correct number of start mutations here.
        for (int i = 0; i < 10; i ++)
            ac.getGenes()[Program.random.nextInt(ac.getGenes().length)].applyMutation();
        
        return ac;
    }


}
