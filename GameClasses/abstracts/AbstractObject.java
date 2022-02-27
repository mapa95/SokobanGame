package abstracts;

import interfaces.IsMovable;
import interfaces.ObjectProperties;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class AbstractObject implements ImageTile, ObjectProperties {
	
	private String imageName;
	private Point2D position;
	private static final int DEFAULTLAYER = 1;
	private int layer;
	
	public AbstractObject(String imageName, Point2D position, int layer) {
		this.imageName = imageName;
		this.position = position;
		this.layer = layer;
	}
	
	public AbstractObject(String imageName, Point2D position) {
		this.imageName = imageName;
		this.position = position;
		this.layer = DEFAULTLAYER;
	}
		
	public AbstractObject(Point2D position) {
		this.position = position;
	}

	//ImageTile
	@Override
	public String getName() {
		return imageName;
	}
	
	@Override
	public Point2D getPosition() {
		return position;
	}
	
	@Override
	public int getLayer() {
		return layer;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	
	/***************** OBJECT PROPERTIES *****************/

	@Override
	public boolean transposableObject(IsMovable obj) {
		return true;
	}
	
	@Override
	public boolean movableObject() {
		return false;
	}
	
	@Override
	public boolean interactiveObject() {
		return false;
	}
	
}
