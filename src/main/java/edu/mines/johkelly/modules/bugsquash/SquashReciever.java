package edu.mines.johkelly.modules.bugsquash;

import edu.mines.acmX.exhibit.stdlib.graphics.Coordinate3D;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.GestureReceiver;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.Gesture;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/3/13
 * Time: 6:01 PM
 */
public class SquashReciever extends GestureReceiver {

	BugSquashModule theGame;

	public SquashReciever(BugSquashModule game){
		theGame = game;
	}

	public void GestureRecognized(Gesture gest){

		if(!gest.getName().equals("Click")){
			return;
		}

		Coordinate3D start = gest.getStart();
		Coordinate3D end = gest.getEnd();

		Coordinate3D avg = new Coordinate3D((start.getX() + end.getX()/2),
				(start.getY() + end.getY()/2),
				(start.getZ() + end.getZ()/2));

		Coordinate3D picked = end;

		int x = (int) picked.getX();
		int y = (int) picked.getY();

		System.out.println("x: " + x + ", y: " + y);


		theGame.squash(x, y);
	}
}
