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



	
public class Candidate_5_10 extends AdvancedRobot
{
public void run()
 {
setTurnGunLeft(((getGunHeadingRadians() + 13) + 46));
}


public void onHitByBullet(HitByBulletEvent e)
 {
setTurnGunRight((53 - 87));
execute();
}


public void onHitRobot(HitRobotEvent e)
 {
}


public void onHitWall(HitWallEvent e)
 {
execute();
setTurnRadarRight((getRadarTurnRemainingRadians() + getGunHeadingRadians()));
}


public void onBulletHit(BulletHitEvent e)
 {
setMaxVelocity((5 / 26));
setMaxTurnRate((getTurnRemainingRadians() * getRadarTurnRemainingRadians()));
}


public void onBulletMissed(BulletMissedEvent e)
 {
setTurnLeft((getRadarTurnRemainingRadians() + getRadarTurnRemainingRadians()));
}


public void onBulletHitBullet(BulletHitBulletEvent e)
 {
setBack((68 * 23));
}


public void onScannedRobot(ScannedRobotEvent e)
 {
setAhead((getGunTurnRemainingRadians() * getGunHeadingRadians()));
}


}