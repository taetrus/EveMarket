package com.kk.evemarket.viewpartprovider2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.evemarket.common.settings.Settings;
import com.kk.evemarket.common.trade.TradeParameters;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ToolbarController {

	@FXML
	private Label lblMinVol;
	@FXML
	private Label lblMinMargin;
	@FXML
	private Label lblMinVal;
	@FXML
	private Label lblFeeTax;
	@FXML
	private Label lblRegion;
	@FXML
	private TextField txtFeeTax;
	@FXML
	private ComboBox<RegionIdName> cmbRegion;
	@FXML
	private Button btnStart;
	@FXML
	private Button btnClear;
	@FXML
	private Button btnAuth;
	@FXML
	GridPane gridPane;
	@FXML
	TextField txtMinVol;
	@FXML
	TextField txtMargin;
	@FXML
	TextField txtMinVal;

	public void initialize() {

		btnStart.setText("Start");
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// Alert alert = new Alert(AlertType.INFORMATION);
				// alert.setTitle("Information Dialog");
				// alert.setHeaderText("Look, an Information Dialog");
				// alert.setContentText("I have a great message for you!");
				// alert.showAndWait();

				TradeParameters parameters = new TradeParameters(Settings.get().getMinVolume(),
						Settings.get().getMinMargin(), Settings.get().getBuyFeeAndTax(), Settings.get().getRegionId());

				ToolbarEventHandler.getEventHandler().postEvent(ToolbarEventHandler.getFindTradesEvent(parameters));

				Platform.runLater(() -> {

					saveSettings();

				});
			}
		});

		btnAuth.setText("Authorize");
		btnAuth.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ToolbarEventHandler.getEventHandler().postEvent(ToolbarEventHandler.getStartAuthEvent());
			}
		});

		btnClear.setText("Clear");
		btnClear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ToolbarEventHandler.getEventHandler().postEvent(ToolbarEventHandler.getClearTradesEvent());

			}
		});

		List<RegionIdName> model = null;

		try {

			URL url = getClass().getResource("regionIdName.json");

			InputStream inputStream = url.openConnection().getInputStream();

			model = (new ObjectMapper()).readValue(inputStream, new TypeReference<List<RegionIdName>>() {
			});

			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		model.sort(null);

		RegionIdName savedRegion = model.stream().filter(r -> r.getRegion_id() == Settings.get().getRegionId())
				.collect(Collectors.toList()).get(0);

		ObservableList<RegionIdName> list = FXCollections.observableArrayList(model);

		cmbRegion.setItems(list);
		cmbRegion.getSelectionModel().select(savedRegion);
		cmbRegion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

			}
		});

		lblMinVal.setText("Min. Value");
		lblFeeTax.setText("Fee & Tax");
		lblMinMargin.setText("Min. Margin");
		lblMinVol.setText("Min. Volume");
		lblRegion.setText("Region");

		txtMinVal.setText(String.valueOf(Settings.get().getMinValue()));
		txtMargin.setText(String.valueOf(Settings.get().getMinMargin()));
		txtFeeTax.setText(String.valueOf(Settings.get().getBuyFeeAndTax()));
		txtMinVol.setText(String.valueOf(Settings.get().getMinVolume()));

	}

	private void saveSettings() {
		try {
			NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

			Settings.get().setMinVolume(Integer.valueOf((txtMinVol.getText())));
			Settings.get().setMinMargin(format.parse(txtMargin.getText()).doubleValue());
			Settings.get().setBuyFeeAndTax(format.parse(txtFeeTax.getText()).doubleValue());
			Settings.get().setRegionId(Integer.valueOf(cmbRegion.getSelectionModel().getSelectedItem().getRegion_id()));
			Settings.get().save();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public GridPane getGridPane() {
		return gridPane;
	}

	public TextField getTxtMinVol() {
		return txtMinVol;
	}

	public TextField getTxtMargin() {
		return txtMargin;
	}

	public TextField getTxtMinVal() {
		return txtMinVal;
	}

	public Label getLblMinVol() {
		return lblMinVol;
	}

	public Label getLblMinMargin() {
		return lblMinMargin;
	}

	public Label getLblMinVal() {
		return lblMinVal;
	}

	public Label getLblFeeTax() {
		return lblFeeTax;
	}

	public Label getLblRegion() {
		return lblRegion;
	}

	public TextField getTxtFeeTax() {
		return txtFeeTax;
	}

	public ComboBox<RegionIdName> getCmbRegion() {
		return cmbRegion;
	}

	public Button getBtnStart() {
		return btnStart;
	}

	public Button getBtnClear() {
		return btnClear;
	}

}
