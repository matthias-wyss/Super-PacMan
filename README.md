# Super-PacMan


Super-PacMan is a game written in Java that was created as part of a mini-project 2 during my first semester of my first year at EPFL.

In this game you embody PacMan, a hungry little yellow ball that is stuck in a maze with ghosts.


### Installation

* Install Java 11 or higher
* Download Super-PacMan.jar

### The game

1. The goal of the game is to get out of the labyrinths of the different levels of the game without getting eaten by the ghosts. To move from one level to the next, you must collect a key and / or collect all the diamonds, in order to open the door to the next level. The game has 3 levels.

2. To control Pacman you can use the arrows on your keyboard. Pacman can only move in spaces that do not contain walls. Pacman will stop if he meets a wall in front of him. To stop Pacman while moving, you can press an arrow that will direct Pacman towards a wall.

3. Your score is displayed at the top left of your screen. To increase it you can eat diamonds, fruits, coins, or even ghosts when they are scared.

4. Ghosts are present from the second level. There are 3 types: 
	* Blinky in red, which choose orientations at random.
	* Inky in blue, who stalk you when you get too close to them, and otherwise stay close to their refuge. 
	* Pinky in pink, who stalk you when you get a little too close, and don't hesitate to stray from their refuge. They are also able to run away from you when they are afraid.

5. Pay attention ! When you come across a ghost in your path, it will eat you, causing you to lose a life and respawn in your spawn position. Ghosts temporarily go into a frightened state when you eat a coin, allowing you to eat them. When you eat a ghost, you increase your score and it will respawn in its safe position.

### Authors

* **Matthias Wyss**
* **Thibault Czarniak**

### License

This project is under MIT license - see [LICENSE.md](https://github.com/matthias-wyss/Super-PacMan/blob/main/LICENSE.md) for more informations