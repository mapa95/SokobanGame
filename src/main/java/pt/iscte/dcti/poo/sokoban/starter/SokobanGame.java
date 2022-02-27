package pt.iscte.dcti.poo.sokoban.starter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import abstracts.AbstractObject;
import abstracts.ObjectsFactory;
import interfaces.InteractiveObject;
import objects.Chao;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import scores.ScoreBoard;

public class SokobanGame implements Observer {

	private static SokobanGame INSTANCE;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private static final String GameName = "PinkySokoban by Mara Alves";
	private Player player;
	private ScoreBoard scoreBoard = new ScoreBoard();

	private int lvl = 0;
	private int maxLvl = 5;
	private static final int InitialScore = 0;
	private static final int NextLevel = 100;
	private String playerName;
	private int score;
	private int levelScore;
	private int energy;
	private boolean finish;

	private List<AbstractObject> objects = new ArrayList<AbstractObject>();

	private SokobanGame() {
		int n = JOptionPane.showConfirmDialog(null, "Welcome to the challenge!"
													+ "\n" + "In this game you will find several objects with different interactions, and you"
														+ "\n" + "will see that you have a lot of ways to make your deliveries..."
													+ "\n\n" + "Keys:"
													+ "\n" + "'H' - If you need help."
													+ "\n" + "'R' - If you need to restart the game and save your score (you will start over"
														+ "\n" + "on tutorial, and your score will be reseted)."
													+ "\n" + "'Q' - To quit and save your score."
													+ "\n\n" + "To move your player, you just need to press the arrow keys."
													+ "\n" + "If you close the game, you will lose your score. Press 'Q' to close and save."
													+ "\n" + "Your score will accumulate through the levels, unless you reset the game.",
												GameName, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		
		if (n == JOptionPane.CANCEL_OPTION)
			System.exit(0);
		
		playerName = JOptionPane.showInputDialog(null, "Insert your name here:", "Player Name", JOptionPane.PLAIN_MESSAGE);
		if (playerName.equals(""))
			throw new NullPointerException("Please insert a valid name.");
					
		createLevel();
		ImageMatrixGUI.getInstance().setName(GameName + " | Player: " + playerName);
		ImageMatrixGUI.getInstance().addImages(getImageTile());
		ImageMatrixGUI.getInstance().setStatusMessage("Press an arrow key to start the game.");
	}

	public static SokobanGame getInstance() {
		if (INSTANCE == null)
			INSTANCE = new SokobanGame();
		return INSTANCE;
	}

	@Override
	public void update(Observed arg0) {

		int lastKeyPressed = ((ImageMatrixGUI) arg0).keyPressed();
		if (Direction.isDirection(lastKeyPressed)) {
			Direction direction = Direction.directionFor(lastKeyPressed);
			if (player != null)
				if (energy > 0) {
					if (player.isMovable(direction))
						energy--;
					player.move(direction);
				}
		} else {
			optionsDialogue(lastKeyPressed);
		}
		if (lvl == 0)
			ImageMatrixGUI.getInstance().setStatusMessage("Tutorial  ||  Push the box until the target.");
		else
			ImageMatrixGUI.getInstance()
					.setStatusMessage("Level: " + lvl + " | Level Score: " + levelScore + " | Score: " + score 
							+ " | Energy: " + energy);
	
		
		
	}

	public void updateScore(InteractiveObject obj) {
		int influenceOnScore = obj.scoreInfluence();
		levelScore += influenceOnScore;
		score += influenceOnScore;
		energy += obj.energyInfluence();
	}

	
	/****************** LEVEL FUNCTIONS ******************/

	private void createLevel() {
	
		energy = 100;
		levelScore = InitialScore;
		if (lvl == 0) {
			score = InitialScore;
			System.out.println("--------- Tutorial ---------");
		} else {
			if (lvl == 1) {
				score = InitialScore;
				System.out.println("\n" + "Now, let's get real...");
			}
			System.out.println("\n" + "--------- Level " + lvl + " ---------" + "\n");
		}

		File level = new File("levels/level" + lvl + ".txt");
		ArrayList<AbstractObject> levelObjects = new ArrayList<AbstractObject>();

		try {
			Scanner scan = new Scanner(level);
			while (scan.hasNextLine())
				for (int y = 0; y < HEIGHT; y++) {
					String line = scan.nextLine();
					for (int x = 0; x < WIDTH; x++) {
						levelObjects.add(new Chao(new Point2D(x, y)));
						char obj = line.charAt(x);
						AbstractObject newObject = ObjectsFactory.createObject(obj, x, y);
						if (obj == 'E') {
							player = (Player) newObject;
							levelObjects.add(player);
						} else
							levelObjects.add(newObject);
					}
				}
			objects = levelObjects;
			scan.close();

		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Ups... We don't have any level with that number!");
		}
	}

	public void levelCompleted() {
		score += (NextLevel + energy);
		levelScore += (NextLevel + energy);
		if (lvl == maxLvl) {
			finish = true;
			ImageMatrixGUI.getInstance().setStatusMessage("Level: Final Level | Level Score: " + levelScore + " | Score: " + score 
					+ " | Energy: 0");
			scoreBoard.saveScores(lvl, finish);
			
			JOptionPane.showInternalMessageDialog(null, "Congratulations " + playerName + "!"
					+ "\n" + "You finished the game!"
					+ "\n\n" + "Your total score was: " + score + "!"
					+ "\n\n" + "Check if you're in our high scores:"
					+ scoreBoard.toString() + "\n\n" + "Good game! Until next time.",
					"Winner!", JOptionPane.WARNING_MESSAGE);
			
			System.out.println("\n\n" + "Congratulations! Good game!");
			System.exit(0);
		} else {
			if (lvl != 0) {
				ImageMatrixGUI.getInstance().setStatusMessage("Level: " + lvl + " | Level Score: " + levelScore + " | Score: " + score
						+ " | Energy: 0");				

				int n = JOptionPane.showConfirmDialog(null,  "Great job " + playerName + "!"
						+ "\n" + "Currente total score: " + score + "."
						+ "\n" + "In this level, your score was: " + levelScore + "."
						+ "\n\n" + "Check the previous best scores for this level:"
						+ scoreBoard.toString() + "\n\n" + "Do you wanna procceed onto the level " + (lvl+1) + "?",
						"Keep playing!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (n == JOptionPane.NO_OPTION) {
					finish = true;
					scoreBoard.saveScores(lvl, finish);
					System.exit(0);
				} else
					scoreBoard.saveScores(lvl, finish);
			}
			lvl++;
			newLevel();
		}
	}
	
	public void newLevel() {
		ImageMatrixGUI.getInstance().clearImages();
		objects.clear();
		createLevel();
		ImageMatrixGUI.getInstance().addImages(getImageTile());
		ImageMatrixGUI.getInstance().update();
	}

	public void losingTheGame() {
		finish = true;
		scoreBoard.saveScores(lvl, finish);
		ImageMatrixGUI.getInstance().removeImage(player);
		System.out.println("You lost the game." + "\n" + "Better luck next time!" + "\n\n");
	}

	public void removeAnObject(AbstractObject obj) {
		objects.remove(obj);
		ImageMatrixGUI.getInstance().removeImage(obj);
	}

	
	/******************* AUX FUNCTIONS *******************/

	private void optionsDialogue(int lastKeyPressed) {
		switch(lastKeyPressed) {
		case KeyEvent.VK_H:
			JOptionPane.showInternalMessageDialog(null, "Welcome to " + GameName + " instructions." + "\n\n" 
					+ "You have several objects in this game that will help you... or not. " + "\n" 
					+ "Some of them can influence your score and/or energy." + "\n" 
					+ "But you will have to find which ones for yourself." + "\n\n\n" 
					+ "Instructions for the different objects:" + "\n\n" 
					+ "- Boxes: you should take them to the targets." + "\n" 
					+ "- Targets: when all of them have a box on top of it, you will pass the level." + "\n" 
					+ "- Teleport: you can use it to go to the other teleport on the map." + "\n" 
					+ "- Stones: can be pushed." + "\n" 
					+ "- Hammer: allows you to break some special walls." + "\n" 
					+ "- Ice: it will get you to the other side of it." + "\n" 
					+ "- Fuel: it will give more energy (and a little bonus in the score)." + "\n" 
					+ "- Hole: you have to be careful with that... but maybe you can take some" + "\n" 
						+ "advantage of it?" + "\n\n" 
					+ "Key Instructions:" + "\n" 
						+ "'R' - Restart, save score and reset it in a new game." + "\n" 
						+ "'Q' - Quit and save scores.",
					"Instructions for " + GameName, JOptionPane.INFORMATION_MESSAGE);
			break;
		case KeyEvent.VK_R:
			JOptionPane.showInternalMessageDialog(null, "It seems that you gave up... better luck next time!" + "\n\n"
					+ "High Scores:" + scoreBoard.toString(), 
					"Going back to tutorial.", JOptionPane.WARNING_MESSAGE);
			losingTheGame();
			lvl = 1;
			score = 0;
			newLevel();
			break;
		case KeyEvent.VK_Q:
			finish = true;
			scoreBoard.saveScores(lvl, finish);
			JOptionPane.showInternalMessageDialog(null, "Have a nice day!" + "\n\n"
					+ "High Scores:" + scoreBoard.toString(), 
					"Exiting and saving your scores...", JOptionPane.WARNING_MESSAGE);
			ImageMatrixGUI.getInstance().dispose();
		}
	}
	
	/******************* GET FUNCTIONS *******************/

	public Player getPlayer() {
		return player;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getScore() {
		return score;
	}
	
	public int getLevelScore() {
		return levelScore;
	}

	public int getLevel() {
		return lvl;
	}

	public int getMaxLvl() {
		return maxLvl;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public List<ImageTile> getImageTile() {
		return Collections.unmodifiableList(objects);
	}

	public List<AbstractObject> getObjects() {
		return Collections.unmodifiableList(objects);
	}

	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> getListOfObjects(Predicate<AbstractObject> abs) {
		ArrayList<T> selectedObjects = new ArrayList<>();
		for (AbstractObject obj : objects)
			if (abs.test(obj))
				selectedObjects.add((T) obj);
		return selectedObjects;
	}

	@SuppressWarnings("unchecked")
	public <T> T getObjectFromType(Predicate<AbstractObject> abs) {
		for (AbstractObject obj : objects)
			if (abs.test(obj))
				return (T) obj;
		return null;
	}
}