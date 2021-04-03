package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;

import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.And;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level2 extends SuperPacmanArea {
	
	@Override
	public String getTitle() {
		return "superpacman/Level2";
	}
	
	@Override
	protected void createArea() {
		//Création manuelle des clés et Gate
		Key key1 = new Key(this, Orientation.RIGHT, new DiscreteCoordinates(3,16));
		Key key2 = new Key(this, Orientation.RIGHT, new DiscreteCoordinates(26,16));
		Key key3 = new Key(this, Orientation.RIGHT, new DiscreteCoordinates(2,8));
		Key key4 = new Key(this, Orientation.RIGHT, new DiscreteCoordinates(27,8));
		

		this.registerActor(key1);
		this.registerActor(key2);
		this.registerActor(key3);
		this.registerActor(key4);
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8,14), key1, this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.DOWN, new DiscreteCoordinates(5,12), key1, this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8,10), key1, this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8,8), key1, this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21,14), key2, this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.DOWN, new DiscreteCoordinates(24,12), key2, this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21,10), key2, this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21,8), key2, this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(10,2), new And(key3,key4), this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(19,2), new And(key3,key4), this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(12,8), new And(key3,key4), this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(17,8), new And(key3,key4), this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14,3), ((SuperPacmanArea)this), this.getBehavior().getGraph()));
		this.registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15,3), ((SuperPacmanArea)this), this.getBehavior().getGraph()));

	}
	

}
