/*
 * Author: Thibault Czarniak
 * EPFL
 * Date: Nov 27, 2020
*/

package ch.epfl.cs107.play.game.superpacman.handler;

import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.PacmanCollectableAreaEntity;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;

public interface SuperPacmanInteractionVisitor extends RPGInteractionVisitor{
	default void interactWith(SuperPacmanPlayer superPacmanPlayer) {
		
	}

	default void interactWith(Ghost ghost) {
		
	}

	default void interactWith(PacmanCollectableAreaEntity pacmanCollectableAE) {
		
	}

}
