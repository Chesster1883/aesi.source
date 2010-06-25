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



	
public class Candidate200 extends AdvancedRobot
{
private double bulletBearing;
private double enemyBearing;
private double enemyEnergy;
private double enemyHeading;
private double enemyDistance;
private double wallBearing;
private double bulletHeading;

public void run()
 {
setBack(40);
execute();
}


public void onScannedRobot(ScannedRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();
enemyDistance = e.getDistance();
enemyHeading = e.getHeading();

setTurnGunLeft(7);
setAhead(0);
}


public void onHitWall(HitWallEvent e)
 {
wallBearing = e.getBearing();

setTurnGunRight((54 / 39));
execute();
}


public void onHitRobot(HitRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();

execute();
setTurnLeft(27);
setAhead(enemyHeading);
}


public void onHitByBullet(HitByBulletEvent e)
 {
bulletBearing = e.getBearing();
bulletHeading = e.getHeading();

setTurnRight(((getRadarTurnRemainingRadians() - enemyDistance) + 75));
setFire(enemyHeading);
}


public void onBulletMissed(BulletMissedEvent e)
 {
execute();
execute();
execute();
}


public void onBulletHitBullet(BulletHitBulletEvent e)
 {
setTurnLeft(((wallBearing / getRadarHeadingRadians()) - 86));
setBack(enemyDistance);
setAhead(getHeadingRadians());
}


public void onBulletHit(BulletHitEvent e)
 {
enemyEnergy = e.getEnergy();

setTurnGunLeft((66 / 49));
setTurnGunRight(getDistanceRemaining());
}


}