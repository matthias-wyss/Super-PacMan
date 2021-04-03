/*
 * Author: Thibault Czarniak
 * EPFL
 * Date: Dec 9, 2020
*/

package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;


public class Key extends PacmanCollectableAreaEntity implements Logic {

	private boolean isCollected;
	private Sprite sprite;
	private SoundAcoustics sound;
	
	
	/**
	 * Default Key Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position, not null
	 */
	public Key(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, 0);
		this.sprite = new Sprite("superpacman/key", 1.f, 1.f,this);
		isCollected = false;
		
		sound = new SoundAcoustics("res/sounds/pacmanEatingKey.wav");
		
	}
	public void draw (Canvas canvas) {

		if(!this.isCollected) {
			sprite.draw(canvas);
		}
		
	}
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		super.acceptInteraction(v);
		sound.shouldBeStarted();
	}
	
	@Override
	public void collect(){
		this.isCollected = true;
		
		
	}
	
	@Override
	public boolean isOn() {
		return isCollected;
	}
	@Override
	public boolean isOff() {
		return !isCollected;
	}
	@Override
	public float getIntensity() {
		if(isCollected) {
			return 1.f;
		} else {
			return 0.f;
		}

	}
	
	public void bip(Audio audio) {
		sound.bip(audio);
	}
	
}



	
