/*
 * Author: Thibault Czarniak
 * EPFL
 * Date: Dec 9, 2020
*/

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Gate extends AreaEntity{
	private Logic signal;
	private Sprite sprite;

	
	/**
	 * Default Gate Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position, not null
	 * @param signal (Logic) : signal associated to the Gate for its activation and deactivation, not null
	 * @param graph (AreaGraph), not null
	 */
	public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Logic signal, AreaGraph graph) {

		super(area, orientation, position);
		this.signal = signal;
		graph.setSignal(this.getCurrentMainCellCoordinates(),signal);
		if(orientation.equals(Orientation.DOWN) || orientation.equals(Orientation.UP)) {
			sprite = new Sprite("superpacman/gate", 1, 1, this, new RegionOfInterest(0,0,64,64));
		} else {
			sprite = new Sprite("superpacman/gate", 1, 1, this, new RegionOfInterest(0,64,64,64));
		}
		
	}
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {

		return !signal.isOn();



	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {

	}

	@Override
	public void draw(Canvas canvas) {
		
		if(signal.isOff()) {
			sprite.draw(canvas);

		} 
	

}
}
