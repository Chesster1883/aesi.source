package aesi;

import java.util.Arrays;

import robocode.control.events.*;
import robocode.control.*;
import robocode.BattleResults;
import javax.tools.*;
import java.util.ArrayList;


/**
 * The class that evaluates a population and assigns fitness values but also compiles chromosomes.
 * This class has a lot of "unimplemented" overridden methods that are of no use for us. 
 * 
 * @author Jeroen Rooijmans
 * @author Maarten de Waard
 * @author Maarten Inja
 *
 */
public class AESIFitnessFunction implements IBattleListener 
{ 	

    private BattlefieldSpecification battlefieldSpecification = new BattlefieldSpecification();
    private RobocodeEngine robocodeEngine;
    private RobotSpecification[] robotSpecification;
    // arguments could be more dynamical. This actually should be the case.
    private BattleSpecification battleSpecification;
    private JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
    private BattleResults[] battleResults;
    private Program program;

    public AESIFitnessFunction (Program iProgram)
    {
	robocodeEngine = new RobocodeEngine();
        robocodeEngine.addBattleListener(this);
        program = iProgram;

        Program.print("Fitness Function created!\n");
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
    
    public void evaluate(AESIChromosome chromosome)
    { 
	chromosome.writeAndCompile(Program.DEFAULT_JAVA_FILE_PATH, Program.DEFAULT_CLASS_FILE_PATH);

	robotSpecification = robocodeEngine.getLocalRepository(Program.DEFAULT_CLASS_FILE_PATH + chromosome.getName() + "," + Program.opponent); 

	battleSpecification = new BattleSpecification(Program.nr_of_fitness_rounds, battlefieldSpecification, robotSpecification);

	robocodeEngine.runBattle(battleSpecification);
	robocodeEngine.waitTillBattleOver();

        if (battleResults == null)
            return;

        // the commented values are fitness functions that seemed to have horrible results

        double s1 = battleResults[0].getScore();
        double s2 = battleResults[1].getScore(); 
        double value = 100 * (1 + ((s1 - s2)/(s1 + s2)));
        //double value = (double)(battleResults[0].getScore() - battleResults[1].getScore());
	    //double value = (double)battleResults[0].getScore(); 
        //double value = 1000*(double)(1 +battleResults[0].getScore())/(double)(battleResults[0].getScore() + battleResults[1].getScore() + 1);
        Program.print("Robots fitness value: " + value + "\n");
        chromosome.setFitnessValue(value);  
    }


	public void onBattleCompleted(BattleCompletedEvent e) 
	{
        battleResults = e.getIndexedResults();
	    for (BattleResults br : e.getIndexedResults())
	     	Program.print(br.getTeamLeaderName() + " scored: " + br.getScore() + "\n");    
	    //Program.print("Battle Completed:\n");
	}
	
	public void onBattleError(BattleErrorEvent e) 
	{
	    System.err.print("ERROR: " + e.getError() + "\n");	
	}

	public void onBattleFinished(BattleFinishedEvent e) 
	{
        /*
	    if (e.isAborted())
		    Program.print("aborted!\n");
	    else
		    Program.print("not aborted!\n");
        */
	}

	public void onBattleMessage(BattleMessageEvent e) 
	{
        // Program.print("Battle Message:" + e.getMessage() + "\n");
	}

	public void onBattlePaused(BattlePausedEvent arg0) 
	{
	    // TODO Auto-generated method stub

	}

	public void onBattleResumed(BattleResumedEvent arg0) 
	{
	    // TODO Auto-generated method stub

	}

	public void onBattleStarted(BattleStartedEvent arg0) 
	{
	    // TODO Auto-generated method stub

	}

	public void onRoundEnded(RoundEndedEvent arg0) 
	{
	    // TODO Auto-generated method stub
	    //Program.print("round ended\n");
	}

	public void onRoundStarted(RoundStartedEvent arg0) 
	{
	    // TODO Auto-generated method stub

	}

	public void onTurnEnded(TurnEndedEvent arg0) 
	{
	    // TODO Auto-generated method stub

	}

	public void onTurnStarted(TurnStartedEvent arg0) 
	{
	    // TODO Auto-generated method stub
	}



}

