package castleGame.base;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Sprite {

	// VARIABLES
    private ImageView imageView;

    private Pane layer;

    protected double x;
    protected double y;

    protected double dx;
    protected double dy;

	private double w;
    private double h;

    private Image image;
    
	    
	// CONSTRUCTORS
    public Sprite(Pane layer, Image image, double x, double y) {

        this.layer = layer;
        this.x = x;
        this.y = y;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);

        this.w = image.getWidth(); 
        this.h = image.getHeight(); 

        this.image = image;
        addToLayer();

    }
    
    public Sprite(Pane layer, Image image, Point2D pos) 
    {
    	this(layer, image, pos.getX(), pos.getY());
	}



	// GETTERS AND SETTERS
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

    public ImageView getView() {
        return imageView;
    }
    
    public Image getImage() {
        return image;
    }
    
    
	// INHERITED METHODS
		
		
		
	// METHODS
    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void updateUI() {
        imageView.relocate(x, y);
    }
}
