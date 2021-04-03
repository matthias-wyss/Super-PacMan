package ch.epfl.cs107.play.game.superpacman;

import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class SuperPacman extends RPG {

	public final static float CAMERA_SCALE_FACTOR = 20.f;
	private SuperPacmanPlayer player;
	private RegionOfInterest roi;
	public ImageGraphics endScreenImage;
	
	private final String[] areas = {"superpacman/Level0", "superpacman/Level1", "superpacman/Level2"};
	private int areaIndex;
	@Override
	public void update(float deltaTime) {

		super.update(deltaTime);

		Vector anchor = player.getPosition();
		roi = new RegionOfInterest(0,0,150,150);
		
		if (win()) {  // affiche "YOU WIN" au dessus de Pacman si le joueur a gagné
			endScreenImage = new ImageGraphics(ResourcePath.getSprite("superpacman/EndScreen"),10,10,roi,anchor.add(-5,-3));
	    	endScreenImage.draw(getWindow());
	    }
		if (player.getHP() <= 0) {  // affiche "GAME OVER" au dessus de Pacman s'il n'a plus de vie
			endScreenImage = new ImageGraphics(ResourcePath.getSprite("superpacman/GameOverScreen"),10,10,roi,anchor.add(-5,-3));
	    	endScreenImage.draw(getWindow());
	    }

	}

	@Override
	public String getTitle() {
		return "Super Pac-Man";
	}
	
	private void createAreas(){

		addArea(new Level0());
		addArea(new Level1());
		addArea(new Level2());

	}
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {

		if (super.begin(window, fileSystem)) {
			createAreas();
			
			areaIndex = 0;
			this.setCurrentArea(areas[areaIndex], true);
			
			player = new SuperPacmanPlayer((SuperPacmanArea) this.getCurrentArea(), new DiscreteCoordinates(10,1));
			super.initPlayer(player);

			return true;
		}
		return false;
	}
	
	
	
	/** 
	 * Determines if Pacman has passed the last level's gate
	 * @return True if he has passed the last level's gate, False otherwise
	 */
	private boolean win() {
		List<DiscreteCoordinates> winPositions = Arrays.asList(new DiscreteCoordinates(14,1), new DiscreteCoordinates (15,1), new DiscreteCoordinates (14,0), new DiscreteCoordinates (15,0));

		if(areaIndex == 2 && winPositions.contains(player.getCurrentCells().get(0))) {
			return true;
		}
		return false;
	}
	
}
