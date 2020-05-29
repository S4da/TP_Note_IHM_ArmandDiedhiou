package sample;

import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.io.File;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	Canvas canvas;
	
	GraphicsContext gc=canvas.getGraphicsContext2D();
	
	public void initialize() {
		model=new Model(this);
		rdbSelectMove.setSelected(true);
		initDraw(gc);
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
		
		canvas.addEventHandler(	MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                gc.beginPath();
                gc.moveTo(event.getX(), event.getY());
                gc.stroke();
            }
        });
		
		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
                new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                gc.lineTo(event.getX(), event.getY());
                gc.stroke();
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
                new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {

            }
        });
		
	}
	public void actualiserVue() {
		
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	/*
	public void tracer(GraphicsContext gc,Shape s) {
		
	}
	*/
	/*
	private void drawRectangle(GraphicsContext gc,Rectangle rect){
		gc.setFill(Color.WHITESMOKE);
		gc.fillRect(rect.getX(),
                     rect.getY(), 
                     rect.getWidth(), 
                     rect.getHeight());
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
}*/
	private void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

    }
}
