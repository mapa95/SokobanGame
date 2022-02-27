package interfaces;

public interface InteractiveObject {
	boolean isInteracting(IsMovable obj);
	void interaction(IsMovable obj);
	void dialogue();
	int scoreInfluence();
	int energyInfluence();
}
