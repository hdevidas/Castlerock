package castleGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public abstract class Sprite {

    private ImageView imageView;

    private Pane layer;

    protected double x;
    protected double y;

    protected double dx;
    protected double dy;

    private int level;
    private int money;
    private String name;

	private double w;
    private double h;

    public Sprite(Pane layer, Image image, double x, double y, String name, int money, int level) {

        this.layer = layer;
        this.x = x;
        this.y = y;

        this.name = name;
        this.money = money;
        this.level = level;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);

        this.w = image.getWidth(); 
        this.h = image.getHeight(); 

        addToLayer();

    }

    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    protected ImageView getView() {
        return imageView;
    }

    public void updateUI() {
        imageView.relocate(x, y);
    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
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
}
