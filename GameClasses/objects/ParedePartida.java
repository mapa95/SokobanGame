package objects;

import abstracts.AbstractObject;
import interfaces.InteractiveObject;
import interfaces.IsMovable;
import pt.iscte.dcti.poo.sokoban.starter.Player;
import pt.iscte.dcti.poo.sokoban.starter.SokobanGame;
import pt.iul.ista.poo.utils.Point2D;

public class ParedePartida extends AbstractObject implements InteractiveObject {

	private boolean ableToBreak;
	private static final int SCORE = 20;
	private static final int ENERGY = -5;
	
	public ParedePartida(Point2D position) {
		super("Parede_Partida", position, 2);
	}

	@Override
	public boolean interactiveObject() {
		return true;
	}
	
	@Override
	public boolean transposableObject(IsMovable obj) {
		if (obj instanceof Player)
			ableToBreak = ((Player)obj).hasHammer();
		return ableToBreak;
	}
	
	@Override
	public boolean isInteracting(IsMovable obj) {
		interaction(obj);
		return ableToBreak;
	}

	@Override
	public void interaction(IsMovable obj) {
		if (obj instanceof Player)
			dialogue();
			SokobanGame.getInstance().removeAnObject(this);
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
		if (ableToBreak)
			System.out.println("Bye bye wall.");
		else
			System.out.println("Something is missing... maybe with an hammer!");
	}

}
