package scores;

public class Score implements Comparable<Score>{

	private String playerName;
	private int score;
	private int level;
	
	public Score(String playerName, int score) {
		this.playerName = playerName;
		this.score = score;
	}

	public Score(String playerName, int score, int level) {
		this.playerName = playerName;
		this.score = score;
		this.level = level;
	}
	
	public String getName() {
		return playerName;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	
	@Override
	public int compareTo(Score scoreData) {		
		return scoreData.getScore() - getScore();
	}

	@Override
	public String toString() {
		return getName() + " : " + getScore();
	}
	
}
