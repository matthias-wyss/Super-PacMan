package ch.epfl.cs107.play.game.superpacman.actor;


import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public class Bonus extends PacmanCollectableAreaEntity {
	private Animation animation;
	private Sprite[] sprites;
	private final static int SPEED = 6;
	private final static float bonusTimer = 40.f; // 10s
	private SoundAcoustics sound;
	
	
	/**
	 * Default Bonus Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position, not null
	 */
	public Bonus(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, 0);
		this.sprites =RPGSprite.extractSprites("superpacman/coin", 4, 1, 1, this, 16, 16);
		animation = new Animation(SPEED/2, sprites, true);
		
		sound = new SoundAcoustics("res/sounds/pacmanEatingFruit.wav");
		
	}
	
	public float getBonusTimer() {
		return bonusTimer;
	}
	
	@Override
	public void update(float deltaTime) {
		animation.update(deltaTime);
	}

	@Override
	public void draw(Canvas canvas) {

		animation.draw(canvas);
		
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
