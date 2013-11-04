package edu.mines.johkelly.modules.bugsquash;

import edu.mines.acmX.exhibit.input_services.hardware.BadFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.HardwareManager;
import edu.mines.acmX.exhibit.input_services.hardware.HardwareManagerManifestException;
import edu.mines.acmX.exhibit.input_services.hardware.UnknownDriverRequest;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.GestureTrackerInterface;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.HandTrackerInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.module_management.modules.ProcessingModule;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.GestureReceiver;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.HandReceiver;
import processing.core.PImage;

import java.util.*;

public class BugSquashModule extends ProcessingModule {
    private static final int extMargin = 300;
    private static final int expFPS = 30;

    // private Logger log = LogManager.getLogger(BugSquashModule.class);

    private List<Bug> bugs = new ArrayList<Bug>();
    private final List<Splat> splats = new ArrayList<Splat>();
    private PImage bugSprite;

    private int framesSinceLastBug = 0;
	private GestureReceiver receiver;
	private SquashHandRecevier handRecv;
	HandTrackerInterface handDriver;
	GestureTrackerInterface gestDriver;

    @Override
    public void setup() {
        size(getWidth(), getHeight());
        bugSprite = loadImage("bug.png");
        frameRate(expFPS);

	    HardwareManager hm;
	    try {
		    hm = HardwareManager.getInstance();
		    receiver = new SquashReciever(this);
		    handRecv = new SquashHandRecevier(this);

		    gestDriver = (GestureTrackerInterface) hm.getInitialDriver("gesturetracking");
		    gestDriver.registerGestureRecognized(receiver);

//		    handDriver = (HandTrackerInterface) hm.getInitialDriver("handtracking");
//		    handDriver.registerHandCreated(handRecv);
//		    handDriver.registerHandUpdated(handRecv);
//		    handDriver.registerHandDestroyed(handRecv);

	    } catch (HardwareManagerManifestException e){
		    e.printStackTrace();
	    }   catch (BadFunctionalityRequestException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
	    } catch (UnknownDriverRequest e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
	    } catch (InvalidConfigurationFileException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
	    }
    }

    public void update() {

	    //handDriver.updateDriver();
	    gestDriver.updateDriver();

        framesSinceLastBug++;
        // Spawn one bug every second
        if (framesSinceLastBug % (expFPS) == 0) {
            spawnBug();
            framesSinceLastBug = 0;
        }
        // Update each bug's position and remove it if it is too far offscreen
        for (Iterator<Bug> iter = bugs.iterator(); iter.hasNext(); /**/) {
            Bug b = iter.next();
            b.update();
            if (!inBounds(b)) {
                iter.remove();
            }
        }
    }

    @Override
    public void draw() {
        background(color(256, 256, 256));
        update();
        for (Bug b : bugs) {
            b.draw(this);
        }
        synchronized (splats) {
            for (Splat s : splats) {
                s.draw(this);
            }
        }
        fill(color(0));
    }

    private boolean inBounds(Bug b) {
        return b.x > 0 - extMargin &&
                b.y > 0 - extMargin &&
                b.x < width + extMargin &&
                b.y < height + extMargin;
    }

    private void spawnBug() {
        int x = (int) (random(1) * (width));
        int y = (int) (random(1) * (height));
        float heading = (float) (random(1) * Math.PI / 2);
        // random boundaries are irrelevant -- just a convenient way to do 50/50 chance
        // On the vertical rails
        if (random(1) > .5) {
            // On the right
            if (random(1) > .5) {
                float rot = (float) (heading - 3 * Math.PI / 4);
                bugs.add(new Bug(width + extMargin, y, rot, bugSprite));
            }
            // On the left
            else {
                float rot = (float) (heading + Math.PI / 4);
                bugs.add(new Bug(-extMargin, y, rot, bugSprite));
            }
        }
        // On the horizontal rails
        else {
            // On the bottom
            if (random(1) > .5) {
                float rot = (float) (heading - Math.PI / 4);
                bugs.add(new Bug(x, height + extMargin, rot, bugSprite));
            }
            // On the top
            else {
                float rot = (float) (heading + 3 * Math.PI / 4);
                bugs.add(new Bug(x, -extMargin, rot, bugSprite));
            }
        }
    }

    public void squash(int x, int y) {
        // Test all bugs for collision
        for (Iterator<Bug> iter = bugs.iterator(); iter.hasNext(); /**/) {
            Bug b = iter.next();
            // distance between squash point and bug position
            double dist = Math.sqrt(Math.pow(b.x - x, 2) + Math.pow(b.y - y, 2));
            // The height is the larger graphic dimension, so players can "fat finger" a bit off target
            if (dist < bugSprite.height / 2) {
                iter.remove();
                // Spawn a new splat to display
                String splatGraphicName = "splat" + (int) (random(3) + 1) + ".png";
                final Splat s = new Splat(b.x, b.y, loadImage(splatGraphicName));
                splats.add(s);
                // Schedule synchronized deletion of the graphic 2 seconds later
                new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                synchronized (splats) {
                                    splats.remove(s);
                                }
                            }
                        },
                        2000
                );
            }
        }
    }
}
