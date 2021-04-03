package ch.epfl.cs107.play.game.superpacman.area;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Window;


public abstract class SuperPacmanArea extends Area implements Logic {


	protected int diamondCount;
	private SuperPacmanBehavior behavior;
	private List<Ghost> GhostList;
	
	public SuperPacmanBehavior getBehavior() {
		return behavior;
	}
	

	
	
	public boolean isOn() {
		return this.diamondCount == 0;
		
	}
	public boolean isOff() {
		return !(this.diamondCount ==0);
	}
	
	public float getIntensity() {
		return getIntensity(0.f);
		
	}
	
	public void addDiamond() {
		++diamondCount;
	}
	public void removeDiamond() {
		--diamondCount;
	}
	
	public void addGhost(Ghost ghost) {
		GhostList.add(ghost);
	}
	
	public void resetGhosts() {
		for(Ghost ghost : GhostList) {
			ghost.ghostIsEaten();
		}
	}

	/**
	 * Finds the player's spawn point depending on the area
	 * @param areaName (String): associated area's name not null,
	 */
	public static DiscreteCoordinates getPlayerSpawnPoint(String areaName) {
		if(areaName.equals("superpacman/Level0")) {
			return new DiscreteCoordinates(10,1);
		}
		if(areaName.equals("superpacman/Level1")) {
			return new DiscreteCoordinates(15,6);
		}
		if(areaName.equals("superpacman/Level2")) {
			return new DiscreteCoordinates(15,29);
		}
		else {
			return new DiscreteCoordinates(0,0); //if it doesn't correspond to any area return origin
		}
	}
	
	protected abstract void createArea();

    @Override
    /** 
     * Returns the camera scale factor of SuperPacmanAreas
     */
    public final float getCameraScaleFactor() {
        return SuperPacman.CAMERA_SCALE_FACTOR;
    }
  
    public void update(float deltaTime) {
    	super.update(deltaTime);
    	
    }
    
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
        	GhostList = new ArrayList<Ghost>();
        	this.diamondCount = 0;
        	behavior = new SuperPacmanBehavior(window, getTitle());
            setBehavior(behavior);
            behavior.registerActors(this);
            createArea();
            
            return true;
        }
        return false;
    }
    

}
