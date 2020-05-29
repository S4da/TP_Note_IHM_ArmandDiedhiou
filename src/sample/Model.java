package sample;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Model {

	private Color color;
	private boolean rectangle=false,ellipse=false,line=false,selection=false;
	private Controller controller;
	ArrayList<Shape> listeObj;
	int indexSelectionne=-1;
	private Color lineColor;
	
	
	public void setUnselected() {
		indexSelectionne=-1;
	}
	
	public boolean getPeutSelectionner() {
		return selection;
	}
	
	public Shape getSelected() {
		if (indexSelectionne>=0) return listeObj.get(indexSelectionne);
		else return null;
	}
	public void deselect() {
		Shape s=listeObj.get(indexSelectionne);
		if(s instanceof Rectangle) {
			Rectangle r=(Rectangle)listeObj.get(indexSelectionne);
			r.setWidth(r.getWidth()-8);
			r.setHeight(r.getHeight()-8);
			r.setStroke(color);
		}else if (s instanceof Ellipse) {
			Ellipse ellipse=(Ellipse)listeObj.get(indexSelectionne);
			ellipse.setRadiusX(ellipse.getRadiusX()-8);
    		ellipse.setRadiusY(ellipse.getRadiusY()-8);
			ellipse.setStroke(color);
		}else {
			Line line=(Line)listeObj.get(indexSelectionne);
			line.setStrokeWidth(10);
			line.setStroke(lineColor);
		}
		indexSelectionne=-1;
	}
	public void setSelected(Shape s) {
		if (indexSelectionne>=0) deselect();
		
		for (int i=0;i<listeObj.size();i++) {
			if (listeObj.get(i) instanceof Rectangle && s instanceof Rectangle) {
				Rectangle r=(Rectangle)s;
				if (r.getX()==((Rectangle)listeObj.get(i)).getX() && r.getY()==((Rectangle)listeObj.get(i)).getY()
						&& r.getHeight()==((Rectangle)listeObj.get(i)).getHeight() && r.getWidth()==((Rectangle)listeObj.get(i)).getWidth()) {
					indexSelectionne=i;
					r.setStroke(Color.RED);
					break;
				}
			}
		
			else if (listeObj.get(i) instanceof Ellipse && s instanceof Ellipse) {
				Ellipse e=(Ellipse)s;
				if (e.getCenterX()==((Ellipse)listeObj.get(i)).getCenterX() && e.getCenterY()==((Ellipse)listeObj.get(i)).getCenterY()
						&& e.getRadiusX()==((Ellipse)listeObj.get(i)).getRadiusX() && e.getRadiusY()==((Ellipse)listeObj.get(i)).getRadiusY()) {
					indexSelectionne=i;
					e.setStroke(Color.RED);
					break;
				}
			}
			else if (listeObj.get(i) instanceof Line && s instanceof Line) {
				Line l=(Line)s;
				if (l.getStartX()==((Line)listeObj.get(i)).getStartX() && l.getStartY()==((Line)listeObj.get(i)).getStartY()
						&& l.getEndX()==((Line)listeObj.get(i)).getEndX() && l.getEndY()==((Line)listeObj.get(i)).getEndY()) {
					indexSelectionne=i;
					lineColor=(Color) l.getStroke();
					l.setStroke(Color.RED);
					break;
				}
						
			}
		}
	}
			
	public boolean estSelectionne(Shape s) {
		boolean selec=false;
		if (indexSelectionne>=0) {
			if (s instanceof Rectangle && listeObj.get(indexSelectionne) instanceof Rectangle) {
				Rectangle r=(Rectangle)s;
				if (r.getX()==((Rectangle)listeObj.get(indexSelectionne)).getX() && r.getY()==((Rectangle)listeObj.get(indexSelectionne)).getY()
						&& r.getHeight()==((Rectangle)listeObj.get(indexSelectionne)).getHeight() && r.getWidth()==((Rectangle)listeObj.get(indexSelectionne)).getWidth()) {
					selec=true;
				}
					
			}else if (s instanceof Ellipse && listeObj.get(indexSelectionne) instanceof Ellipse) {
				Ellipse e=(Ellipse)s;
				if (e.getCenterX()==((Ellipse)listeObj.get(indexSelectionne)).getCenterX() && e.getCenterY()==((Ellipse)listeObj.get(indexSelectionne)).getCenterY()
						&& e.getRadiusX()==((Ellipse)listeObj.get(indexSelectionne)).getRadiusX() && e.getRadiusY()==((Ellipse)listeObj.get(indexSelectionne)).getRadiusY()) {
					selec=true;
				}
			}else if (s instanceof Line && listeObj.get(indexSelectionne) instanceof Line) {
				Line l=(Line)s;
				if (l.getStartX()==((Line)listeObj.get(indexSelectionne)).getStartX() && l.getStartY()==((Line)listeObj.get(indexSelectionne)).getStartY()
						&& l.getEndX()==((Line)listeObj.get(indexSelectionne)).getEndX() && l.getEndY()==((Line)listeObj.get(indexSelectionne)).getEndY()) {
					selec=true;
				}
			}
		}
		return selec;
	}
	
	
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
		if (indexSelectionne>=0) {
			if (listeObj.get(indexSelectionne) instanceof Rectangle) {
				((Rectangle)listeObj.get(indexSelectionne)).setFill(color);
			}else if (listeObj.get(indexSelectionne) instanceof Ellipse) {
				((Ellipse)listeObj.get(indexSelectionne)).setFill(color);
			}else if (listeObj.get(indexSelectionne) instanceof Line) {
				lineColor=color;
			}
		}
	}
	
	public Color getColor() {
		return color;
	}
	
	public void selectionner() {
		rectangle=false;
		ellipse=false;
		line=false;
		selection=true;
		System.out.println("rien");
	}
	
	public void drawRectangle() {
		if (indexSelectionne>=0) deselect();
		rectangle=true;
		ellipse=false;
		line=false;
		selection=false;
		System.out.println("rectangle");
	}
	
	public void drawEllipse() {
		if (indexSelectionne>=0) deselect();
		ellipse=true;
		rectangle=false;
		line=false;
		selection=false;
		System.out.println("ellipse");
	}
	
	public void drawLine() {
		if (indexSelectionne>=0) deselect();
		ellipse=false;
		rectangle=false;
		line=true;
		selection=false;
		System.out.println("line");
	}
}