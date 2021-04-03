package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Blinky extends Ghost {
	
	private static final int MAX = 4;
	private Animation[] animations;

	
	/**
	 * Default Ghost Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position and refuge position, not null
	 */
	public Blinky (Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		
		Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1, this, 16, 16,
                new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});
		animations = Animation.createAnimations(SPEED / 2, sprites);
		
	}
	

	@Override
	void basicAnimate(float deltaTime) {  // animation when not afraid
		animations[getOrientation().ordinal()].update(deltaTime);
		
	}
	
	public void update(float deltaTime) {
		if(!this.isDisplacementOccurs() && this.getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(getNextOrientation().toVector())))){
	    	Orientation orientation = getNextOrientation();
	    	if (orientation != null) {
	    		this.orientate(orientation);
		    	this.move(SPEED);
	    	}
	    }
		super.update(deltaTime);
	}

	@Override
	Orientation getNextOrientation() {  // determine une orientation au hasard

		int randomInt = RandomGenerator.getInstance().nextInt(MAX);
		Orientation nextOrientation = Orientation.fromInt(randomInt);
		
		return nextOrientation;

	}



	@Override
	void basicDraw(Canvas canvas) {
		
		animations[getOrientation().ordinal()].draw(canvas);
		
	}

}
