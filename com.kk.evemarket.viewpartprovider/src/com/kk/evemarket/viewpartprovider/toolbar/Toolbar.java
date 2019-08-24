package com.kk.evemarket.viewpartprovider.toolbar;

import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Toolbar extends GridPane {

	TextField txtMinVol;
	TextField txtMargin;
	TextField txtMinVal;
	TextField txtFeeTax;
	ComboBox<RegionIdName> cmbRegion;

	public Toolbar() {
		super();
		initGui();
	}

	private void initGui() {

		Label lblMinVol = new Label("Min. Volume");
		Label lblMargin = new Label("Min. Margin");
		Label lblMinVal = new Label("Min. Value");
		Label lblFeeTax = new Label("Fee & Tax");
		Label lblRegion = new Label("Region");

		txtMinVol = new TextField();
		txtMinVol.textProperty().set(String.valueOf(txtMinVol.getWidth()));
		txtMargin = new TextField();
		txtMinVal = new TextField();
		txtFeeTax = new TextField();
		txtFeeTax.textProperty().set(String.valueOf(txtFeeTax.getWidth()));
		cmbRegion = new ComboBox<>();

		Button btnClear = new Button("Clear");
		Button btnStart = new Button("Find Trades");
		setHgap(5);
		setVgap(5);
		setStyle("-fx-background-color: yellow;");
		setPadding(new Insets(5, 5, 5, 5));
		setGridLinesVisible(true);

		ColumnConstraints col0 = new ColumnConstraints();
		col0.setHgrow(Priority.NEVER);
		col0.minWidthProperty().set(70);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setHgrow(Priority.ALWAYS);
		col1.minWidthProperty().set(100);
		col1.setFillWidth(true);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setHgrow(Priority.NEVER);
		col2.minWidthProperty().set(50);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setHgrow(Priority.ALWAYS);
		col3.minWidthProperty().set(50);
		col3.setFillWidth(true);
		ColumnConstraints col4 = new ColumnConstraints();
		col4.setHgrow(Priority.ALWAYS);
		col4.minWidthProperty().set(50);
		col4.setFillWidth(true);

		getColumnConstraints().addAll(Arrays.asList(new ColumnConstraints[] { col0, col1, col2, col3, col4 }));

		add(lblMinVol, 0, 0);
		add(lblMargin, 0, 1);
		add(lblMinVal, 0, 2);
		add(lblFeeTax, 2, 0);
		add(lblRegion, 2, 1);

		add(txtMinVol, 1, 0);
		add(txtMargin, 1, 1);
		add(txtMinVal, 1, 2);
		add(txtFeeTax, 3, 0);
		add(cmbRegion, 3, 1);

		add(btnClear, 3, 2);
		add(btnStart, 4, 2);

		GridPane.setFillWidth(btnClear, true);
		GridPane.setFillWidth(btnStart, true);
		GridPane.setFillWidth(cmbRegion, true);
		cmbRegion.setMaxWidth(Double.MAX_VALUE);

		// GridPane.setHgrow(cmbRegion, Priority.ALWAYS);

		GridPane.setColumnSpan(txtFeeTax, 2);
		GridPane.setColumnSpan(cmbRegion, 2);

		txtFeeTax.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				Toolbar.this.txtFeeTax.setText(String.valueOf(arg2));
			}
		});
		txtMinVol.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				Toolbar.this.txtMinVol.setText(String.valueOf(arg2));
			}
		});

	}

}
