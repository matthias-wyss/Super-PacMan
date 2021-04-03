package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level0 extends SuperPacmanArea {

	@Override
	public String getTitle() {
		return "superpacman/Level0";
	}
	
	@Override
	protected void createArea() {
		//Création manuelle de la clé et des Gates
		this.registerActor(new Door("superpacman/Level1", super.getPlayerSpawnPoint("superpacman/Level1"), Logic.TRUE, this, Orientation.DOWN, new DiscreteCoordinates(5,9), new DiscreteCoordinates(6,9)));
		Key key =  new Key(this, Orientation.RIGHT, new DiscreteCoordinates(3,4));
		this.registerActor(key);
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(5,8), key, this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.LEFT, new DiscreteCoordinates(6,8), key, this.getBehavior().getGraph()));
	
		
	}
	


}
