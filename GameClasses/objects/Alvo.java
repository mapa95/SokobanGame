package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import abstracts.AbstractObject;
import interfaces.InteractiveObject;
import interfaces.IsMovable;
import pt.iscte.dcti.poo.sokoban.starter.SokobanGame;
import pt.iul.ista.poo.utils.Point2D;

public class Alvo extends AbstractObject implements InteractiveObject {

	private boolean bonus;
	private boolean update;
	private boolean ready;
	private Map<Alvo, Boolean> activeTargets = new HashMap<>();
	private static final int SCORE = 50;
	private static final int ENERGY = 0;

	public Alvo(Point2D position) {
		super("Alvo", position);
	}

	@Override
	public boolean interactiveObject() {
		return true;
	}
	
	@Override
	public boolean isInteracting(IsMovable obj) {
		if (obj instanceof Caixote) {
			interaction(obj);
			update = true;
			dialogue();
		}
		return update;
	}

	@Override
	public void interaction(IsMovable obj) {

		ArrayList<Alvo> targets = SokobanGame.getInstance().getListOfObjects(t -> t instanceof Alvo);
		int counter = 0;
		activeTargets();

		for (Boolean check : activeTargets.values())
			if (check)
				counter++;

		if (targets.size() == counter) {
			SokobanGame.getInstance().levelCompleted();
			ready = true;
		}
	}
	
	
	@Override
	public int scoreInfluence() {
		
		int counter = 0;
		for (Boolean check : activeTargets.values())
			if (check)
				counter++;
		
		if (!bonus && counter < activeTargets.size()) {
			bonus = true;
			return SCORE;
		}
		return 0;
	}

	@Override
	public int energyInfluence() {
		int counter = 0;
		for (Boolean check : activeTargets.values())
			if (check)
				counter++;
		
		if (!bonus && counter < activeTargets.size()) {
			bonus = true;
			return ENERGY;
		}
		return 0;
	}
	
	@Override
	public void dialogue() {
		if (!ready)
			System.out.println("Keep going...");
	}

	public void activeTargets() {

		ArrayList<Alvo> targets = SokobanGame.getInstance().getListOfObjects(t -> t instanceof Alvo);
		ArrayList<Caixote> boxes = SokobanGame.getInstance().getListOfObjects(b -> b instanceof Caixote);
		Map<Alvo, Boolean> mapOfTargets = new HashMap<>();

		for (Alvo t : targets) {
			mapOfTargets.put(t, false);
			for (Caixote b : boxes)
				if (t.getPosition().equals(b.getPosition()))
					for (Entry<Alvo, Boolean> activeT : mapOfTargets.entrySet())
						if (activeT.getKey().equals(t))
							activeT.setValue(true);
		}
		activeTargets = mapOfTargets;
	}
}
