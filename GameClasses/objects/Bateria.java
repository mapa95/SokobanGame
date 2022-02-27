package objects;

import abstracts.AbstractObject;
import interfaces.InteractiveObject;
import interfaces.IsMovable;
import pt.iscte.dcti.poo.sokoban.starter.Player;
import pt.iscte.dcti.poo.sokoban.starter.SokobanGame;
import pt.iul.ista.poo.utils.Point2D;

public class Bateria extends AbstractObject implements InteractiveObject {

	private boolean charge;
	private static final int SCORE = 25;
	private static final int ENERGY = 20;

	public Bateria(Point2D position) {
		super("Bateria", position);
	}

	@Override
	public boolean interactiveObject() {
		return true;
	}
	
	@Override
	public boolean isInteracting(IsMovable obj) {
		if (obj instanceof Player)
			interaction(obj);
		return charge;
	}

	@Override
	public void interaction(IsMovable obj) {
		charge = true;
		SokobanGame.getInstance().removeAnObject(this);
	}

	@Override
	public int scoreInfluence() {
		dialogue();
		return SCORE;
	}

	@Override
	public int energyInfluence() {
		return ENERGY;
	}
	
	@Override
	public void dialogue() {
		System.out.println("Thank you for the refill!");
	}

}
