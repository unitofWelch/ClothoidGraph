package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import javafx.scene.shape.Box;

import javax.swing.*;

public class Controller {
    @FXML TextField factorA; @FXML TextField factorB; @FXML TextField factorC; @FXML TextField factorD;
    @FXML TextField xMin; @FXML TextField xMax;

    @FXML
    Button button;
    @FXML
    LineChart<Number, Number> chart;
    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML Slider limitSlider; @FXML Slider stepSlider; @FXML Slider SSlider; @FXML Slider CSlider; @FXML Slider DSlider;
    @FXML Label lbl_step; @FXML Label lbl_S; @FXML Label lbl_C; @FXML Label lbl_D; @FXML Label lbl_Min; @FXML Label lbl_Max;
    @FXML CheckBox cb_symb; @FXML CheckBox cb_pol; @FXML CheckBox cb_anim;
    @FXML Box anim_box;

//    // final?
//    NumberAxis xAxis = new NumberAxis();
//    NumberAxis yAxis = new NumberAxis();
//    LineChart<Number,Number> chart = new LineChart<Number,Number>(xAxis,yAxis);
//    TextField factorA = new TextField("0.1");
//    TextField factorB = new TextField("10.0");
//    TextField factorC = new TextField("");
//    TextField xMin = new TextField("-10.0");
//    TextField xMax = new TextField("10.0");
//    //

    @FXML
    public void updateChart() {
        XYChart.Series<Number, Number> series = getSeries();
        chart.getData().clear();
        chart.getData().add(series);

        if (cb_symb.isSelected()) chart.setCreateSymbols(true); else if (!cb_symb.isSelected()) chart.setCreateSymbols(false);
    }

    public void initialize(){
        initChartProperties();
        initInputControls();
        XYChart.Series<Number, Number> series = getSeries();
        chart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        chart.getData().add(series);

        // 5B5B5B // Elephant 16 //
        lbl_step.setText("Step:"); lbl_step.setTextFill(Color.web("#757575")); lbl_step.setFont(new Font("Arial Bold", 16));
        lbl_S.setText("S:"); lbl_S.setTextFill(Color.web("#757575")); lbl_S.setFont(new Font("Arial Bold", 16));
        lbl_C.setText("C:"); lbl_C.setTextFill(Color.web("#757575")); lbl_C.setFont(new Font("Arial Bold", 16));
        lbl_D.setText("D:"); lbl_D.setTextFill(Color.web("#757575")); lbl_D.setFont(new Font("Arial Bold", 16));
        lbl_Min.setText("Mn:"); lbl_Min.setTextFill(Color.web("#757575")); lbl_Min.setFont(new Font("Arial Bold", 16));
        lbl_Max.setText("Mx:"); lbl_Max.setTextFill(Color.web("#757575")); lbl_Max.setFont(new Font("Arial Bold", 16));

        limitSlider.setOrientation(Orientation.VERTICAL); limitSlider.setShowTickLabels(true); limitSlider.setShowTickMarks(true); limitSlider.setMajorTickUnit(24); limitSlider.setBlockIncrement(1); limitSlider.setMax(50); limitSlider.setValue(Double.parseDouble(xMax.getText())); limitSlider.setMin(0.2);
        limitSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){
                xMin.setText(String.valueOf(-(double)newValue)); xMax.setText(String.valueOf(newValue));
            }
        });
        stepSlider.setShowTickLabels(true); stepSlider.setShowTickMarks(true); stepSlider.setMajorTickUnit(0.5); stepSlider.setBlockIncrement(0.05); stepSlider.setMax(1); stepSlider.setValue(Double.parseDouble(factorA.getText()));
        stepSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){
                factorA.setText(String.valueOf(newValue));
            }
        });
        SSlider.setShowTickLabels(true); SSlider.setShowTickMarks(true); SSlider.setMajorTickUnit(25); SSlider.setBlockIncrement(5); SSlider.setMax(50); SSlider.setValue(Double.parseDouble(factorB.getText())); SSlider.setMin(0.1);
        SSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){
                factorB.setText(String.valueOf(newValue));
            }
        });
        CSlider.setShowTickLabels(true); CSlider.setShowTickMarks(true); CSlider.setMajorTickUnit(4); CSlider.setBlockIncrement(0.5); CSlider.setMax(10); CSlider.setValue(Double.parseDouble(factorC.getText())); CSlider.setMin(0.1);
        CSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){
                factorC.setText(String.valueOf(newValue));
            }
        });
        DSlider.setShowTickLabels(true); DSlider.setShowTickMarks(true); DSlider.setMajorTickUnit(4); DSlider.setBlockIncrement(0.5); DSlider.setMax(10); DSlider.setValue(Double.parseDouble(factorD.getText())); DSlider.setMin(0.1);
        DSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){
                factorD.setText(String.valueOf(newValue));
            }
        });

        cb_symb.setSelected(true); cb_symb.setText("Draw symbols");
        cb_pol.setSelected(false); cb_pol.setText("Draw polar");
        cb_anim.setSelected(false); cb_anim.setText("Animate");

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DODGERBLUE);
        //redMaterial.setSpecularColor(Color.BLUE);

        anim_box.setVisible(true); anim_box.setMaterial(redMaterial);

    }

    private XYChart.Series<Number, Number> getSeries() {

        System.out.println("Cartesian");

        double xMax1 = Double.parseDouble(xMax.getText());
        double xMin1 = Double.parseDouble(xMin.getText());
        double step = Double.parseDouble(factorA.getText());
        double b = Double.parseDouble(factorB.getText());
        double c = Double.parseDouble(factorC.getText());
        double d = Double.parseDouble(factorD.getText());

        XYChart.Series<Number,Number> series = new XYChart.Series<Number, Number>();
        series.setName("Chart");

        double x_temp = 0, y_temp = 0; int i = 0;
        double pol_sin = 0, pol_cos = 0, pol_rds = 0, pol_angle = 10;

        double left = xMin1, right = xMax1;

        if (cb_pol.isSelected()) {

            left = 0; right = 2 * Math.PI;

        }

        for (double a = left; a < right; a += step) {

            i++;

            x_temp = //(Math.pow(S, 3) / (6 * C)) - (Math.pow(S, 7) / (336 * Math.pow(C, 3)));
                    c * (a - Math.pow(a, 5) / (1 * 2 * 5 * Math.pow(b, 4)) + Math.pow(a, 9) / (1 * 2 * 3 * 4 * 9 * Math.pow(b, 8)) - Math.pow(a, 13) / (1 * 2 * 3 * 4 * 5 * 6 * 13 * Math.pow(b, 12)));
                    //Math.signum(a) * Math.abs(a) * Math.pow(b, 1.0/2.0) * Math.cos(b);

            y_temp = //S - (Math.pow(S, 5) / (40 * Math.pow(C, 2)));
                    d * (Math.pow(a, 3) / (1 * 3 * Math.pow(b, 2)) - Math.pow(a,7) / (1 * 2 * 3 * 7 * Math.pow(b, 6)) + Math.pow(a, 11) / (1 * 2 * 3 * 4 * 5 * 11 * Math.pow(b, 10)) - Math.pow(a, 15) / (1 * 2 * 3 * 4 * 5 * 6 * 7 * 15 * Math.pow(b, 14)));
                    //Math.signum(a) * Math.abs(a) * Math.pow(b, 1.0/2.0) * Math.sin(b);

            if (cb_pol.isSelected()) {

                pol_rds = Math.sqrt(Math.pow(x_temp, 2) + Math.pow(y_temp, 2));

                x_temp = pol_rds * Math.cos(a);
                y_temp = pol_rds * Math.sin(a);

            }

//            if (cb_anim.isSelected()) {
//
//                anim_box.setTranslateX(x_temp);
//                anim_box.setTranslateY(y_temp);
//
//            }
//            else if (!cb_anim.isSelected()) {
//
//                anim_box.relocate(-100.0, -100.0);
//
//            }

            System.out.println("[" + i + "]: " + "x_temp is " + x_temp + "; y_temp is " + y_temp);

            series.getData().add(new XYChart.Data(x_temp, y_temp));

        }

        return series;
    }

    private void initInputControls() {
        xMax.setText("10.0");
        xMin.setText("-10.0");
        factorA.setText("0.25");
        factorB.setText("10.0");
        factorC.setText("1.0");
        factorD.setText("1.0");
    }

    private void initChartProperties() {
        chart.setAnimated(true);
        xAxis.setLabel("X");
        yAxis.setLabel("Y");
        //chart.setCreateSymbols(false);
    }

}