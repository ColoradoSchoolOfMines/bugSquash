package edu.mines.johkelly.modules.bugsquash;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * Created with IntelliJ IDEA.
 * User: kinectteam
 * Date: 11/3/13
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bug {
    private int x, y;
    private float rotRadians;
    private PImage sprite;

    private static final int SPEED = 10;

    public Bug(int x, int y, float rotRadians){
        this.x = x;
        this.y = y;
        this.rotRadians = rotRadians;
    }

    public void update(){
        x += SPEED * Math.cos(rotRadians);
        y += SPEED * Math.sin(rotRadians);
    }

    public void draw(PApplet canvas){
        canvas.imageMode(PConstants.CENTER);
        canvas.translate(x/2, y/2);
        canvas.rotate(rotRadians);
        canvas.image(sprite, 0, 0);
        canvas.rotate(-rotRadians);
        canvas.translate(-x/2, -y/2);
        canvas.imageMode(PConstants.CORNER);
    }
}
