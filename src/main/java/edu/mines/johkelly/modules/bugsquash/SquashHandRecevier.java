package edu.mines.johkelly.modules.bugsquash;

import edu.mines.acmX.exhibit.stdlib.graphics.Coordinate3D;
import edu.mines.acmX.exhibit.stdlib.graphics.HandPosition;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.HandReceiver;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/3/13
 * Time: 11:31 PM
 */
public class SquashHandRecevier extends HandReceiver {
	private BugSquashModule theGame;
	Map<Integer, Coordinate3D> hands;

	public SquashHandRecevier(BugSquashModule instance, Map<Integer, Coordinate3D> hands) {
		this.theGame = instance;
		this.hands = hands;
	}

	public void handCreated(HandPosition handPos) {
		System.out.println(new Throwable().getStackTrace()[0]);
		hands.put(handPos.getId(), handPos.getPosition());
		theGame.usefulUpdate();
	}

	public void handDestroyed(int id) {
		hands.remove(id);
	}

	@Override
	public void handUpdated(HandPosition pos) {
		hands.put(pos.getId(), pos.getPosition());
		if(hands.containsKey(pos.getId())) theGame.usefulUpdate();
	}
/*
	private synchronized void scheduleExit() {
		if(timey == null) {
			timey = new Timer();
			timey.schedule(
					new TimerTask() {
						@Override
						public void run() {
							theGame.getHandDriver().clearAllHands();
							theGame.destroy();
						}
					},
					5000
			);
		}
	}

	private synchronized void cancelExit() {
		if(timey != null) {
			timey.cancel();
			timey = null;
		}
	}*/
}
