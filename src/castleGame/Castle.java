package castleGame;

import castleGame.Inputs;
import castleGame.Main;
import castleGame.Settings;
import castleGame.Inputs;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Castle extends Sprite {

	private Inputs inputs;
	
	public Castle(Pane layer, Image image, double x, double y, Inputs inputs, String name, int money, int level) {
		super(layer, image, x, y, name, money, level);
		this.inputs = inputs;
	}
	
	//INUTILE ?
	public void processInput() {
		if (inputs.isLevelUp()) {
			level_up();
		}
	}
	public void is_enough_money_to_level_up() { //MONTE D'UN NIVEAU LE CHATEAU
		if (getMoney() >= (getLevel()*1000)) {
			setMoney(getMoney()-getLevel()*1000);
			setLevel(getLevel()+1);
		}
	}
	
	public void level_up() { //MONTE D'UN NIVEAU LE CHATEAU
		setLevel(getLevel()+1);
	}
	
	public void money_up() { //GENERATION FLORINS
		setMoney(getMoney() + getLevel());
	}
	
	public boolean is_the_same_name(String name) {
		if (this.getName() == name) {
			return true;
		}
		return false;
	}
}
