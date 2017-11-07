package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import static java.lang.Float.NaN;
import static java.lang.Math.*;

public class Controller {
    Function func = (double x)->cos(x) + x;

    public double[] getCoordinates9Y() {
        double [] value = {func.calculate(0),func.calculate(PI/4),func.calculate(PI/2),func.calculate(PI*3/4)
                ,func.calculate(PI),func.calculate(PI*5/4),func.calculate(PI*3/2),func.calculate(PI*7/8),func.calculate(PI*2)};
        return value;
    }
    public double[] getCoordinatesY() {
        double [] value = {func.calculate(0),func.calculate(PI/2),func.calculate(PI),func.calculate(PI*3/2),func.calculate(PI*2)};
        double[] coordinatesNegY = {func.calculate(-PI),func.calculate(-PI/2),func.calculate(0),func.calculate(PI*1/2),func.calculate(PI)};
        return value;
    }
    public double[] getCoordinates9NegY() {
        double [] value = {func.calculate(0),func.calculate(-PI/4),func.calculate(PI/4),func.calculate(-PI/2),func.calculate(PI/2)
                ,func.calculate(-PI*3/4),func.calculate(PI*3/4),func.calculate(-PI),func.calculate(PI)};
        return value;
    }
    public double[] getCoordinatesNegY() {
        double [] value = {func.calculate(-PI),func.calculate(-PI/2),func.calculate(0),func.calculate(PI*1/2),func.calculate(PI)};
        return value;
    }
    public double[] getCoordinatesNegBigY() {
        double [] value = {func.calculate(-PI*3),func.calculate(-PI*3/2),func.calculate(0),func.calculate(PI*3/2),func.calculate(PI*3)};
        return value;
    }
    public double[] getCoordinatesBigY() {
        double [] value = {func.calculate(0),func.calculate(PI*3/2),func.calculate(PI*3),func.calculate(PI*9/2),func.calculate(PI*6)};
        return value;
    }
    double[] coordinatesX = {0,PI/2,PI,PI*3/2,PI*2};
    double[] coordinatesNegX = {-PI,-PI/2,0,PI*1/2,PI};
    double[] coordinates9X = {0,PI/4,PI/2,PI*3/4,PI,PI*5/4,PI*3/2,PI*7/8,PI*2};
    double[] coordinates9NegX = {0,-PI/4,PI/4,-PI/2,PI/2,-PI*3/4,PI*3/4,-PI,PI};
    double[] coordinatesNegBigX = {-PI*3,-PI*3/2,0,PI*3/2,PI*3};
    double[] coordinatesBigX = {0,PI*3/2,PI*3,PI*9/2,PI*6};
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
    @FXML public void drawCos(){
        func = (double x)->cos(x) + x;
        draw(false);
    }
    @FXML public void drawPow2(){
        func = (double x)->pow(x,3)+1;
        draw(false);
    }
    @FXML public void drawGrowingSin(){
        func = (double x)->sin(x);
        draw(false);
    }
    @FXML public void drawSqrt(){
        func= (double x) -> Math.E*sin(x)-sqrt(x);
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
        boolean b = false;
        double x = Double.valueOf(input.getText());
        if(Double.isNaN(func.calculate(-1)))
            b=true;
        if(Double.isNaN(func.calculate(x))){
            first.setText("x > 0!");
            second.setText("x > 0!");
            third.setText("x > 0!");
            forth.setText("x > 0!");
            fifth.setText("x > 0!");
            return;
        }
        first.setText(String.valueOf(func.calculate(x)));
        if(b){
            third.setText(String.valueOf(Lagrange.interpolate(x,coordinatesBigX,getCoordinatesBigY())));
            forth.setText(String.valueOf(Lagrange.interpolate(x,coordinatesX,getCoordinatesY())));
            fifth.setText(String.valueOf(Lagrange.interpolate(x,coordinates9X,getCoordinates9Y())));
            double[] changedCoordY = getCoordinatesY();
            changedCoordY[3] = func.calculate(PI*5/2);
            second.setText(String.valueOf(Lagrange.interpolate(x,coordinatesX,changedCoordY)));
        }
        else{
            third.setText(String.valueOf(Lagrange.interpolate(x,coordinatesNegBigX,getCoordinatesNegBigY())));
            forth.setText(String.valueOf(Lagrange.interpolate(x,coordinatesNegX,getCoordinatesNegY())));
            fifth.setText(String.valueOf(Lagrange.interpolate(x,coordinates9NegX,getCoordinates9NegY())));
            double[] changedCoordY = getCoordinatesY();
            changedCoordY[3] = func.calculate(-PI*3);
            second.setText(String.valueOf(Lagrange.interpolate(x,coordinatesNegX,changedCoordY)));
        }

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
        }
    }
    public void draw(boolean fromZero){
        chart.getData().clear();
        XYChart.Series mainGraph = new XYChart.Series();
        XYChart.Series GraphMainPoints = new XYChart.Series();
        ObservableList<XYChart.Data> mainPoints = FXCollections.observableArrayList();
        if(graph1.isSelected()||graph2.isSelected()||graph3.isSelected()||graph4.isSelected()||graph5.isSelected()) {
            mainPoints.add(new XYChart.Data(0, func.calculate(0)));
            mainPoints.add(new XYChart.Data(PI / 2, func.calculate(PI / 2)));
            mainPoints.add(new XYChart.Data(PI, func.calculate(PI)));
            if(fromZero) {
                mainPoints.add(new XYChart.Data(PI * 3 / 2, func.calculate(PI * 3 / 2)));
                mainPoints.add(new XYChart.Data(PI * 2, func.calculate(PI * 2)));
            }
            else{
                mainPoints.add(new XYChart.Data(-PI / 2, func.calculate(-PI/ 2)));
                mainPoints.add(new XYChart.Data(-PI, func.calculate(-PI)));
            }
            GraphMainPoints.setData(mainPoints);
            chart.getData().add(GraphMainPoints);
        }
        if(graph1.isSelected()) {
            ObservableList<XYChart.Data> PointsOfMainGraph = FXCollections.observableArrayList();
            if (!fromZero)
                for (double i = -2 * PI; i < 2 * PI; i += 0.1) {
                    PointsOfMainGraph.add(new XYChart.Data(i, func.calculate(i)));
                }
            else
                for (double i = 0; i < 14; i += 0.1) {
                    PointsOfMainGraph.add(new XYChart.Data(i, func.calculate(i)));
                }
            mainGraph.setData(PointsOfMainGraph);
            chart.getData().add(mainGraph);
        }
        if(graph2.isSelected()) {
            XYChart.Series Interpolate5points = new XYChart.Series();
            ObservableList<XYChart.Data> Int5Points = FXCollections.observableArrayList();
            if (!fromZero)
                for (double i = -PI - 0.05; i < PI + 0.1; i += 0.1) {
                    Int5Points.add(new XYChart.Data(i, Lagrange.interpolate(i, coordinatesNegX, getCoordinatesNegY())));
                } else
                for (double i = 0; i < 7; i += 0.1) {
                    Int5Points.add(new XYChart.Data(i, Lagrange.interpolate(i, coordinatesX, getCoordinatesY())));
                }
            Interpolate5points.setData(Int5Points);
            chart.getData().add(Interpolate5points);
        }
        if(graph3.isSelected()) {
            XYChart.Series Interpolate9points = new XYChart.Series();
            ObservableList<XYChart.Data> Int9Points = FXCollections.observableArrayList();
            if (!fromZero)
                for (double i = -PI - 0.05; i < PI + 0.1; i += 0.1) {
                    Int9Points.add(new XYChart.Data(i, Lagrange.interpolate(i, coordinates9NegX, getCoordinates9NegY())));
                } else
                for (double i = 0; i < 7; i += 0.1) {
                    Int9Points.add(new XYChart.Data(i, Lagrange.interpolate(i, coordinates9X, getCoordinates9Y())));
                }
            Interpolate9points.setData(Int9Points);
            chart.getData().add(Interpolate9points);
        }
        if(graph4.isSelected()) {
            XYChart.Series InterpolateChangedpoints = new XYChart.Series();
            ObservableList<XYChart.Data> IntChangedPoints = FXCollections.observableArrayList();
            double[] changedCoordNegY = getCoordinatesNegY();
            changedCoordNegY[3] = func.calculate(-PI*3);
            double[] changedCoordY = getCoordinatesY();
            changedCoordY[3] = func.calculate(PI*5/2);
            if (!fromZero)
                for (double i = -PI - 0.05; i < PI + 0.1; i += 0.1) {
                    IntChangedPoints.add(new XYChart.Data(i, Lagrange.interpolate(i, coordinatesNegX, changedCoordNegY)));
                } else
                for (double i = 0; i < 7; i += 0.1) {
                    IntChangedPoints.add(new XYChart.Data(i, Lagrange.interpolate(i, coordinatesX, changedCoordY)));
                }
            InterpolateChangedpoints.setData(IntChangedPoints);
            chart.getData().add(InterpolateChangedpoints);
        }
        if(graph5.isSelected()) {
            XYChart.Series InterpolateBigpoints = new XYChart.Series();
            ObservableList<XYChart.Data> IntBigPoints = FXCollections.observableArrayList();
            if (!fromZero)
                for (double i = -PI*3 - 0.05; i < PI*3 + 0.1; i += 0.1) {
                    IntBigPoints.add(new XYChart.Data(i, Lagrange.interpolate(i, coordinatesNegBigX, getCoordinatesNegBigY())));
                } else
                for (double i = 0; i < PI*6; i += 0.1) {
                    IntBigPoints.add(new XYChart.Data(i, Lagrange.interpolate(i, coordinatesBigX, getCoordinatesBigY())));
                }
            InterpolateBigpoints.setData(IntBigPoints);
            chart.getData().add(InterpolateBigpoints);
        }
    }

}