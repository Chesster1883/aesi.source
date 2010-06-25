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



	
public class BestOf_2 extends AdvancedRobot
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
setFire((enemyEnergy + 0));
setBack(18);
setBack(bulletBearing);
setMaxVelocity(51);
}


public void onScannedRobot(ScannedRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();
enemyDistance = e.getDistance();
enemyHeading = e.getHeading();

setTurnGunLeft((wallBearing + getTurnRemainingRadians()));
setAhead(28);
}


public void onHitWall(HitWallEvent e)
 {
wallBearing = e.getBearing();

setMaxVelocity(getGunHeadingRadians());
setAhead(wallBearing);
}


public void onHitRobot(HitRobotEvent e)
 {
enemyBearing = e.getBearing();
enemyEnergy = e.getEnergy();

setFire(getGunTurnRemainingRadians());
execute();
setFire(bulletHeading);
execute();
}


public void onHitByBullet(HitByBulletEvent e)
 {
bulletBearing = e.getBearing();
bulletHeading = e.getHeading();

setTurnLeft(4);
setTurnLeft(getGunTurnRemainingRadians());
setMaxVelocity(54);
}


public void onBulletMissed(BulletMissedEvent e)
 {
execute();
setTurnGunRight(41);
setTurnGunRight(getGunHeadingRadians());
}


public void onBulletHitBullet(BulletHitBulletEvent e)
 {
setFire(((enemyHeading - getTurnRemainingRadians()) / 92));
}


public void onBulletHit(BulletHitEvent e)
 {
enemyEnergy = e.getEnergy();

setMaxVelocity((enemyDistance - getDistanceRemaining()));
setTurnLeft(27);
setBack(9);
}


}