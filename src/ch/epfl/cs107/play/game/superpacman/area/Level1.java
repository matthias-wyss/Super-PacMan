package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level1 extends SuperPacmanArea {
	
	@Override
	public String getTitle() {
		return "superpacman/Level1";
	}
	
	@Override
	protected void createArea() {
		//Création manuelle des Gate
		this.registerActor(new Door("superpacman/Level2", super.getPlayerSpawnPoint("superpacman/Level2"), Logic.TRUE, this, Orientation.DOWN, new DiscreteCoordinates(14,0), new DiscreteCoordinates(15,0)));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14,3), ((SuperPacmanArea)this), this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15,3), ((SuperPacmanArea)this), this.getBehavior().getGraph()));
	}

}
