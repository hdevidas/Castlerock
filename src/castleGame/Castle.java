package castleGame;

import java.util.ArrayList;
import java.util.List;

import castleGame.Inputs;
import castleGame.Main;
import castleGame.Settings;
import castleGame.Inputs;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Castle extends Sprite {

	private Inputs inputs;
	private String name;
	private int level;
	private int money;
	private int army_life[] = new int[3];
	private String owner;
	
	public Castle(Pane layer, Image image, double x, double y, Inputs inputs, String name, int money, int level, int[] army_life, String owner) {
		super(layer, image, x, y);
		this.inputs = inputs;
		this.money = money;
		this.level = level;
		this.name = name;
		
		this.army_life[0] = army_life[0];
		this.army_life[1] = army_life[1];
		this.army_life[2] = army_life[2];
		
		this.owner = owner;
	}

	public void enough_money_to_level_up() { //MONTE D'UN NIVEAU LE CHATEAU
		if (getMoney() >= (getLevel()*1000)) {
			setMoney(getMoney()-getLevel()*1000);
			setLevel(getLevel()+1);
		}
	}

	public void build_troupe() { //CONSTRUIS UN PIQUIER
		if (  getMoney() >= TroupeType.Piquier.getProductionCost() ) {
			setMoney( getMoney()-TroupeType.Piquier.getProductionCost());
			//this.army_life[TroupeType.Piquier.ordinal()]=army_life[TroupeType.Piquier.ordinal()] + TroupeType.Piquier.getHealthPoint();
			//setArmy_life(TroupeType.Piquier.ordinal(), army_life[TroupeType.Piquier.ordinal()] + TroupeType.Piquier.getHealthPoint());
			change_army_life(TroupeType.Piquier.ordinal(),army_life[TroupeType.Piquier.ordinal()] + TroupeType.Piquier.getHealthPoint());

		}
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
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public int[] getArmy_life() {
		return army_life;
	}

	public void change_army_life(int index, int new_number) {
		this.army_life[index] = new_number;
	}
	
	public boolean is_player() {
		if (getOwner()=="player") {
			return true;
		}
		return false;
	}
}
