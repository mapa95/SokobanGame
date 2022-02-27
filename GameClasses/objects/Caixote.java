package objects;

import abstracts.MovableObject;
import pt.iul.ista.poo.utils.Point2D;

public class Caixote extends MovableObject{

	public Caixote(Point2D position) {
		super("Caixote", position);
	}
	
//	@Override
//	public void fall() {
//		
//		ArrayList<Alvo> targets = SokobanGame.getInstance().getListOfObjects(t -> t instanceof Alvo);
//		ArrayList<Caixote> boxes = SokobanGame.getInstance().getListOfObjects(b -> b instanceof Caixote);
//		
//		SokobanGame.getInstance().removeAnObject(this);
//		if (boxes.size() < targets.size())
//			SokobanGame.getInstance().losingTheGame();
//	}
}
