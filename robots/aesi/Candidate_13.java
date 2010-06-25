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



	
public class Candidate_13 extends AdvancedRobot
{
public void run()
 {
setTurnRadarLeft((getHeadingRadians() + getTurnRemainingRadians()));
setMaxTurnRate(getRadarTurnRemainingRadians());
setTurnLeft(40);
execute();
}


public void onHitByBullet(HitByBulletEvent e)
 {
setMaxVelocity((((getHeadingRadians() - 33) - getRadarTurnRemainingRadians()) - (81 / getRadarHeadingRadians())));
setTurnGunRight(getTurnRemainingRadians());
setTurnGunRight(52);
setTurnRight(38);
}


public void onHitRobot(HitRobotEvent e)
 {
setFire(((getRadarTurnRemainingRadians() - 50) / getDistanceRemaining()));
execute();
setTurnRadarLeft(26);
setTurnLeft(getRadarTurnRemainingRadians());
}


public void onHitWall(HitWallEvent e)
 {
execute();
setTurnGunLeft(63);
setTurnGunLeft(getRadarTurnRemainingRadians());
execute();
}


public void onBulletHit(BulletHitEvent e)
 {
setAhead(((9 + getTurnRemainingRadians()) - (getGunHeadingRadians() / 86)));
setTurnGunRight(getGunTurnRemainingRadians());
setBack((76 - 7));
setTurnLeft(getHeadingRadians());
}


public void onBulletMissed(BulletMissedEvent e)
 {
setFire(((getGunHeadingRadians() + 51) / getTurnRemainingRadians()));
setMaxTurnRate((getGunTurnRemainingRadians() / 72));
setTurnRight(63);
setTurnRight(getHeadingRadians());
setTurnRadarLeft(getTurnRemainingRadians());
}


public void onBulletHitBullet(BulletHitBulletEvent e)
 {
setFire(((95 + 64) + 47));
setMaxTurnRate((getTurnRemainingRadians() + 26));
execute();
setTurnGunRight(14);
setTurnRadarRight(getRadarHeadingRadians());
}


public void onScannedRobot(ScannedRobotEvent e)
 {
setMaxTurnRate(57);
setTurnRight((64 - getTurnRemainingRadians()));
setFire(49);
setFire(getDistanceRemaining());
execute();
}


}