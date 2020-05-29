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

		
		model=new Model(this);
		model.setColor(colorPicker.getValue());
		rdbSelectMove.setSelected(true);

		ToggleGroup toggle=rdbRectangle.getToggleGroup();
		
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
                        	if (!model.estSelectionne(rectangle)) {
            	            	System.out.println("bonjour");
            	            	rectangle.setWidth(rectangle.getWidth()+8);
            	                rectangle.setHeight(rectangle.getHeight()+8);
            	                model.setSelected(rectangle);
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
                        	if (!model.estSelectionne(ellipse)) {
            	            	System.out.println("bonjour");
            	            	ellipse.setRadiusX(ellipse.getRadiusX()+8);
                        		ellipse.setRadiusY(ellipse.getRadiusY()+8);
            	                model.setSelected(ellipse);
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
                        	if (!model.estSelectionne(line)) {
            	            	System.out.println("bonjour");
            	            	line.setStrokeWidth(14);
            	                model.setSelected(line);
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
        rectangle.setStroke(Color.BLACK);
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
        ellipse.setStroke(Color.BLACK);
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
	
	/*
	private void drawRectangle(GraphicsContext gc2, Rectangle rec){
        double canvasWidth = gc2.getCanvas().getWidth();
        double canvasHeight = gc2.getCanvas().getHeight();

        gc2.setFill(model.getColor());
        gc2.setStroke(model.getColor());
        gc2.setLineWidth(5);

        gc2.fill();
        gc2.strokeRect(
                rec.getX(),              //x of the upper left corner
                rec.getY(),              //y of the upper left corner
                rec.getWidth(),    //width of the rectangle
                rec.getHeight());  //height of the rectangle

        gc2.setFill(model.getColor());
        gc2.setStroke(model.getColor());
        gc2.setLineWidth(1);

    }*/
}
