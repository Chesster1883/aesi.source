package aesi.io;

import java.io.*;
import aesi.*;

/** The state writer can write a state (population and certain settings) to a
* file. The reason this is a different file is because I want to keep the IO
* file just for "every day use" file manipulation. */
public class StateWriter extends IO
{

    /** Since we have abandoned the JGAP framework at this point I will use a 
    * simple text file with pre-defined rules for each line's value and reference
    */
    public static boolean safePopulation(AESIChromosome[] population, String file)
    {
        return false;            
    }

    
    public static AESIChromosome[] readPopulation(String file)
    {
        return null;
    }

}
