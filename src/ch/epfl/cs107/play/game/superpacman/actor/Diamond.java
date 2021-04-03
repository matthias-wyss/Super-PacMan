package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public class Diamond extends PacmanCollectableAreaEntity {
	private Sprite sprite;
	private SoundAcoustics sound;
	
	
	/**
	 * Default Diamond Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position, not null
	 */
	public Diamond(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, 10);
		this.sprite = new Sprite("superpacman/diamond", 1.f, 1.f,this);
		
		sound = new SoundAcoustics("res/sounds/pacmanEatingDiamond.wav");
		
	}
	public void draw (Canvas canvas) {
		sprite.draw(canvas);
	}
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		super.acceptInteraction(v);
		sound.shouldBeStarted();
	}
	
	public void bip(Audio audio) {
		sound.bip(audio);
	}
	

}
