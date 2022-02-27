package pt.iscte.dcti.poo.sokoban.starter;

import java.util.ArrayList;
import java.util.List;
import abstracts.AbstractObject;
import abstracts.MovableObject;
import interfaces.InteractiveObject;
import interfaces.IsMovable;
import objects.Buraco;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Player extends AbstractObject implements IsMovable {

	private boolean hammer;
	private boolean ableToMove;

	public Player(Point2D initialPosition) {
		super("Empilhadora_U", initialPosition, 2);
		ableToMove = true;
	}

	@Override
	public void move(Direction direction) {

		Point2D newPosition = getPosition().plus(direction.asVector());
		ArrayList<MovableObject> movables = SokobanGame.getInstance()
				.getListOfObjects(obj -> obj instanceof MovableObject);
		ArrayList<InteractiveObject> interactives = SokobanGame.getInstance().getListOfObjects(
				inter -> inter instanceof InteractiveObject && inter.getPosition().equals(newPosition));

		if (isMovable(direction) && ImageMatrixGUI.getInstance().isWithinBounds(newPosition) == true) {
			setPosition(newPosition);

			switch (direction) {
			case DOWN:
				setImageName("Empilhadora_D");
				break;
			case UP:
				setImageName("Empilhadora_U");
				break;
			case LEFT:
				setImageName("Empilhadora_L");
				break;
			case RIGHT:
				setImageName("Empilhadora_R");
				break;
			}

			for (MovableObject objM : movables)
				if (objM.getPosition().equals(newPosition))
					objM.move(direction);

			for (InteractiveObject inter : interactives)
				if (getPosition().equals(((AbstractObject) inter).getPosition()))
					if (inter.isInteracting(this))
						if (!(inter instanceof Buraco))
							SokobanGame.getInstance().updateScore(inter);
		}
		ImageMatrixGUI.getInstance().update();
	}

	@Override
	public boolean isMovable(Direction direction) {

		Point2D newPosition = getPosition().plus(direction.asVector());
		List<AbstractObject> objects = SokobanGame.getInstance().getObjects();
		
		if (ableToMove) {
			for (AbstractObject obj : objects)
				if (obj.getPosition().equals(newPosition)) {
					if (obj instanceof MovableObject)
						return ((MovableObject) obj).isMovable(direction);
					else if (!obj.transposableObject(this))
						return false;
				}
		}
		return ableToMove;
	}

	@Override
	public void fall() {
		ableToMove = false;
		SokobanGame.getInstance().losingTheGame();
	}

	@Override
	public boolean getFall() {
		return true;
	}

	@Override
	public Direction direction() {
		int lastKeyPressed = ImageMatrixGUI.getInstance().keyPressed();
		if (Direction.isDirection(lastKeyPressed))
			return Direction.directionFor(lastKeyPressed);
		return null;
	}

	public boolean hasHammer() {
		return hammer;
	}

	public void setHammer(boolean set) {
		hammer = set;
	}

}