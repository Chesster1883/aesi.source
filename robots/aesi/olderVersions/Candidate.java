package aesi;


import robocode.AdvancedRobot;
import robocode.RobocodeFileOutputStream;
import robocode.*;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import static robocode.util.Utils.normalRelativeAngleDegrees;



	
public class Candidate extends AdvancedRobot
{
public void onHitWall(HitWallEvent e)
{
}


public void onScannedRobot(ScannedRobotEvent e)
{
}


public void run()
{
		turnRight(normalRelativeAngleDegrees(getHeading()));
 		ahead(5000);
 		turnLeft(90);
 		ahead(5000);
 		turnGunLeft(90);
}


}