package objects;

import java.util.ArrayList;

import abstracts.AbstractObject;
import interfaces.InteractiveObject;
import interfaces.IsMovable;
import pt.iscte.dcti.poo.sokoban.starter.Player;
import pt.iscte.dcti.poo.sokoban.starter.SokobanGame;
import pt.iul.ista.poo.utils.Point2D;

public class Teleporte extends AbstractObject implements InteractiveObject {

	private boolean blocked;
	private boolean teleport;
	private static final int SCORE = 0;
	private static final int ENERGY = -2;

	public Teleporte(Point2D position) {
		super("Portal", position);
	}

	@Override
	public boolean interactiveObject() {
		return true;
	}
	
	@Override
	public boolean isInteracting(IsMovable obj) {

		Teleporte other = SokobanGame.getInstance()
				.getObjectFromType(t -> t instanceof Teleporte && !(t.getPosition().equals(getPosition())));
		teleport = false;
		check();

		if (!other.blocked) {
			teleport = true;
			interaction(obj);
		}

		if (obj instanceof Player)
			dialogue();

		return teleport;
	}

	private void check() {

		ArrayList<AbstractObject> movables = SokobanGame.getInstance().getListOfObjects(m -> m instanceof IsMovable);
		Teleporte other = SokobanGame.getInstance()
				.getObjectFromType(t -> t instanceof Teleporte && !(t.getPosition().equals(getPosition())));

		for (AbstractObject m : movables)
			if (m.getPosition().equals(other.getPosition())) {
				other.blocked = true;
				return;
			} else
				other.blocked = false;
	}

	@Override
	public void interaction(IsMovable obj) {
		Teleporte other = SokobanGame.getInstance()
				.getObjectFromType(t -> t instanceof Teleporte && !(t.getPosition().equals(getPosition())));
		((AbstractObject) obj).setPosition(other.getPosition());
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
		if (teleport)
			System.out.println("Oh... my stomach!");
		else
			System.out.println("Oh no, it seems that the portal is blocked on the other side!");
	}
}