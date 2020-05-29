package sample;

import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Controller {

	private Model model;

	@FXML
	RadioButton rdbSelectMove;
	
	@FXML
	RadioButton rdbEllipse;
	
	@FXML
	RadioButton rdbRectangle;
	
	@FXML
	RadioButton rdbLine;
	
	@FXML
	ColorPicker colorPicker;
	
	@FXML
	Button btnDelete;
	
	@FXML
	Button btnClone;
	
	@FXML
	Pane canvas;
	

	public void initialize() {

		// modele
		model=new Model(this);
		model.setColor(colorPicker.getValue());
		rdbSelectMove.setSelected(true);

		ToggleGroup toggle=rdbRectangle.getToggleGroup();
		
		// on ajoute un listener pour tous les radioButton liés entre eux (tous)
		toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>()  
        { 
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) 
            { 
                RadioButton rb = (RadioButton)toggle.getSelectedToggle(); 
                if (rb != null) { 
                    String s = rb.getText();
                    if (s.equals("Ellipse")) model.drawEllipse();
                    else if (s.equals("Rectangle")) model.drawRectangle();
                    else if (s.contentEquals("Line")) model.drawLine();
                    else model.selectionner();
                } 
            } 
        });
		
		// change la couleur dans le modele en fonction 
		colorPicker.setOnAction(new EventHandler() {
		     public void handle(Event t) {
		         Color c = colorPicker.getValue();
		         model.setColor(c);
		     }
		 });
		
		canvas.addEventHandler(	MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
            	if (model.getRectangle()) {
            		Rectangle rectangle=initRectangle(event.getX(),event.getY());
            		model.ajouterForme(rectangle);
            		rectangle.setOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent t) {
                        	if (model.getPeutSelectionner()) {
	                        	if (!model.estSelectionne(rectangle)) {
	            	            	rectangle.setWidth(rectangle.getWidth()+8);
	            	                rectangle.setHeight(rectangle.getHeight()+8);
	            	                model.setSelected(rectangle);
	                        	}
                        	}
                        }
                    });
            		rectangle.setOnMouseDragged(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent t) {
                        	if (model.estSelectionne(rectangle)) {
                        		rectangle.setX(t.getX());
                            	rectangle.setY(t.getY());
                        	}
                        }
                    });
                	canvas.getChildren().add(model.getDerniereForme());
	
            	}else if (model.getEllipse()) {
            		Ellipse ellipse=initEllipse(event.getX(),event.getY());
            		model.ajouterForme(ellipse);
                	canvas.getChildren().add(model.getDerniereForme());
                	ellipse.setOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent t) {
                        	if (model.getPeutSelectionner()) {
	                        	if (!model.estSelectionne(ellipse)) {
	            	            	ellipse.setRadiusX(ellipse.getRadiusX()+8);
	                        		ellipse.setRadiusY(ellipse.getRadiusY()+8);
	            	                model.setSelected(ellipse);
	                        	}
                        	}
                        }     	
                    });
            		ellipse.setOnMouseDragged(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent t) {
                        	if (model.estSelectionne(ellipse)) {
                        		ellipse.setCenterX(t.getX());
                            	ellipse.setCenterY(t.getY());
                        	}
                        }
                    });
            	}else if (model.getLine()) {
            		Line line=initLine(event.getX(),event.getY());
            		model.ajouterForme(line);
                	canvas.getChildren().add(model.getDerniereForme());	
                	line.setOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent t) {
                        	if (model.getPeutSelectionner()) {
	                        	if (!model.estSelectionne(line)) {
	            	            	line.setStrokeWidth(14);
	            	                model.setSelected(line);
	                        	}
                        	}
                        }
                    });
            		line.setOnMouseDragged(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent t) {
                        	if (model.estSelectionne(line)) {
                        		double oldX=line.getStartX();
                        		double oldY=line.getStartY();
                        		double newX=t.getX();
                        		double newY=t.getY();
                        		line.setStartX(newX);
                        		line.setStartY(newY);
                        		line.setEndX(line.getEndX()+(newX-oldX));
                        		line.setEndY(line.getEndY()+(newY-oldY));
                        	}
                        }
                    });
            	}
            }
        });
		
		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
                new EventHandler<MouseEvent>(){
			
            @Override
            public void handle(MouseEvent event) {
            	if (model.getRectangle()) {
            		drawRectangle((Rectangle)model.getDerniereForme(),event.getX(),event.getY());
            	}else if (model.getEllipse()) {
            		drawEllipse((Ellipse)model.getDerniereForme(),event.getX(),event.getY());
            	}else if (model.getLine()) {
            		drawLine((Line)model.getDerniereForme(),event.getX(),event.getY());
            	}
            }
        });
		
		btnDelete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            	if (model.getPeutSelectionner()) {
            		Shape s=model.getSelected();
            		if (s!=null) {
            			if (s instanceof Rectangle) {
            				canvas.getChildren().remove((Rectangle)s);
            			}
            			else if (s instanceof Ellipse) {
            				canvas.getChildren().remove((Ellipse)s);
            			}
            			else if (s instanceof Line) {
            				canvas.getChildren().remove((Line)s);
            			}
            		}
            	}
            }
        });
		
		btnClone.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            	if (model.getPeutSelectionner()) {
            		Shape s=model.getSelected();
            		if (s!=null) {
            			if (s instanceof Rectangle) {
            				model.deselect();
            				Rectangle ancien = (Rectangle)s;
            				Rectangle copie = copierRectangle(ancien);
            				canvas.getChildren().add(copie);
            			}
            			else if (s instanceof Ellipse) {
            				model.deselect();
            				Ellipse ancien = (Ellipse)s;
            				Ellipse copie = copierEllipse(ancien);
            				canvas.getChildren().add(copie);
            			}
            			else if (s instanceof Line) {
            				model.deselect();
            				Line ancien = (Line)s;
            				Line copie = copierLine(ancien);
            				canvas.getChildren().add(copie);
            			}
            		}
            	}
            }
        });
	}
	
	
	// initialiser le rectangle quand l'utilisateur clique
	private Rectangle initRectangle(double x,double y){
		Rectangle rectangle = new Rectangle();
		rectangle.requestFocus();
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setWidth(0);
        rectangle.setHeight(0);
        rectangle.setFill(model.getColor());
        rectangle.setStrokeWidth(1);
        rectangle.setStroke(model.getColor());
        return rectangle;
	}
	
	// tracer le rectangle en fonction de comment l'utilisateur bouge sa souris
	private void drawRectangle(Rectangle r,double x,double y) {
		r.setHeight(Math.abs(y));
		r.setWidth(Math.abs(x));
	}
	
	// initialiser l'ellipse quand l'utilisateur clique
	private Ellipse initEllipse(double x,double y){
		Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(x);
        ellipse.setCenterY(y);
        ellipse.setRadiusX(0);
        ellipse.setRadiusY(0);
        
        ellipse.setFill(model.getColor());
        ellipse.setStrokeWidth(1);
        ellipse.setStroke(model.getColor());
        return ellipse;
	}
	
	// tracer l'ellipse en fonction de comment l'utilisateur bouge sa souris
	private void drawEllipse(Ellipse e,double x,double y) {
	e.setRadiusX(Math.abs(x-e.getCenterX()));
	e.setRadiusY(Math.abs(y-e.getCenterY()));
	}
	
	// initialiser la ligne quand l'utilisateur clique
	private Line initLine(double x,double y){
		Line line = new Line();
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(x);
        line.setEndY(y);
        line.setStrokeWidth(10);
        line.setStroke(model.getColor());
        return line;
	}
	
	// tracer la ligne en fonction de comment l'utilisateur bouge sa souris
	private void drawLine(Line l,double x,double y) {
		l.setEndX(x);
        l.setEndY(y);
	}

	
	private Rectangle copierRectangle(Rectangle ancien) {
		Rectangle copie=new Rectangle();
		copie.setX(ancien.getX()+20);
		copie.setY(ancien.getY()+20);
		copie.setWidth(ancien.getWidth());
		copie.setHeight(ancien.getHeight());
		copie.requestFocus();
		copie.setFill(ancien.getFill());
        copie.setStrokeWidth(ancien.getStrokeWidth());
        copie.setStroke(copie.getFill());
        copie.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	if (model.getPeutSelectionner()) {
                	if (!model.estSelectionne(copie)) {
    	            	copie.setWidth(copie.getWidth()+20);
    	            	copie.setHeight(copie.getHeight()+20);
    	                model.setSelected(copie);
                	}
            	}
            }
        });
		copie.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	if (model.estSelectionne(copie)) {
            		copie.setX(t.getX());
            		copie.setY(t.getY());
            	}
            }
        });
		model.ajouterForme(copie);
		return copie;
	}
	
	private Ellipse copierEllipse(Ellipse ancien) {
		
		Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(ancien.getCenterX()+20);
        ellipse.setCenterY(ancien.getCenterY()+20);
        ellipse.setRadiusX(ancien.getRadiusX());
        ellipse.setRadiusY(ancien.getRadiusY());
        
        ellipse.setFill(ancien.getFill());
        ellipse.setStrokeWidth(ancien.getStrokeWidth());
        ellipse.setStroke(ancien.getStroke());
        ellipse.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	if (model.getPeutSelectionner()) {
                	if (!model.estSelectionne(ellipse)) {
    	            	ellipse.setRadiusX(ellipse.getRadiusX()+8);
                		ellipse.setRadiusY(ellipse.getRadiusY()+8);
    	                model.setSelected(ellipse);
                	}
            	}
            }     	
        });
		ellipse.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	if (model.estSelectionne(ellipse)) {
            		ellipse.setCenterX(t.getX());
                	ellipse.setCenterY(t.getY());
            	}
            }
        });
		model.ajouterForme(ellipse);
        return ellipse;
	}
	
	private Line copierLine(Line ancien) {
		Line line = new Line();
        line.setStartX(ancien.getStartX()+20);
        line.setStartY(ancien.getStartY()+20);
        line.setEndX(ancien.getEndX()+20);
        line.setEndY(ancien.getEndY()+20);
        line.setStrokeWidth(ancien.getStrokeWidth());
        line.setStroke(ancien.getStroke());
        line.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	if (model.getPeutSelectionner()) {
                	if (!model.estSelectionne(line)) {
    	            	line.setStrokeWidth(14);
    	                model.setSelected(line);
                	}
            	}
            }
        });
		line.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	if (model.estSelectionne(line)) {
            		double oldX=line.getStartX();
            		double oldY=line.getStartY();
            		double newX=t.getX();
            		double newY=t.getY();
            		line.setStartX(newX);
            		line.setStartY(newY);
            		line.setEndX(line.getEndX()+(newX-oldX));
            		line.setEndY(line.getEndY()+(newY-oldY));
            	}
            }
        });
		model.ajouterForme(line);
		return line;		
	}

}
