package ch.epfl.cs107.play.game.superpacman.area;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.Blinky;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Inky;
import ch.epfl.cs107.play.game.superpacman.actor.Pinky;
import ch.epfl.cs107.play.game.superpacman.actor.Wall;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;


public class SuperPacmanBehavior extends AreaBehavior {
	private AreaGraph graph;
	
	public enum SuperPacmanCellType{
		//https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
		NONE(0),                    // never used as real content
		WALL(-16777216),            //black
		FREE_WITH_DIAMOND(-1),      //white
		FREE_WITH_BLINKY(-65536),   //red
		FREE_WITH_PINKY(-157237),   //pink
		FREE_WITH_INKY(-16724737),  //cyan
		FREE_WITH_CHERRY(-36752),   //light red
		FREE_WITH_BONUS(-16478723), //light blue
		FREE_EMPTY(-6118750);       // sort of gray

		final int type;
		

		SuperPacmanCellType(int type){
			this.type = type;
		}

		public static SuperPacmanCellType toType(int type){
			for(SuperPacmanCellType ict : SuperPacmanCellType.values()){
				if(ict.type == type)
					return ict;
			}
			System.out.println(type);
			return NONE;
		}
	}
	
	
	
	private boolean[][] getNeighborhood(int x, int y) {  // renvoie une matrice 3x3 de boolean : mur ou pas dans les cellules voisines
		
        boolean[][] neighborhood = new boolean[3][3];
        for(int i = -1; i <= 1 ; ++i) {
            for(int j = -1; j <= 1; ++j) {


                if(!(x + j < 0 || x + j > this.getWidth()-1 || y + i < 0 || y + i > this.getHeight()-1)) {
                    if(((SuperPacmanCell)this.getCell(x+j, y+i)).type == SuperPacmanCellType.WALL) {
                        neighborhood[j+1][-i+1] = true;
                    }

                }
                else {
                	neighborhood[j+1][-i+1] = false;
                }
            }
            }
         return neighborhood;   
        }

            
	
	
	
	
	protected void registerActors(Area area) {  // enregistre les acteurs en fonction du type (enum) des cellules
		int height = this.getHeight();
		int width = this.getWidth(); 
		
		for(int x = 0; x < width ; ++x) {
			for(int y = 0; y < height ; ++y) {
			
				SuperPacmanCell currentCell = (SuperPacmanCell)this.getCell(x,y);
				if(currentCell.type == SuperPacmanCellType.WALL) {
			
					boolean[][] neighborhood = getNeighborhood(x,y);
					Wall wall = new Wall(area, new DiscreteCoordinates(x,y), neighborhood);
    				area.registerActor(wall);		
				      
						
				}
				else {
					
					
						//Crée le AreaGraph
						boolean hasLeftEdge = (x > 0 && ((SuperPacmanCell)this.getCell(x-1,y)).type != SuperPacmanCellType.WALL);
						boolean hasRightEdge = (x < width && ((SuperPacmanCell)this.getCell(x+1,y)).type != SuperPacmanCellType.WALL);
						boolean hasDownEdge = (y > 0 && ((SuperPacmanCell)this.getCell(x,y-1)).type != SuperPacmanCellType.WALL);
						boolean hasUpEdge = (y < height-1 && ((SuperPacmanCell)this.getCell(x,y+1)).type != SuperPacmanCellType.WALL);
						graph.addNode(new DiscreteCoordinates(x,y), hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);
					
					
					//Vérifie chaque type de cellule possible
					if(currentCell.type == SuperPacmanCellType.FREE_WITH_BONUS) {
						
						Bonus bonus = new Bonus(area, Orientation.DOWN, new DiscreteCoordinates(x,y));
						area.registerActor(bonus);
						
					} if (currentCell.type == SuperPacmanCellType.FREE_WITH_CHERRY) {
						Cherry cherry = new Cherry(area, Orientation.DOWN, new DiscreteCoordinates(x,y));
						area.registerActor(cherry);
						
					} if(currentCell.type == SuperPacmanCellType.FREE_WITH_DIAMOND){ 
						Diamond diamond = new Diamond(area, Orientation.DOWN, new DiscreteCoordinates(x,y));
						area.registerActor(diamond);
						((SuperPacmanArea)area).addDiamond();
						
					}
					if (currentCell.type == SuperPacmanCellType.FREE_WITH_BLINKY) {
						Blinky blinky = new Blinky(area, Orientation.DOWN, new DiscreteCoordinates(x,y));
						area.registerActor(blinky);
						((SuperPacmanArea)area).addGhost(blinky);

					}
					if (currentCell.type == SuperPacmanCellType.FREE_WITH_INKY) {
						Inky inky = new Inky(area, Orientation.DOWN, new DiscreteCoordinates(x,y), graph);
						area.registerActor(inky);
						((SuperPacmanArea)area).addGhost(inky);
					}
					if(currentCell.type == SuperPacmanCellType.FREE_WITH_PINKY) {
						Pinky pinky = new Pinky(area, Orientation.DOWN, new DiscreteCoordinates(x,y), graph);
						area.registerActor(pinky);
						((SuperPacmanArea)area).addGhost(pinky);
					}
				}
		
			}
		}
	}




	
	

	/**
	 * Default SuperPacmanBehavior Constructor
	 * @param window (Window), not null
	 * @param name (String): Name of the Behavior, not null
	 */
	public SuperPacmanBehavior(Window window, String name){
		super(window, name);
		int height = getHeight();
		int width = getWidth();
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width ; x++) {
				SuperPacmanCellType color = SuperPacmanCellType.toType(getRGB(height-1-y, x));
				setCell(x,y, new SuperPacmanCell(x,y,color));
			}
		}
		graph = new AreaGraph();
	}
	
	
	public AreaGraph getGraph() {
		return graph;
	}
	
	/**
	 * Cell adapted to the SuperPacman game
	 */
	public class SuperPacmanCell extends AreaBehavior.Cell {
		/// Type of the cell following the enum
		public final SuperPacmanCellType type;
		
		/**
		 * Default SuperPacmanCell Constructor
		 * @param x (int): x coordinate of the cell
		 * @param y (int): y coordinate of the cell
		 * @param type (SuperPacmanCellType), not null
		 */
		public  SuperPacmanCell(int x, int y, SuperPacmanCellType type){
			super(x, y);
			this.type = type;
		}
	
		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			return !this.hasNonTraversableContent();
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
			
		}

	}

}
