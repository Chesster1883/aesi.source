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



	
public class BestOf_17 extends AdvancedRobot
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
int n = 0;

}


public void onScannedRobot(ScannedRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();
enemyDistance = e.getDistance();
enemyHeading = e.getHeading();

setBack(70);
}


public void onHitWall(HitWallEvent e)
 {
wallBearing = e.getBearing();

setMaxTurnRate(24);
}


public void onHitRobot(HitRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();

setFire(((62 / getGunTurnRemainingRadians()) * 1));
}


public void onHitByBullet(HitByBulletEvent e)
 {
bulletBearing = e.getBearing();
bulletHeading = e.getHeading();

setAhead((28 / 50));
setTurnRight(10);
}


public void onBulletMissed(BulletMissedEvent e)
 {
int n = 0;

execute();
setMaxTurnRate((getHeadingRadians() / enemyDistance));
setAhead(bulletBearing);
setAhead(enemyDistance);
}


public void onBulletHitBullet(BulletHitBulletEvent e)
 {
int n = 0;

}


public void onBulletHit(BulletHitEvent e)
 {
enemyEnergy = e.getEnergy();

execute();
}


}