package ch.epfl.cs107.play.game.superpacman.actor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class SuperPacmanPlayer extends Player {

	private Orientation desiredOrientation;
	private static final int SPEED = 6;
	private Animation[] animations;
	private SuperPacmanPlayerHandler handlerPlayer;
	private int hp;
	private int score;
	private static final int HPMAX =5;
	private SuperPacmanPlayerStatusGUI playerGUI;
	private boolean ateBonus;
	private final static int RANDOM_MAX = 15;
	private SoundAcoustics pacmanDeathSound;
	private SoundAcoustics pacmanHardDeathSound;
	
	
	/**
	 * Default SuperPacmanPlayer Constructor
	 * @param area (SuperPacmanArea), not null
	 * @param coords (DiscreteCoordinates) : initial position, not null
	 */
	public SuperPacmanPlayer(SuperPacmanArea area, DiscreteCoordinates coords) {
		super(area, Orientation.RIGHT, ((SuperPacmanArea)area).getPlayerSpawnPoint(area.getTitle()));
		
		Sprite[][] sprites = RPGSprite.extractSprites("superpacman/pacman", 4, 1, 1, this, 64, 64,
                new Orientation[] {Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT});
        animations = Animation.createAnimations(SPEED / 2, sprites);
      

        this.hp = 3;
        this.score = 0;
		handlerPlayer = new SuperPacmanPlayerHandler();
		playerGUI = new SuperPacmanPlayerStatusGUI(this);
		
		pacmanDeathSound = new SoundAcoustics("res/sounds/pacmanDeath.wav");
		pacmanHardDeathSound = new SoundAcoustics("res/sounds/pacmanHardDeath.wav");
		
		
	}
	
	protected boolean getAteCherry() {
		return ateBonus;
	}
	
	public void setAteBonus(boolean value) {
		this.ateBonus = value;
	}
	
	public int getHP() { // public pour etre recuperer par SuperPacman pour gerer le game over
		return this.hp; 
	}
	
	protected int getScore() { // Here it seems reasonable that SuperPacmanPlayerStatusGUI can access the player's Score
		return this.score;
	}
	
	protected int getMaxHP() { //same reasoning as above
		return this.HPMAX; 
	}
	
	public void update(float deltaTime) {
		
		Keyboard keyboard = getOwnerArea().getKeyboard();
		moveOrientate(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
	    moveOrientate(Orientation.UP, keyboard.get(Keyboard.UP));
	    moveOrientate(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
	    moveOrientate(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
	    
	    animate(this.getOrientation(), deltaTime);
	   	
	    if(!this.isDisplacementOccurs() && desiredOrientation != null && this.getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(desiredOrientation.toVector())))){
	    	this.orientate(desiredOrientation);
	    	this.move(SPEED);
	    	
	    }
	 
	    super.update(deltaTime);
	   
	}
	
	private void animate(Orientation orientation, float deltaTime) {
		if(this.isDisplacementOccurs()) {
			animations[orientation.ordinal()].update(deltaTime);
		}
		else {
			animations[orientation.ordinal()].reset();
		}
	}
	
	private void moveOrientate(Orientation orientation, Button b){

        if(b.isDown()) {
        	desiredOrientation = orientation;
            if(getOrientation() == orientation) {
 
            	animations[orientation.ordinal()].update(SPEED);
            }
            else orientate(orientation);
        }
    } 
	
	public void bip(Audio audio) {
		pacmanDeathSound.bip(audio);
		pacmanHardDeathSound.bip(audio);
	}
	
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}
	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return null;
	}
	@Override
	public boolean wantsCellInteraction() {
		return true;
	}
	@Override
	public boolean wantsViewInteraction() {
		return false;
	}
	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handlerPlayer);
		
	}
	@Override
	public boolean takeCellSpace() {
		return false;
	}
	@Override
	public boolean isCellInteractable() {
		return false;
	}
	@Override
	public boolean isViewInteractable() {
		return true;
	}
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor)v).interactWith(this);
		
	}
	@Override
	public void draw(Canvas canvas) {
		
		playerGUI.draw(canvas);
		
		if(this.getOrientation() == Orientation.LEFT) {
	    	animations[Orientation.LEFT.ordinal()].draw(canvas);
		}
		if(this.getOrientation() == Orientation.UP) {
	    	animations[Orientation.UP.ordinal()].draw(canvas);
		}
		if(this.getOrientation() == Orientation.RIGHT) {
	    	animations[Orientation.RIGHT.ordinal()].draw(canvas);
		}
		if(this.getOrientation() == Orientation.DOWN) {
	    	animations[Orientation.DOWN.ordinal()].draw(canvas);
		}
		
	}
	
	
	public void pacmanIsEaten() {  // called on SuperPacmanPlayerHandler when interaction with Ghost
		hp -= 1;
		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		DiscreteCoordinates newPosition = SuperPacmanArea.getPlayerSpawnPoint(this.getOwnerArea().getTitle());
		this.setCurrentPosition(newPosition.toVector());
		this.getOwnerArea().enterAreaCells(this, getCurrentCells()); 
		resetMotion();
		((SuperPacmanArea)this.getOwnerArea()).resetGhosts();
		
		if (hp > 0) {  // gestion des sons
			pacmanDeathSound.shouldBeStarted();
		}
		else {
			pacmanHardDeathSound.shouldBeStarted();
		}
	}
	
	private class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {
		
	
		
		@Override
		public void interactWith(Door door) {
			SuperPacmanPlayer.this.setIsPassingADoor(door);
		
		}
		
		@Override
		public void interactWith(Ghost ghost) {
			
			if (ghost.isAfraid()) {
				ghost.ghostIsEaten();
				ghost.ghostIsEatenSound();
				score += ghost.getGhostScore();
			}
			else { 
				pacmanIsEaten();
			}
		}

		@Override
		public void interactWith(PacmanCollectableAreaEntity pacmanCollectableAE) {
			
			if(pacmanCollectableAE instanceof Bonus) {
				SuperPacmanPlayer.this.ateBonus = true;
				Ghost.startTimer(((Bonus)pacmanCollectableAE).getBonusTimer());
			} 

			if(pacmanCollectableAE instanceof Key) {
				((Key)pacmanCollectableAE).collect();
			}
			
			if(pacmanCollectableAE instanceof Diamond) {
				((SuperPacmanArea)SuperPacmanPlayer.this.getOwnerArea()).removeDiamond();
			}
			
			if(pacmanCollectableAE instanceof Cherry) {
				int randomInt = RandomGenerator.getInstance().nextInt(RANDOM_MAX);  // 1/5 luck to win 1 more life when eating a cherry
				if(randomInt == 2 || randomInt == 5 || randomInt == 7) {            // but 1/15 luck to lost a life
					hp += 1;
				}
				else if(randomInt == 14) {
					hp -= 1;
				}
			}
			

			SuperPacmanPlayer.this.score += pacmanCollectableAE.getAddedScore();
			
		}
	
	}

}


class SuperPacmanPlayerStatusGUI implements Graphics {
	
	private SuperPacmanPlayer player;
	
	/**
	 * Default SuperPacmanPlayerStatusGUI
	 * @param player (SuperPacmanPlayer): player associated to the GUI
	 */
	public SuperPacmanPlayerStatusGUI(SuperPacmanPlayer player) {
		this.player = player;

	}

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		drawHP(canvas, width, height);
		drawScore(canvas, width, height);
		
	}
	

	//Draws the HP count of Pacman
	private void drawHP(Canvas canvas, float width, float height) {
		
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, height/2));
		float n = 0;
		int m=0;
	
		for (int i = 0; i < this.player.getHP() ; ++i) {
			ImageGraphics life = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f, new RegionOfInterest(m,0,64,64), anchor.add(new Vector(n, height-1.375f)), 1, 1.f);
			life.draw(canvas);
			n +=1;
			
		}
		m=64;
		for(int i = this.player.getHP() ; i < this.player.getMaxHP(); ++i) {
			ImageGraphics life = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f, new RegionOfInterest(m,0,64,64), anchor.add(new Vector(n, height-1.375f)), 1, 1.f);
			life.draw(canvas);
			n+=1;
		}
		
	}
	//Draws Pacman's current score
	private void drawScore(Canvas canvas, float width, float height) {

		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2-6, (float)(-height/2+1.25))); 
		TextGraphics score = new TextGraphics(String.valueOf("SCORE: " + this.player.getScore()), 1, Color.YELLOW, Color.RED, 0.f, false, false, anchor);
		
		score.draw(canvas);
		
		
		
	}
	
	

}

