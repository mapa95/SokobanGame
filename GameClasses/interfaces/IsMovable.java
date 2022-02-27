package interfaces;

import pt.iul.ista.poo.utils.Direction;

public interface IsMovable {
	boolean isMovable(Direction direction);
	void move(Direction d);
	void fall();
	boolean getFall();
	Direction direction();
}