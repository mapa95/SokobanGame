package objects;

import abstracts.AbstractObject;
import interfaces.InteractiveObject;
import interfaces.IsMovable;
import pt.iscte.dcti.poo.sokoban.starter.Player;
import pt.iscte.dcti.poo.sokoban.starter.SokobanGame;
import pt.iul.ista.poo.utils.Point2D;

public class Martelo extends AbstractObject implements InteractiveObject {

	private boolean ableToBreak;
	private static final int SCORE = 5;
	private static final int ENERGY = 0;
	
	public Martelo(Point2D position) {
		super("Martelo", position);
	}
	
	@Override
	public boolean interactiveObject() {
		return true;
	}
	
	@Override
	public boolean isInteracting(IsMovable obj) {
		interaction(obj);
		return ableToBreak;
	}

	@Override
	public void interaction(IsMovable obj) {
		if (obj instanceof Player) {
			ableToBreak = true;
			((Player)obj).setHammer(true);
			dialogue();
			SokobanGame.getInstance().removeAnObject(this);
		}
	}

	@Override
	public int scoreInfluence() {
		return SCORE;
	}

	@Override
	public int energyInfluence() {
		return ENERGY;
	}

	@Override
	public void dialogue() {
		System.out.println("Now we can break those walls!");
	}

}
