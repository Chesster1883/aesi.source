package aesi;

import java.util.ArrayList;
import java.util.TreeMap;

/** Manages the population, IMPORTANT, every function that operates 
* assumes the population is sorted first.
* @todo:    Check if the percentage anywhere is actually correct (perhaps
*   we need to do (1 - x) somewhere... 
*
* @author Maarten Inja*/
public class PopulationManager
{

    private AESIChromosome[] population;
    private Program program;
    private AESIFitnessFunction fitnessFunction;


    public PopulationManager(Program iProgram)
    {
	Program.print("Population Manager created!\n");
        program = iProgram; 
        fitnessFunction = program.getFitnessFunction();
        initPopulation();
    }
    
    public AESIGene[] getGenes()
    {
	/*
	TreeMap<String, String[]> tm = program.eventsCallsAndPropertiesTreeMap(); 
	AESIGene[] sampleGenes = new AESIGene[tm.size()];

	for (AESIGene ag : sampleGenes)
	{
	    ag = new AESIGene(tm.lastKey(), tm.get(tm.lastKey()), program);
	    tm.remove(tm.lastKey());
	}
	
        return sampleGenes;
	 */
	ArrayList<AESIGene> sampleGenes = new ArrayList<AESIGene>();
        TreeMap<String, String[]> tm = program.eventsCallsAndPropertiesTreeMap(); 
        while(tm.size() != 0)
        {
            sampleGenes.add(new AESIGene(tm.lastKey(), tm.get(tm.lastKey()), program));
            tm.remove(tm.lastKey());
        }
        AESIGene[] g = new AESIGene[0];
        return sampleGenes.toArray(g);
    }
    

    public void initPopulation()
    {
        // is this bad programming, should this set by an argument?
        population = new AESIChromosome[Program.population_size];

        AESIChromosome defaultChromosome = new AESIChromosome(getGenes(), Program.CANDIDATE_NAME, program);
       
        for (int i = 0; i < population.length; i ++)
            population[i] = defaultChromosome.startMutation(); 

        evaluateBottomXPercent(1, -1); 
        sortOnFitnessValue();
        replaceBottomXPercent(Program.select_from_previous_gen, -1);
    }

    /** Sorts the population based on the fitness value from best to worst using
    * a bubble sort algorithm. */
    public void sortOnFitnessValue()
    {
	Program.print("Sorting on fitness value!\n");
        for (int i = population.length; --i>=0; ) 
            for (int j = 0; j<i; j++) 
                if (population[j].getFitnessValue() < population[j+1].getFitnessValue()) 
                {
                    AESIChromosome T = population[j];
                    population[j] = new AESIChromosome(population[j+1]);
                    population[j+1] = new AESIChromosome(T);
                }
    }
   
    /** NOT FINISHED, x = always 0.5!!!*/
    public void replaceBottomXPercent(double x, int round)
    {
	Program.print("Replacing bottom X percent!\n");
        //x = checkPercentage(1 - x);
        //int to = (int)(population.length * x);
        for (int i = 0; i <= (population.length/2) - 1; i ++)
           population[population.length - i - 1] = new AESIChromosome(population[i], Program.CANDIDATE_NAME + round);
    }
 
    /** Evolves (mutates) the bottom x percent of the population. The 
    * percentage in paramater x should be between 0 and 1. An error is printed
    * and x is set to 1 otherwise.*/
    public void evolveBottomXPercent(double x, int round)
    {
        x = checkPercentage(1 - x); 
        int from = (int)(population.length * x);
        for (int i = from; i < population.length; i ++)
        {
            Program.print("=== Changing a chromosome: " + population[i].getName() + " with fitness value: " + population[i].getFitnessValue() + "\n");
            population[i].setName(Program.CANDIDATE_NAME + round);
            population[i].mutate(); 
        }
    }

    /** Evaluates the bottom x percent of the population. The percentage in 
    * percentage in paramater x should be between 0 and 1. An error is printed
    * and x is set to 1 otherwise.*/
    public void evaluateBottomXPercent(double x, int round)
    {
        x = checkPercentage(1 - x);
        int from = (int)(population.length * x);
        for (int i = from; i < population.length; i ++)
        {
            Program.print("Candidate: " + i + " of " + population.length + " from round " + round + "\n");
            fitnessFunction.evaluate(population[i]);
        }
    }

    public void printPopulation()
    {
        for (AESIChromosome ac : population)
            Program.print(ac.getName() + "\t:\t" + ac.getFitnessValue() + ".\n");
    } 

    public void renameBestIfNeeded(int round)
    {
        if (population[0].getName().contains(Program.CANDIDATE_NAME))
        {
            population[0].setName("BestOf_" + round);
            if(Program.plays_among_self && population[0].getFitnessValue() > Program.plays_among_self_threshold)
        	Program.opponent = "aesi." + population[0].getName();
        }
    }

    /////////////////// properties
    
    public AESIChromosome[] getPopulation()
    {
        return population;
    }

    public aesi.AESIChromosome getFittestChromosome()
    {
        return population[0];
    }
    
    public double getBestFitnessValue()
    {
        return population[0].getFitnessValue();
    }

    public double getAverageFitnessValue()
    {
        double avg = 0;
        for (AESIChromosome ac : population)
            avg += ac.getFitnessValue();
        return avg/population.length;
    }    

    public double getAverageFitnessValueOfBottomXPercent(double x)
    {
        x = checkPercentage(1 - x);
        int from = (int)(population.length * x);
        double avg = 0;
        for (int i = from; i < population.length; i ++)
            avg += population[i].getFitnessValue();
        return avg/(int)(population.length-from); 
    }

    /////////////// static functions

    private static double checkPercentage(double x)
    {
        if (x < 0 || x > 1)
        {
            System.err.print("ERROR: percentage x should be between 0 and 1, setting to 1!");
            return 1d;
        }
        return x;
    }




}
