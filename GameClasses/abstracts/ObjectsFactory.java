package abstracts;

import objects.Alvo;
import objects.Bateria;
import objects.BigStone;
import objects.Buraco;
import objects.Caixote;
import objects.Chao;
import objects.Gelo;
import objects.Martelo;
import objects.Parede;
import objects.ParedePartida;
import objects.SmallStone;
import objects.Teleporte;
import pt.iscte.dcti.poo.sokoban.starter.Player;
import pt.iul.ista.poo.utils.Point2D;

public abstract class ObjectsFactory {

//	private char object;
//	private int x, y;

//	public ObjectsFactory(char obj, int x, int y) {
//		this.object = obj;
//		this.x = x;
//		this.y = y;
//	}
//
//	public char getObject() {
//		return object;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public int getY() {
//		return y;
//	}

	public static AbstractObject createObject(char obj, int x, int y) {

		switch (obj) {

		// Principais
		case ' ':
			return new Chao(new Point2D(x, y));
		case 'E':
			return new Player(new Point2D(x, y));
		case '#':
			return new Parede(new Point2D(x, y));
		case 'C':
			return new Caixote(new Point2D(x, y));
		case 'X':
			return new Alvo(new Point2D(x, y));

		// Interactives
		case 'b':
			return new Bateria(new Point2D(x, y));

		case 'O':
			return new Buraco(new Point2D(x, y));

		// Movables
		case 'S':
			return new BigStone(new Point2D(x, y));
		case 's':
			return new SmallStone(new Point2D(x, y));

		// New Objects
		case 'm':
			return new Martelo(new Point2D(x, y));
		case '%':
			return new ParedePartida(new Point2D(x, y));
		case 't':
			return new Teleporte(new Point2D(x, y));
		case 'g':
			return new Gelo(new Point2D(x, y));

		}
		return null;
	}
}
