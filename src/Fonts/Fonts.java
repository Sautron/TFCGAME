//Class that allows for custom fonts than the default to be use
//Classe que permite o uso de tipos de letra que não sejam as padrão

package Fonts;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

import GameState.GameStateManager;

public class Fonts {

	private static ArrayList<Fonts> fontList = new ArrayList<Fonts>();
	
	private static String fontPath;
	
	public Fonts(String filePath){
		Fonts.fontPath = GameStateManager.FONT_LOCATION + filePath;
		registerFont();
	}
	
	private void registerFont(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try{
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
		}catch(Exception e){
			
		}
	}
	
	public static void addFont(Fonts font){
		fontList.add(font);
	}
}

