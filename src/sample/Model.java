package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Model {

	private Color color;
	private boolean rectangle=false,ellipse=false,line=false;
	private Controller controller;
	private GraphicsContext gc;
	
	public Model(Controller c) {
		controller=c;
		gc=controller.getCanvas().getGraphicsContext2D();
	}
	
	public void setColor(Color color) {
		this.color=color;
	}
	
	public void selectionner() {
		rectangle=false;
		ellipse=false;
		line=false;
		System.out.println("rien");
	}
	
	public void drawRectangle() {
		rectangle=true;
		ellipse=false;
		line=false;
		System.out.println("rectangle");
	}
	
	public void drawEllipse() {
		ellipse=true;
		rectangle=false;
		line=false;
		System.out.println("ellipse");
	}
	
	public void drawLine() {
		ellipse=false;
		rectangle=false;
		line=true;
		System.out.println("line");
	}
}