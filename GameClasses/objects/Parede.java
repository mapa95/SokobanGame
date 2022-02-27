package objects;

import abstracts.AbstractObject;
import interfaces.IsMovable;
import pt.iul.ista.poo.utils.Point2D;

public class Parede extends AbstractObject {
	
	public Parede(Point2D position) {
		super("Parede", position, 2);
	}

	@Override
	public boolean transposableObject(IsMovable obj) {
		return false;
	}
	
}
