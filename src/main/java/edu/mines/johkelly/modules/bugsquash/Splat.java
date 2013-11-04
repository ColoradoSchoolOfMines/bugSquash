package edu.mines.johkelly.modules.bugsquash;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Splat {
    int x, y;
    PImage sprite;

    public Splat(int x, int y, PImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void draw(PApplet canvas) {
        canvas.imageMode(PConstants.CENTER);
        canvas.pushMatrix();
        canvas.translate(x, y);
        canvas.image(sprite, 0, 0);
        canvas.popMatrix();
        canvas.imageMode(PConstants.CORNER);
    }
}
