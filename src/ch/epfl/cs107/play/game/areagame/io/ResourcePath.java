package ch.epfl.cs107.play.game.areagame.io;


public class ResourcePath {

	private static final String SPRITE = "res/images/sprites/";
	private static final String BEHAVIORS = "res/images/behaviors/";
	private static final String BACKGROUNDS = "res/images/backgrounds/";
	private static final String FOREGROUNDS = "res/images/foregrounds/";
	private static final String SOUNDS = "res/sounds/";
	private static final String STRINGS = "res/strings/";
	public static final String FONTS = "res/fonts/";

	private static final String IMAGE_EXTENSION = ".png";
	private static final String SOUNDS_EXTENSION = ".wav";
	private static final String STRINGS_EXTENSION = ".xml";

	public static String getSprite(String name){
		return SPRITE+name+IMAGE_EXTENSION;
	}

	public static String getBehaviors(String name){
		return BEHAVIORS+name+IMAGE_EXTENSION;
	}

	public static String getBackgrounds(String name){
		return BACKGROUNDS+name+IMAGE_EXTENSION;
	}

	public static String getForegrounds(String name){
		return FOREGROUNDS+name+IMAGE_EXTENSION;
	}

	public static String getSounds(String name){
		return SOUNDS+name+SOUNDS_EXTENSION;
	}

	public static String getStrings(String name){
		return STRINGS+name+STRINGS_EXTENSION;
	}


}
