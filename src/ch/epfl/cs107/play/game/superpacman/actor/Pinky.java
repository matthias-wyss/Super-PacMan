/*
 * Author: Thibault Czarniak
 * EPFL
 * Date: Dec 14, 2020
*/

package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Pinky extends Inky {

	private Animation[] animations;

	private static final int MAX_RANDOM_ATTEMPT = 200;
	private static final int MIN_AFRAID_DISTANCE = 5;
	protected static int AFRAID_SPEED = 9;
	private int currentSpeed;
	
	
	/**
	 * Default Pinky Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position and refuge position, not null
	 * @param graph (AreaGraph): graph associated to the area, not null
	 */
	public Pinky(Area area, Orientation orientation, DiscreteCoordinates position, AreaGraph graph) {
		super(area, orientation, position, graph);

		this.currentSpeed = SPEED;
		Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.pinky", 2, 1, 1, this, 16, 16,
                new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});
		animations = Animation.createAnimations(currentSpeed / 2, sprites);
		
		
	}
	
	public void update (float deltaTime) {
		if(needsUpdating()) {
			if(isAfraid() && super.isTargetingPlayer()) {
					setFleePosition();
				} else {
					super.setNextPos(this.getOwnerArea().getWidth());
				}
			}
			super.update(deltaTime);
		}
	

	//Find a fleeing position for Pinky at a certain distance from the Player
	public void setFleePosition(){
		int x,y;
		int attempts =0;
		DiscreteCoordinates position = null;
		do {
		
			if(attempts >= MAX_RANDOM_ATTEMPT) {
				break;
			}
			else {
				x = RandomGenerator.getInstance().nextInt(this.getOwnerArea().getWidth());
				y = RandomGenerator.getInstance().nextInt(this.getOwnerArea().getHeight());
				position = new DiscreteCoordinates(x,y);
				++attempts;
			}
		} while(position == null && DiscreteCoordinates.distanceBetween(this.getCurrentMainCellCoordinates(), super.getPlayer().getCurrentCells().get(0)) <= MIN_AFRAID_DISTANCE);
		super.setTargetPos(position);
	}
	
	@Override
	void basicDraw(Canvas canvas) {
		
		animations[getOrientation().ordinal()].draw(canvas);
		
	}
	
	public void draw(Canvas canvas) {
		
		super.draw(canvas);
	}
	
	
	

	@Override
	protected void basicAnimate(float deltaTime) {  // animation when not afraid
		animations[getOrientation().ordinal()].update(deltaTime);
		
	}

}
