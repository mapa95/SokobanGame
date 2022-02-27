package objects;

import abstracts.AbstractObject;
import interfaces.InteractiveObject;
import interfaces.IsMovable;
import pt.iul.ista.poo.utils.Point2D;

public class Gelo extends AbstractObject implements InteractiveObject {

	private boolean objSlide;
	private static final int SCORE = 0;
	private static final int ENERGY = 0;
	
	public Gelo(Point2D position) {
		super("Gelo", position);
	}

	@Override
	public boolean interactiveObject() {
		return true;
	}
	
	@Override
	public boolean isInteracting(IsMovable obj) {
		objSlide = obj.isMovable(obj.direction());
		interaction(obj);
		return objSlide;
	}

	@Override
	public void interaction(IsMovable obj) {
		obj.move(obj.direction());
		if (obj.isMovable(obj.direction()))
			dialogue();
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
		System.out.println("This was FAST!");
	}
}
