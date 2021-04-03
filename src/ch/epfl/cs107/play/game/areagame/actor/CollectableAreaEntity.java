package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class CollectableAreaEntity extends AreaEntity {
	
	
	/**
	 * Default PacmanCollectableAreaEntity Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position, not null
	 */
	public CollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
	}
	
	//Collection method which simply unregisters the actor
	public void collect() {
		this.getOwnerArea().unregisterActor(this);
		
	}

}
