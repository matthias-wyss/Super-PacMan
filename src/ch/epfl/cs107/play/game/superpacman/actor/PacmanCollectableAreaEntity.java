/*
 * Author: Thibault Czarniak
 * EPFL
 * Date: Dec 6, 2020
*/

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public abstract class PacmanCollectableAreaEntity extends CollectableAreaEntity {
	private int addedScore;

	
	/**
	 * Default PacmanCollectableAreaEntity Constructor
	 * @param area (Area), not null
	 * @param orientation (Orientation) : initial orientation, not null
	 * @param position (DiscreteCoordinates) : initial position, not null
	 * @param addedScore (int) : score added when eaten
	 */
	public PacmanCollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position, int addedScore) {

		super(area,orientation,position);
		this.addedScore = addedScore;

		
	}
	
	public int getAddedScore() {
		return addedScore;
	}
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}
	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		if(v instanceof SuperPacmanInteractionVisitor) {
			((SuperPacmanInteractionVisitor)v).interactWith(this);
			super.collect();
		}
		
		
	}
	
	abstract public void bip(Audio audio);
	
	@Override
	public abstract void draw(Canvas canvas);

}
