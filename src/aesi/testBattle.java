package aesi;
import robocode.control.*;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.BattleMessageEvent;
import robocode.control.events.BattlePausedEvent;
import robocode.control.events.BattleResumedEvent;
import robocode.control.events.BattleStartedEvent;
import robocode.control.events.IBattleListener;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.events.TurnStartedEvent;


/**
 * 
 * @author Jeroen Rooijmans
 *
 * @deprecated Used to test and learn how we can run robocode from our program. Delete when everyone is sure we will never need it anymore?
 */
public class testBattle implements IBattleListener{

	public static void main(String[] args) 
	{
		new testBattle();
	}

	public testBattle()
	{
		BattlefieldSpecification battlefieldSpecification = new BattlefieldSpecification();
		
		RobocodeEngine robocodeEngine = new RobocodeEngine(this);
		RobotSpecification[] robotSpecification = robocodeEngine.getLocalRepository();
		BattleSpecification battleSpecification = new BattleSpecification(10, battlefieldSpecification, robotSpecification);
		System.out.println("Starting battles");
		robocodeEngine.runBattle(battleSpecification);
	}
	
	@Override
	public void onBattleCompleted(BattleCompletedEvent arg0) {
		System.out.println("SHIT!!!");
		
	}

	@Override
	public void onBattleError(BattleErrorEvent arg0) {
System.err.println(arg0.getError());		
	}

	@Override
	public void onBattleFinished(BattleFinishedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBattleMessage(BattleMessageEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBattlePaused(BattlePausedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBattleResumed(BattleResumedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBattleStarted(BattleStartedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoundEnded(RoundEndedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoundStarted(RoundStartedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTurnEnded(TurnEndedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTurnStarted(TurnStartedEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
