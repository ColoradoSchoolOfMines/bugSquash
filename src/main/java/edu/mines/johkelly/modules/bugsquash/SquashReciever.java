package edu.mines.johkelly.modules.bugsquash;

import edu.mines.acmX.exhibit.input_services.hardware.devicedata.HandTrackerInterface;
import edu.mines.acmX.exhibit.stdlib.graphics.Coordinate3D;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.GestureReceiver;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.Gesture;
import edu.mines.acmX.exhibit.stdlib.input_processing.tracking.HandTrackingUtilities;
import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/3/13
 * Time: 6:01 PM
 */
public class SquashReciever extends GestureReceiver {

    BugSquashModule theGame;
    HandTrackerInterface driver;
    PApplet canvas;

    public SquashReciever(BugSquashModule game, HandTrackerInterface driver, PApplet canvas){
        theGame = game;
        this.driver = driver;
        this.canvas = canvas;
    }

    public void GestureRecognized(Gesture gest){

        if(!gest.getName().equals("push")){
            return;
        }

        Coordinate3D start = gest.getStart();
        Coordinate3D end = gest.getEnd();

        Coordinate3D avg = new Coordinate3D((start.getX() + end.getX()/2),
                (start.getY() + end.getY())/2,
                (start.getZ() + end.getZ())/2);

        Coordinate3D picked = start;

        int x = HandTrackingUtilities.getScaledHandX(picked.getX(), driver.getHandTrackingWidth(), canvas.width, 1/6.f);
        int y =  HandTrackingUtilities.getScaledHandY(picked.getY(), driver.getHandTrackingHeight(), canvas.height, 1/6.f);

        theGame.squash(x, y);


        picked = end;
        x = HandTrackingUtilities.getScaledHandX(picked.getX(), driver.getHandTrackingWidth(), canvas.width, 1/6.f);
        y =  HandTrackingUtilities.getScaledHandY(picked.getY(), driver.getHandTrackingHeight(), canvas.height, 1/6.f);

        theGame.squash(x, y);
    }
}
