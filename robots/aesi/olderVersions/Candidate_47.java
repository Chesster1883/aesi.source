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



	
public class Candidate_47 extends AdvancedRobot
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
back(29);
turnGunRight(getRadarHeadingRadians());
fire(bulletBearing);
}


public void onScannedRobot(ScannedRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();
enemyDistance = e.getDistance();
enemyHeading = e.getHeading();

ahead(92);
fire(getRadarHeadingRadians());
}


public void onHitWall(HitWallEvent e)
 {
wallBearing = e.getBearing();

fire((4 + getGunTurnRemainingRadians()));
turnGunRight((bulletHeading - 82));
}


public void onHitRobot(HitRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();

back(bulletHeading);
ahead(59);
}


public void onHitByBullet(HitByBulletEvent e)
 {
bulletBearing = e.getBearing();
bulletHeading = e.getHeading();

ahead((78 - 79));
fire(bulletBearing);
}


public void onBulletMissed(BulletMissedEvent e)
 {
turnGunLeft(((63 + getHeadingRadians()) / getRadarHeadingRadians()));
turnGunLeft((enemyHeading - 60));
}


public void onBulletHitBullet(BulletHitBulletEvent e)
 {
fire(wallBearing);
back((getHeadingRadians() - 83));
}


public void onBulletHit(BulletHitEvent e)
 {
enemyEnergy = e.getEnergy();

back(((37 * 15) / enemyDistance));
ahead(51);
turnGunLeft(enemyBearing);
}


}