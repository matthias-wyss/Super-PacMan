package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Ghost extends MovableAreaEntity implements Interactor {

	private DiscreteCoordinates refuge;
	private final static int GHOST_SCORE = 500;
	private final static int rayon = 5;
	private GhostHandler handler;
	private boolean isCopiePacmanNull = false;
	protected final static int SPEED = 18;     // protected to be able to use on specific ghost for animation
	protected static int AFRAID_SPEED = SPEED; // protected to be modifiable on specific ghost
	private Animation animationAfraid;
	private static float timer;
	private SoundAcoustics GhostIsEatenSound;
	
	
	/**
	 * Default Ghost Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position and refuge position, not null
	 */
	public Ghost(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		refuge = position;
		
		Sprite[] spritesAfraid = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1, this, 16, 16);
		animationAfraid = new Animation(AFRAID_SPEED / 2, spritesAfraid);
		
		handler = new GhostHandler();
		
		GhostIsEatenSound = new SoundAcoustics("res/sounds/pacmanEatingGhost.wav");

	}
	
	protected float getTimerValue() {
		return timer;
	}
	
	public void update(float deltaTime) {
		
		if(this.isDisplacementOccurs()) {
			animate(this.getOrientation(), deltaTime);
		}

		timer -= deltaTime;
			
		super.update(deltaTime);
	}
	
	private void animate(Orientation orientation, float deltaTime) {
	
		if (isAfraid()) {
			animationAfraid.update(deltaTime);
		}
		else {
			basicAnimate(deltaTime);
		}
	}
	
	abstract void basicAnimate(float deltaTime);  // to define on specific ghost
	
	abstract Orientation getNextOrientation();  // to define on specific ghost
	
	public int getGhostScore () {
		return GHOST_SCORE;
	}
	
	public boolean isAfraid() {
		if(timer > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public DiscreteCoordinates getRefuge() {
		return refuge;
	}
	
	public static void startTimer(float timerValue) {  // called on SuperPacmanPlayerHandler when interaction with Bonus
		timer = timerValue;
	}
	
	public void ghostIsEaten() {  // called on SuperPacmanPlayerHandler when interaction with SuperPacman
		
		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		this.setCurrentPosition(refuge.toVector());
		this.getOwnerArea().enterAreaCells(this, getCurrentCells());
		resetMotion();
		isCopiePacmanNull = true;
		
	}
	
	public void ghostIsEatenSound() {  // called on SuperPacmanPlayerHandler when interaction with SuperPacman
		GhostIsEatenSound.shouldBeStarted();
	}

	public void bip(Audio audio) {
		GhostIsEatenSound.bip(audio);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		
		return Collections.singletonList(getCurrentMainCellCoordinates());
		
	}

	@Override
	public boolean takeCellSpace() {

		return false;
	}

	@Override
	public boolean isCellInteractable() {

		return true;
	}

	@Override
	public boolean isViewInteractable() {

		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor)v).interactWith(this);
		
	}

	@Override
	public void draw(Canvas canvas) {
		if (isAfraid()) {
			animationAfraid.draw(canvas);
		}
		else {
			basicDraw(canvas);
		}
	}
	
	abstract void basicDraw(Canvas canvas);  // to define on specific ghost
	
	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		
		DiscreteCoordinates ghostPosition = getCurrentCells().get(0);
		
		List<DiscreteCoordinates> fieldOfViewCells = new ArrayList<DiscreteCoordinates>();
		
		for (int i = -rayon; i <= rayon; ++i) {
			for (int j = -rayon; j <= rayon; ++j) {
				
				fieldOfViewCells.add(new DiscreteCoordinates (ghostPosition.x+i,ghostPosition.y+j));
				
			}
		}
		
		return fieldOfViewCells;
	}


	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
		
	}
	
	
	protected boolean checkPacMan(SuperPacmanPlayer player) {
		return handler.isPacManSeen(player);
	}
	
	
	private class GhostHandler implements SuperPacmanInteractionVisitor {
		
		public boolean isPacManSeen (SuperPacmanPlayer superPacmanPlayer) {  // check if Pacman is in the field of view of the ghost
			DiscreteCoordinates pacmanPosition = superPacmanPlayer.getCurrentCells().get(0);
			
		for (int i = 0; i < getFieldOfViewCells().size(); ++i) {
			if (getFieldOfViewCells().get(i).equals(pacmanPosition)) {
				
				return true;
			}
		}
		return false;
		}
		
		@Override
		public void interactWith (SuperPacmanPlayer superPacmanPlayer) {
			if (isPacManSeen(superPacmanPlayer)) {
				SuperPacmanPlayer copiePacman = superPacmanPlayer;
				if (isCopiePacmanNull) {
					copiePacman = null;
				}
			}

		}
		
	}
	 
}
