package edu.mines.johkelly.modules.bugsquash;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Bug {
	public int x, y;
	private float rotRadians;
	private PImage sprite;

	private static final int SPEED = 10;

	public Bug(int x, int y, float rotRadians, PImage sprite) {
		this.x = x;
		this.y = y;
		this.rotRadians = rotRadians;
		this.sprite = sprite;
	}

	public void update() {
		// 0 radians in Processing coordinates points up, not right
		x += SPEED * Math.sin(rotRadians);
		y -= SPEED * Math.cos(rotRadians);
	}

	public void draw(PApplet canvas) {
		canvas.imageMode(PConstants.CENTER);
		canvas.pushMatrix();
		canvas.translate(x, y);
		canvas.rotate(rotRadians);
		canvas.image(sprite, 0, 0);
		canvas.popMatrix();
		canvas.imageMode(PConstants.CORNER);
	}
}
