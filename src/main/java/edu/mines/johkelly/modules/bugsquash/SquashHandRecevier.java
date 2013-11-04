package edu.mines.johkelly.modules.bugsquash;

import edu.mines.acmX.exhibit.stdlib.graphics.Coordinate3D;
import edu.mines.acmX.exhibit.stdlib.graphics.HandPosition;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.HandReceiver;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/3/13
 * Time: 11:31 PM
 */
public class SquashHandRecevier extends HandReceiver {
	private BugSquashModule theGame;

	private int ID = -1;
	private Coordinate3D pos = null;



	public SquashHandRecevier(BugSquashModule instance) {
		this.theGame = instance;
	}

	public void handCreated(HandPosition handPos) {
		System.out.println("EGRDSRE");
		if(ID == -1){
			System.out.println("EGRDSRE");
			ID = handPos.getId();
			pos = handPos.getPosition();
			doSquash();
		}
	}

	public void handDestroyed(int id) {
		if(id == ID)
			ID = -1;
	}

	@Override
	public void handUpdated(HandPosition pos) {
		if (ID == pos.getId()) {
			this.pos = pos.getPosition();
			doSquash();
		}
	}

	void doSquash(){
		theGame.squash((int)pos.getX(), (int)pos.getY());
	}

}
