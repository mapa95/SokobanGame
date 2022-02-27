package abstracts;

import java.util.ArrayList;
import java.util.List;

import interfaces.InteractiveObject;
import interfaces.IsMovable;
import pt.iscte.dcti.poo.sokoban.starter.SokobanGame;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class MovableObject extends AbstractObject implements IsMovable {

	private boolean fall;
	private boolean ableToMove;

	public MovableObject(String imageName, Point2D position) {
		super(imageName, position, 2);
		fall = true;
		ableToMove = true;
	}

	public MovableObject(String imageName, Point2D position, boolean fall) {
		super(imageName, position, 2);
		this.fall = fall;
		ableToMove = true;
	}

	@Override
	public boolean getFall() {
		return fall;
	}

	@Override
	public boolean transposableObject(IsMovable obj) {
		return false;
	}

	@Override
	public boolean movableObject() {
		return true;
	}

	@Override
	public boolean isMovable(Direction direction) {

		Point2D newPosition = getPosition().plus(direction.asVector());
		List<AbstractObject> objects = SokobanGame.getInstance().getObjects();

		for (AbstractObject obj : objects)
			if (obj.getPosition().equals(newPosition))
				if (!ableToMove || !obj.transposableObject(this))
					return false;
		return ableToMove;
	}

	@Override
	public void move(Direction direction) {

		Point2D newPosition = getPosition().plus(direction.asVector());
		ArrayList<InteractiveObject> interactives = SokobanGame.getInstance().getListOfObjects(
				inter -> inter instanceof InteractiveObject && inter.getPosition().equals(newPosition));

		if (isMovable(direction) && ImageMatrixGUI.getInstance().isWithinBounds(newPosition)) {
			setPosition(newPosition);

			for (InteractiveObject inter : interactives)
				if (inter.isInteracting(this))
					SokobanGame.getInstance().updateScore(inter);
		}
	}

	@Override
	public void fall() {
		if (fall)
			SokobanGame.getInstance().removeAnObject(this);
		else
			ableToMove = false;
	}

	@Override
	public Direction direction() {
		int lastKeyPressed = ImageMatrixGUI.getInstance().keyPressed();
		if (Direction.isDirection(lastKeyPressed))
			return Direction.directionFor(lastKeyPressed);
		return null;
	}
}