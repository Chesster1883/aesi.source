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



	
public class Candidate0 extends AdvancedRobot
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

setMaxVelocity(21);
}


public void onScannedRobot(ScannedRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();
enemyDistance = e.getDistance();
enemyHeading = e.getHeading();

setTurnRight(24);
}


public void onHitWall(HitWallEvent e)
 {
wallBearing = e.getBearing();

setTurnLeft((75 - 99));
}


public void onHitRobot(HitRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();

}


public void onHitByBullet(HitByBulletEvent e)
 {
bulletBearing = e.getBearing();
bulletHeading = e.getHeading();

setTurnGunRight(6);
execute();
setTurnGunRight(38);
}


public void onBulletMissed(BulletMissedEvent e)
 {
int n = 0;

setBack((enemyBearing * 72));
}


public void onBulletHitBullet(BulletHitBulletEvent e)
 {
int n = 0;

setMaxVelocity(getDistanceRemaining());
}


public void onBulletHit(BulletHitEvent e)
 {
enemyEnergy = e.getEnergy();

}


}