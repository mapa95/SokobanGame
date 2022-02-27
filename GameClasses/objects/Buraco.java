package objects;

import abstracts.AbstractObject;
import interfaces.InteractiveObject;
import interfaces.IsMovable;
import pt.iscte.dcti.poo.sokoban.starter.Player;
import pt.iul.ista.poo.utils.Point2D;

public class Buraco extends AbstractObject implements InteractiveObject {

	private boolean fall;
	private boolean blocked;
	private boolean update;
	private static final int SCORE = 15;
	private static final int ENERGY = -10;

	public Buraco(Point2D position) {
		super("Buraco", position);
	}

	@Override
	public boolean interactiveObject() {
		return true;
	}
	
	@Override
	public boolean isInteracting(IsMovable obj) {
		interaction(obj);
		if (obj instanceof Player || obj instanceof Caixote)
			System.out.println("Ups... Maybe it is going to be hard to win now..."
					+ "\n" + "Press 'R' to restart the game.");
		else
			dialogue();
		return update;
	}

	@Override
	public void interaction(IsMovable obj) {
		if (!blocked) {
			fall = false;
			obj.fall();
			fall = obj.getFall();
			if (!fall)
				blocked = true;
			update = true;
		}
	}

	@Override
	public int scoreInfluence() {
		if (fall || blocked)
			return SCORE;
		return 0;
	}

	@Override
	public int energyInfluence() {
		if (blocked)
			return ENERGY;
		return 0;
	}
	
	@Override
	public void dialogue() {
		if (fall)
			System.out.println("One less thing around here...");
		if (blocked)
			System.out.println("Nothing will fall now...");
	}
}