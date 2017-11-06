package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import static java.lang.Math.*;

public class Controller {
    @FXML
    private Label first;
    @FXML
    private Label second;
    @FXML
    private Label third;
    @FXML
    private Label forth;
    @FXML
    private Label fifth;
    @FXML
    private CheckBox graph1;
    @FXML
    private CheckBox graph2;
    @FXML
    private CheckBox graph3;
    @FXML
    private CheckBox graph4;
    @FXML
    private CheckBox graph5;
    @FXML
    private RadioButton sqrt;
    @FXML
    private RadioButton cos;
    @FXML
    private RadioButton growingSin;
    @FXML
    private RadioButton sqr;
    @FXML
    private TextField input;

    Function func;
    @FXML public void drawCos(){
        func = (double x)->cos(x);
        draw(false);
    }
    @FXML public void drawPow2(){
        func = (double x)->pow(x,2);
        draw(false);
    }
    @FXML public void drawGrowingSin(){
        func = (double x)->x*sin(x);
        draw(false);
    }
    @FXML public void drawSqrt(){
        func= (double x) -> sqrt(x);
        draw(true);
    }
    @FXML
    private NumberAxis xAxis = new NumberAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis,yAxis);
    @FXML
    public void doCheck(){
        first.setText(input.getText());
        second.setText(input.getText());
        third.setText(input.getText());
        forth.setText(input.getText());
        fifth.setText(input.getText());
    }
    @FXML
    public void drawGraphics(){
        if(cos.isSelected()){
            drawCos();
        }else if(sqr.isSelected()){
            drawPow2();
        }else if(growingSin.isSelected()){
            drawGrowingSin();
        }else if(sqrt.isSelected()){
            drawSqrt();
        }else{
            System.out.println("Выберете функцию");
        }
    }
    public void draw(boolean fromZero){
        double[] coordinatesX = {0,PI/2,PI,PI*3/2,PI*2};
        double[] coordinatesY = {func.calculate(0),func.calculate(PI/2),func.calculate(PI),func.calculate(PI*3/2),func.calculate(PI*2)};
        chart.getData().clear();
        XYChart.Series mainGraph = new XYChart.Series();
        XYChart.Series Interpolate5points = new XYChart.Series();
        XYChart.Series dots1 = new XYChart.Series();
        ObservableList<XYChart.Data> PointsOfMainGraph = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datasDots1 = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> Int5Points = FXCollections.observableArrayList();
        if(!fromZero)
        for(double i=-10; i<10; i+=0.1){
            PointsOfMainGraph.add(new XYChart.Data(i,func.calculate(i)));
        }
        else
            for(double i=0; i<14; i+=0.1){
                PointsOfMainGraph.add(new XYChart.Data(i,func.calculate(i)));
            }
        for(double i=0; i<7; i+=0.1){
            Int5Points.add(new XYChart.Data(i,Lagrange.interpolate(i,coordinatesX,coordinatesY)));
        }
        datasDots1.add(new XYChart.Data(0,func.calculate(0)));
        datasDots1.add(new XYChart.Data(PI/2,func.calculate(PI/2)));
        datasDots1.add(new XYChart.Data(PI,func.calculate(PI)));
        datasDots1.add(new XYChart.Data(PI*3/2,func.calculate(PI*3/2)));
        datasDots1.add(new XYChart.Data(PI*2,func.calculate(PI*2)));
        mainGraph.setData(PointsOfMainGraph);
        Interpolate5points.setData(Int5Points);
        dots1.setData(datasDots1);
        chart.getData().add(mainGraph);
        chart.getData().add(Interpolate5points);
        chart.getData().add(dots1);
    }
}