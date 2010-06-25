package aesi;

import java.util.Random;
import java.net.URISyntaxException;
import java.io.File;
import java.util.TreeMap;
import java.util.ArrayList;
import aesi.graphicalR.*;
import java.awt.Color;
import aesi.io.*;

/**
 * The main class of this program. Not only does this hold all the variables that define the settings
 * such as what the opponent is or where the log file can be found 
 * but also other interesting variables that can be interesting for every class such as the random generator
 * or the reference to the fitness function.
 * <p>
 * 
 * @author Maarten de Waard
 * @author Maarten Inja
 */
public class Program
{
    //////////////////// variables in the public static final field

    public static final java.awt.Color BACKGROUND_COLOR = Color.white;
    public static final java.awt.Color BEST_FIT_COLOR = Color.red;
    public static final java.awt.Color AVG_FIT_COLOR = Color.blue;
    public static final java.awt.Color AVG_NEW_FIT_COLOR = Color.green;
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static final int TOTAL_BORDER_WIDTH = 30;
    
    /** The percentage that is kept from the previous generation. Currently only 50% is implemented. */
    public static final double select_from_previous_gen = 0.5d;
    /** The file in which we write all the events we also print in the terminal. */
    public static final String LOG_FILE = "log.txt";
    /**
     * The path to the folder chromosomes are printed as .java files. We need to write the java files to 
     * a file in order to compile them (for example in the fitness function). To find such a file all one
     * would need to add is a name and the .java extension
     */
    public static final String DEFAULT_JAVA_FILE_PATH = "robots/aesi/"; // "add name and .java" 
    /**The path that defines how we can reach .class files that can be used by the robocodeEngine. Previously 
     * assumed to define the .class alternative
     * to the DEFAUL_JAVA_FILE_PATH. This is used for example by the robocodeEngine which can use it to load 
     * the robot the class file defines. All one would need to add is the name and not the .class extension.*/
    public static final String DEFAULT_CLASS_FILE_PATH = "aesi.";  // "add name"
    /** The path to the folder chromosomes are printed as .java files. We need to write the java files to 
     * a file in order to compile them. This variables defines a path to the robocode program so one can
     * experience the battle.  To find such a file all one
     * would need to add is a name and the .java extension */
    public static final String ROBOCODE_JAVA_FILE_PATH = "../../robots/aesi/";
    /** Irrelevant, should be deleted from the program */
    public static final String ROBOCODE_CLASS_FILE_PATH = "aesi."; // is this only needed for the robocode engine, getLocalRepository 
    /**
     * The file the is that holds the start of a robot. This part is always the same and can just as well be read 
     * from file rather than written down in java print statements. 
     */
    public static final String beginFile = IO.readFile("empty.txt");
    /** The name we use to define a chromosome when we print and link to it. An idea is to add an version ID in 
     * order to not automagically overwrite older version candidates.  */
    public static final String CANDIDATE_NAME = "Candidate";
    
    
    //////////////////// variables in the public static  field
    // The program should have the option to set these using command line arguments.. 

    public static boolean use_fit_gui = true;
    /** The number of chromosomes in a population. More is better but also
     * a lot more time consuming.*/
    public static int population_size = 50;
    /** The number of battles the fitness functions simulates to find the fitness value. When more rounds
     * are simulated the score is more accurate, resulting in a better (more accurate) fitness value. However
     * this is also more time consuming. We should try to strike to golden mean for maximum results.*/
    public static int nr_of_fitness_rounds = 50;
    /** Once in ten rounds we test if the robot's fitness value is accurate. This is done in this number of rounds. */
    public static int nr_of_reevaluate_rounds = 100;
    /** The maximum a value can be in an expression. The ExpressionTree class consists of operators and values,
     * the values can consist of calls but also of numbers. The maximum such a value can be is defined here. 
     * Negative values not yet implemented.  */
    public static int top_value = 100; 
    /** The maximum number of methods in an event. This is probably not needed anymore as 
     * the program balances this out itself!!*/
    public static int chromosome_depth = 5;
    /** When the robot population should evolve against his best candidate, 
     * this boolean should be set to true. This means the string opponont will be rewritten
     * to an "BestOf_". */
    public static boolean plays_among_self = false;
    /**When the robot population should evolve against his best candidate,
     * this integer indicates after how many rounds they must begin battling themselves.
     * It must not be zero, because in the beginning robots do not know how to fight.  */
    public static int plays_among_self_threshold = 30;  
    /** The random generator that is used throughout the program. */   
    public static Random random;
    /**The opponent our populations battles with. This can be any opponent, for example we might want to 
     * change this to a bot from the population itself!     */
    public static String opponent = "sample.SpinBot"; 
    /** The list of private variables of the type double (the variables that our evolved robots store). */
    public static String[] privateVariableList = {"bulletBearing", "enemyBearing", "enemyEnergy", "enemyHeading", "enemyDistance", "wallBearing", "bulletHeading"};
    /** The possible method calls for the ExpressionTree. */
    public static String[] possibleCalls = { "getDistanceRemaining()", "getHeadingRadians()", "getGunHeadingRadians()", "getRadarHeadingRadians()", "getGunTurnRemainingRadians()", "getRadarTurnRemainingRadians()", "getTurnRemainingRadians()" };
    /** The possible operators for the ExpressionTree.*/
    public static String[] possibleOperators = { "+", "-", "/", "*" };

    ////////////////////////// local variables

    /** The fitnessFunction that evaluates our population. */
    private AESIFitnessFunction fitnessFunction;
    /** The object that graphically represents fitness values throughout the generations. */
    private GraphicsFitnessValue fitGui;

    /**
     * The sole constructor for our program. Currently it does not only construct an instance
     * of our Program but also executes the whole evolution process. The latter should be executed in 
     * a method rather than the constructor.
     * 
     * @throws InvalidConfigurationException
     * @todo Instead of executing the whole genetic evolution algorithm in this constructor we should
     * create a special method for this.
     */
    public Program () 
    {
        // reset stuff!
        IO.deleteFile(LOG_FILE);
        
        random = new Random();    
        if (use_fit_gui)
            initializeFitGui();
        run();
    }

    private PopulationManager populationManager;

    /** The new run version that simply uses the PopulationManager class instead
    * of the for us useless JGAP framework. 
    *
    * @todo: Be able to start from a state (loaded by the io.StateSafer class)*/
    public void run ()
    {
        fitnessFunction = new AESIFitnessFunction(this);
        populationManager = new PopulationManager(this);
        
        print("Initialized population, printing population:\n");
        populationManager.printPopulation();

        ///////// main loop here
        for (int q = 0; q < 99999; q ++)
        {   
            print("------------- NEW GENERATION: " + q + "\n");
            print(" * Current highest fitness value: " + populationManager.getBestFitnessValue() + ", by name: " + populationManager.getFittestChromosome().getName() + "\n");

            populationManager.evolveBottomXPercent(select_from_previous_gen, q);
            if(!opponent.contains("BestOf_"))
        	populationManager.evaluateBottomXPercent(select_from_previous_gen, q);
            else
        	populationManager.evaluateBottomXPercent(1, q);
            
            print("Done evolving and evaluating, printing population:\n");
            populationManager.printPopulation(); 
            
            // re-evaluate the complete population to make sure everything is going fine
            if ((!opponent.contains("BestOf_") || !plays_among_self) && q > 11 && q % 10 == 0)
            {
        	print("!!!!!!!!!!!!Re evaluating total population, just to be safe!\n");
        	int t = nr_of_fitness_rounds;
        	nr_of_fitness_rounds = nr_of_reevaluate_rounds;
        	populationManager.evaluateBottomXPercent(1, q);
        	nr_of_fitness_rounds = t;
            }
            

            double avg = populationManager.getAverageFitnessValueOfBottomXPercent(select_from_previous_gen);
            
            populationManager.sortOnFitnessValue();

            populationManager.renameBestIfNeeded(q);
            populationManager.getFittestChromosome().writeAndCompile(DEFAULT_JAVA_FILE_PATH, DEFAULT_JAVA_FILE_PATH);
            
            print("Sorted on fitness value and renamed best if needed, printing population:\n");
            populationManager.printPopulation(); 
            
            print(" * Average of all: " + populationManager.getAverageFitnessValue());
            print("\n * Average of new chromosomes: " + avg + "\n");
            
            if (use_fit_gui)
            {
                fitGui.addFitnessValue(populationManager.getBestFitnessValue(), q, BEST_FIT_COLOR); 
                fitGui.addFitnessValue(populationManager.getAverageFitnessValue(), q, AVG_FIT_COLOR);
                fitGui.addFitnessValue(avg, q, AVG_NEW_FIT_COLOR);
            }

           populationManager.replaceBottomXPercent(select_from_previous_gen, q);
           
           print("Replaced bottom with top, printing population:\n");
           populationManager.printPopulation(); 

        }
    }

    private void initializeFitGui()
    {
        fitGui = new GraphicsFitnessValue(this);
        fitGui.setTitle("Graphical Representation of the Fitness Values Throughout the Generations");
    } 

    /**
     * Prints an object to both the terminal and the log file. Please use this as textual output at any time
     * as previous projects have proven the effectiveness of doing this. 
     * <p>
     * <b>HOWEVER</b>, recently it seemed as if Java had this INBUILT!  Further investigation is needed to 
     * check if this is the case. We might still be better of using this as it could be extremely handy to 
     * have one central place every where output passes through, ready to be managed.
     * 
     * @param o	The object to print. This could be anything.
     * @see #println(Object o)
     */
    public static void print(Object o)
    {
        System.out.print(o.toString());
        IO.writeFile(LOG_FILE, o.toString());
    }

    /**
     * Same as print but also adds a newline. 
     * 
     * @param o	The object to print. This could be anything. 
     * @see #print(Object o)
     */
    public static void println(Object o)
    {
        print(o + "\n");
    }


    ///////// public properties

    public AESIFitnessFunction getFitnessFunction()
    {
        return fitnessFunction;
    }

    /** The hashmap that stores events, important calls and their properties which can all be stored in local variables in classes/genes.*/
    public TreeMap<String, String[]> eventsCallsAndPropertiesTreeMap()
    {
        String[][] variables = {{"bulletBearing", "e.getBearing()", "bulletHeading", "e.getHeading()"},
        {"enemyBearing", "e.getBearing()", "enemyEnergy", "e.getEnergy()"},
        {"wallBearing", "e.getBearing()"},
        {"enemyEnergy", "e.getEnergy()"},
        {"enemyBearing", "e.getBearing()", "enemyEnergy", "e.getEnergy()", "enemyDistance", "e.getDistance()", "enemyHeading", "e.getHeading()"}};

        String[] vod = {"int n", "0"};
        
        TreeMap<String, String[]> tm = new TreeMap<String, String[]>();
        tm.put("run()", vod);
        tm.put("onHitByBullet(HitByBulletEvent e)", variables[0]);
        tm.put("onHitRobot(HitRobotEvent e)", variables[1]);
        tm.put("onHitWall(HitWallEvent e)", variables[2]);
        tm.put("onBulletHit(BulletHitEvent e)", variables[3] );
        tm.put("onBulletMissed(BulletMissedEvent e)", vod);
        tm.put("onBulletHitBullet(BulletHitBulletEvent e)", vod);
        tm.put("onScannedRobot(ScannedRobotEvent e)", variables[4] );
        return tm;
    }    

    /////////// main
    
    /** The string printed when command line arguments can not be parsed or when the user asks for this. */
    public static String help = "This is wrong, you can give the following arguments: \n" +
    "\n  -g <boolean>: True when a graphical representation is wanted, false otherwise, default is: " + Program.use_fit_gui + 
    "\n  -s <integer>: The size of the population, default: " + Program.population_size + 
    "\n  -r <integer>: The number of rounds when evaluating a candidate (more is better and slower): " + Program.nr_of_fitness_rounds + 
    "\n  -e <integer>: The number of rounds when reevaluating the total population: " + Program.nr_of_reevaluate_rounds +
    "\n  -o <opponent package>.<opponent name>: The bot you want to evolve on, standard is: " + Program.opponent +
    "(This opponent should be in the robots/<package> directory)" +
    "\n  -a <integer>: The minimum fitness value you want the population to have before " +
    "the bots are evaluated using the best of the previous generation.\n\n"+ 
    "\n  -t <integer>: The maximum a random value can be in an expression tree, default: " + Program.top_value + 
    "\n  -d <integer>: How many methods an event can hold as maximum, default: " + Program.chromosome_depth + 
    "\n  -? Prints this help\n\n";
    /** Main entry point of the program, tries to create an instance of Program and parses the command 
     * line arguments
     * <p>
     * @param args	Command line arguments to be parsed (currently nothing is done with this variable!
     */
    public static void main(String args[])
    {
	print("Starting the program!\n");
	for (int i = 0; i < args.length; i ++)
	{
	    print("Parsing command line arguments\n");
	    String current = args[i];
	    i++;
	    try
	    {
		if (current.equals("-g"))
		{
		    println(args[i]);
		    Program.use_fit_gui = Boolean.parseBoolean(args[i]);
		    print("Using a graphical representation: " + Program.use_fit_gui + "\n");
		}
		else if (current.equals("-s"))
		{
		    Program.population_size = Integer.parseInt(args[i]);
		    print("Population size: " + Program.population_size + "\n");
		}
		else if (current.equals("-r"))
		{
		    Program.nr_of_fitness_rounds = Integer.parseInt(args[i]);
		    print("The number of rounds when evaluating a candidate: " + Program.nr_of_fitness_rounds + "\n");
		}
		else if (current.equals("-e"))
		{
		    Program.nr_of_reevaluate_rounds = Integer.parseInt(args[i]);
		    print("The number of rounds when reevaluating the total population: " + Program.nr_of_fitness_rounds + "\n");
		}
		else if (current.equals("-o"))
		{
		    Program.opponent = args[i];
		    print("Opponent: " + opponent + "\n");
		}
		else if(current.equals("-a"))
		{
		    Program.plays_among_self = true;
		    Program.plays_among_self_threshold = Integer.parseInt(args[i]);
		    print("Evaluates using previous best after: " + Program.plays_among_self_threshold + "\n");
		}
		else if (current.equals("-t"))
		{
		    Program.top_value = Integer.parseInt(args[i]);
		    print("The top value: " + Program.top_value + "\n");
		}
		else if (current.equals("-d"))
		{
		    Program.chromosome_depth = Integer.parseInt(args[i]);
		    print("The maximum chromosome depth: " + Program.chromosome_depth + "\n");
		}
		else
		{
		    System.out.print(help);
		    System.exit(0);
		}
	    }
	    catch(Exception e)
	    {
		System.out.print(help);
		System.exit(0);
	    }
	}



	//new aesi.AESIGene();	
	new Program();
    }   
}























