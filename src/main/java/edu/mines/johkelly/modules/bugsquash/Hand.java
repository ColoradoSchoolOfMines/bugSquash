package edu.mines.johkelly.modules.bugsquash;

import edu.mines.acmX.exhibit.stdlib.graphics.Coordinate3D;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import edu.mines.acmX.exhibit.stdlib.input_processing.tracking.HandTrackingUtilities;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.HandTrackerInterface;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/4/13
 * Time: 12:23 AM
 */
public abstract class Hand {

	static public void draw(PApplet canvas, PImage sprite, Coordinate3D p, HandTrackerInterface driver) {
		canvas.imageMode(PConstants.CENTER);
		canvas.pushMatrix();
		canvas.translate(HandTrackingUtilities.getScaledHandX(p.getX(), driver.getHandTrackingWidth(), canvas.width, 1/6.f),
				HandTrackingUtilities.getScaledHandY(p.getY(), driver.getHandTrackingHeight(), canvas.height, 1/6.f));
		canvas.image(sprite, 0, 0);
		canvas.popMatrix();
		canvas.imageMode(PConstants.CORNER);
	}
}
