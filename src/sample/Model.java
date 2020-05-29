package sample;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Model {

	private Color color;
	private boolean rectangle=false,ellipse=false,line=false;
	private Controller controller;
	ArrayList<Shape> listeObj;
	
	public Model(Controller c) {
		controller=c;
		listeObj=new ArrayList();
	}
	
	public void ajouterForme(Shape s) {
		listeObj.add(s);
	}
	
	public Shape getDerniereForme() {
		return listeObj.get(listeObj.size()-1);
	}
	
	public boolean getRectangle() {
		return rectangle;
	}

	public boolean getEllipse() {
		return ellipse;
	}

	public boolean getLine() {
		return line;
	}

	public void setColor(Color color) {
		this.color=color;
		System.out.println(this.color);
	}
	
	public Color getColor() {
		return color;
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