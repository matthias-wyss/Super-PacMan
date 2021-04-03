package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;

import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public class Inky extends Ghost {
	
	private Animation[] animations;
	private static final int MAX_DISTANCE_WHEN_SCARED = 5;
	private static final int MAX_DISTANCE_WHEN_NOT_SCARED = 10;
	protected static int AFRAID_SPEED = 9;
	private int currentSpeed;
	private AreaGraph graph;
	private DiscreteCoordinates targetPos;
	private boolean isTargetingPlayer;
	private SuperPacmanPlayer player;
	private SoundAcoustics sirenSound;
	private boolean sirenSoundIsPlayed;


	/**
	 * Default Inky Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position and refuge position, not null
	 * @param graph (AreaGraph): graph associated to the area, not null
	 */
	public Inky (Area area, Orientation orientation, DiscreteCoordinates position, AreaGraph graph) {
		super(area, orientation, position);
		
		this.currentSpeed = SPEED;
		Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.inky", 2, 1, 1, this, 16, 16,
	         new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});
		animations = Animation.createAnimations(currentSpeed / 2, sprites);
		
		this.graph = graph;
		
		sirenSound = new SoundAcoustics("res/sounds/pacmanSiren.wav");
		sirenSoundIsPlayed = false;
		
	
		
	}
	
	protected boolean isTargetingPlayer() {
		return isTargetingPlayer;
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	
	

	@Override
	protected void basicAnimate(float deltaTime) {  // animation when not afraid
		animations[getOrientation().ordinal()].update(deltaTime);
		
	}
	
	/**
	 * Determines if Inky needs to change its target position
	 * @return boolean True if Inky needs to change its target position, false otherwise
	 */
	public boolean needsUpdating() {
		if(targetPos == null) {
			return true;
		}
		if(this.getCurrentCells().get(0).equals(targetPos)) {
			return true;
		}
		if(super.getTimerValue() >= 19 || super.getTimerValue() ==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		if(v instanceof SuperPacmanPlayer) {
			if(isAfraid()) {
				setNextPos(MAX_DISTANCE_WHEN_SCARED);
			} else {
				setNextPos(MAX_DISTANCE_WHEN_NOT_SCARED);
			}
			
		}
		super.acceptInteraction(v);
		
	}
	
	@Override
	public void interactWith(Interactable other) {
		if(other instanceof SuperPacmanPlayer) {
			if(!isAfraid()) {
				this.targetPos = ((SuperPacmanPlayer)other).getCurrentCells().get(0);
				this.isTargetingPlayer = true;
				this.player = ((SuperPacmanPlayer)other);
			}
		}
		super.interactWith(other);
		if (isTargetingPlayer() && !sirenSoundIsPlayed) {
			sirenSound.shouldBeStarted();
			sirenSoundIsPlayed = true;
		}
		if (isAfraid()) {
			sirenSoundIsPlayed = false;
		}
		
	}

	
	public void bip(Audio audio) {
		sirenSound.bip(audio);
	}
	
	protected SuperPacmanPlayer getPlayer() {
		return player;
	}
	
	/**
	 * Set the target position for Inky
	 * @param pos (DisreteCoordinates): position to be set 
	 */
	protected void setTargetPos(DiscreteCoordinates pos) {
		this.targetPos = pos;
		
	}
	
	@Override
	public void update(float deltaTime) {
		if(needsUpdating() && this instanceof Inky) { //Ici traitement propre a Inky
			this.isTargetingPlayer = false;
			this.player = null;
			if(isAfraid()) {
				setNextPos(MAX_DISTANCE_WHEN_SCARED);
			} else {
				setNextPos(MAX_DISTANCE_WHEN_NOT_SCARED);
			}
		
		}
		if(!this.isDisplacementOccurs()) {
			this.orientate(getNextOrientation());
			this.move(currentSpeed);
		}
		
		if(isAfraid()) {
			this.currentSpeed = AFRAID_SPEED;
		} else {
			this.currentSpeed = SPEED;
		}
		
		super.update(deltaTime);
	}
	
	
	
	
	
	
	@Override
	/** 
	 * Determine the next orientation for Inky's path
	 * @return the next orientation on the path
	 */
	protected Orientation getNextOrientation() {
		Queue<Orientation> path = null;
		int counter = 0;
		do {
			if(counter >=1) {
				if(isAfraid()) {
					setNextPos(MAX_DISTANCE_WHEN_SCARED);
				} else {
					setNextPos(MAX_DISTANCE_WHEN_NOT_SCARED);
				}
				counter = 0;
			}
			path = graph.shortestPath(getCurrentMainCellCoordinates(), targetPos);
			++counter;
			
			
		} while (path == null);
		return path.poll();
		


	}
	
	
	public void setNextPos(int radius) {
		
		DiscreteCoordinates position;
		int x,y;
		
		do {
			x = RandomGenerator.getInstance().nextInt(this.getOwnerArea().getWidth());
			y = RandomGenerator.getInstance().nextInt(this.getOwnerArea().getHeight());
			position = new DiscreteCoordinates(x,y);
			
		} while (DiscreteCoordinates.distanceBetween(getRefuge(), position) > radius && position == null && graph.shortestPath(getCurrentMainCellCoordinates(), position) == null);
	
			
		this.targetPos = position;	
		}
		
	
	
	

	@Override
	void basicDraw(Canvas canvas) {
		
		animations[getOrientation().ordinal()].draw(canvas);
		
	}

}