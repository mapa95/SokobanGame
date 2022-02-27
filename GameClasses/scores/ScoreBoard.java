package scores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import pt.iscte.dcti.poo.sokoban.starter.SokobanGame;

public class ScoreBoard {

	private static final int TOP = 5;
	private static final int PRINTTOP = 3;

	public void saveScores(int level, boolean finish) {

		String name = SokobanGame.getInstance().getPlayerName();
		int score = SokobanGame.getInstance().getScore();
		int levelScore = SokobanGame.getInstance().getLevelScore();
		Score levelNewScore = new Score(name, levelScore, level);
		Score newScore = new Score(name, score);

		File levelBoard = new File("scores/Scores - level " + level + ".txt");
		File topScoresBoard = new File("scores/Score - Top" + TOP + ".txt");
		File highScoresBoard = new File("scores/HighScores" + ".txt");

		ArrayList<Score> levelScores = readFile(levelBoard);
		ArrayList<Score> topScores = readFile(topScoresBoard);
		ArrayList<Score> highScores = readFile(highScoresBoard);

		levelScores.add(levelNewScore);
		Collections.sort(levelScores);
		printScores(levelScores, levelBoard);

		if (finish) {
			topScores.add(newScore);
			highScores.add(newScore);
			Collections.sort(topScores);
			Collections.sort(highScores);
			printTopScores(topScores, topScoresBoard);
			printScores(highScores, highScoresBoard);
		}

		System.out.println("You're score was saved!");

	}


	/****************** PRINT FUNCTIONS ******************/

	private void printScores(ArrayList<Score> scores, File board) {
		try {
			PrintWriter printing = new PrintWriter(board);
			for (Score s : scores)
				printing.println(s);
			printing.close();
		} catch (FileNotFoundException e) {
			System.out.println("It seems that we're having some problems updating the Level Scores.");
		}
	}

	private void printTopScores(ArrayList<Score> bestScores, File highScores) {
		try {
			PrintWriter topScores = new PrintWriter(highScores);
			int counter = 0;
			while (counter < TOP && counter < bestScores.size()) {
				topScores.println(bestScores.get(counter));
				counter++;
			}
			topScores.close();
		} catch (FileNotFoundException e) {
			System.out.println("It seems that we're having some problems updating the High Scores.");
		}
	}


	/****************** SHOW FUNCTIONS *******************/

	@Override
	public String toString() {

		String scores = "";
		File scoreBoard;
		int level = SokobanGame.getInstance().getLevel();

		if (level == SokobanGame.getInstance().getMaxLvl())
			scoreBoard = new File("scores/Score - Top" + TOP + ".txt");
		else
			scoreBoard = new File("scores/Scores - level " + level + ".txt");

		ArrayList<Score> show = checkToShowTopScores(scoreBoard);

		for (Score s : show)
			scores += "\n" + s.toString();

		return scores;

	}

	public ArrayList<Score> checkToShowTopScores(File board){

		ArrayList<Score> scores = readFile(board);

		ArrayList<Score> printTop = new ArrayList<>();
		int counter = 0;

		while (counter < PRINTTOP && counter < scores.size()) {
			printTop.add(scores.get(counter));
			counter++;
		}

		return printTop;
	}


	/****************** READ FUNCTIONS *******************/

	private ArrayList<Score> readFile(File board) {

		ArrayList<Score> fileScores = new ArrayList<>();

		try {
			Scanner scan = new Scanner(board);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				fileScores.add(readScore(line));
			}
			scan.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Creating new score file...");
		}

		return fileScores;
	}

	private Score readScore(String line) {
		String name = (line.split(" : ")[0]);
		int score = Integer.parseInt((line.split(" : ")[1]));
		return new Score(name, score);
	}
}
