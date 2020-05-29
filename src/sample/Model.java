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
	// listes de differentes formes présentes dans le pane
	ArrayList<Shape> listeObj;
	int indexSelectionne=-1;
	private Color lineColor;
	
	//Constructeur de la classe model
	public Model(Controller c) {
		controller=c;
		listeObj=new ArrayList();
	}
	
	// mise à -1 de l'indice d'element selectionné si aucune forme n'est selectionnée
	public void setUnselected() {
		indexSelectionne=-1;
	}
	
	// retourne un booleen si l'utilisateur peut selectionner
	public boolean getPeutSelectionner() {
		return selection;
	}
	
	// retourne la forme select
	public Shape getSelected() {
		if (indexSelectionne>=0) return listeObj.get(indexSelectionne);
		else return null;
	}
	
	// fonction pour deselectionner l'element deja selectionné
	public void deselect() {
		Shape s=listeObj.get(indexSelectionne);
		if(s instanceof Rectangle) {
			Rectangle r=(Rectangle)listeObj.get(indexSelectionne);
			r.setWidth(r.getWidth()-8);
			r.setHeight(r.getHeight()-8);
			r.setStroke(r.getFill());
		}else if (s instanceof Ellipse) {
			Ellipse ellipse=(Ellipse)listeObj.get(indexSelectionne);
			ellipse.setRadiusX(ellipse.getRadiusX()-8);
    		ellipse.setRadiusY(ellipse.getRadiusY()-8);
			ellipse.setStroke(ellipse.getFill());
		}else {
			Line line=(Line)listeObj.get(indexSelectionne);
			line.setStrokeWidth(10);
			line.setStroke(lineColor);
		}
		indexSelectionne=-1;
	}
	
	// set la shape ayant ete selectionnée comme celle selectionne dans le modele
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
	
	// retourne un booleen si la forme passée en parametre est selectionnée		
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
	
	// ajoute une forme dans la liste des formes presentes dans la vue
	public void ajouterForme(Shape s) {
		listeObj.add(s);
	}
	
	// retourne la derniere forme créée dans la vue
	public Shape getDerniereForme() {
		return listeObj.get(listeObj.size()-1);
	}
	
	// rend vrai si les bouton radio sont positionnés sur Rectangle
	public boolean getRectangle() {
		return rectangle;
	}

	// rend vrai si les bouton radio sont positionnés sur Ellipse
	public boolean getEllipse() {
		return ellipse;
	}

	// rend vrai si les bouton radio sont positionnés sur Ligne
	public boolean getLine() {
		return line;
	}

	// rend vrai si les bouton radio sont positionnés sur selection/move
		public void selectionner() {
			rectangle=false;
			ellipse=false;
			line=false;
			selection=true;
			System.out.println("rien");
		}
		
	// met color à la couleur donnée par le controlleur
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
	
	// retourne la couleur actuelle du modele
	public Color getColor() {
		return color;
	}
	
	// met tout les booleens à faux sauf rectangle a vrai pour signifier qu'un rectangle est
	// selectionné pour etre tracé
	public void drawRectangle() {
		if (indexSelectionne>=0) deselect();
		rectangle=true;
		ellipse=false;
		line=false;
		selection=false;
		System.out.println("rectangle");
	}
	
	// met tout les booleens à faux sauf ellipse a vrai pour signifier qu'une ellipse est
	// selectionné pour etre tracé
	public void drawEllipse() {
		if (indexSelectionne>=0) deselect();
		ellipse=true;
		rectangle=false;
		line=false;
		selection=false;
		System.out.println("ellipse");
	}
	
	// met tout les booleens à faux sauf ligne a vrai pour signifier qu'une ligne est
		// selectionné pour etre tracé
	public void drawLine() {
		if (indexSelectionne>=0) deselect();
		ellipse=false;
		rectangle=false;
		line=true;
		selection=false;
		System.out.println("line");
	}
}