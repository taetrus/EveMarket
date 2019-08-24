package com.kk.evemarket.viewpartprovider.table;

import java.text.NumberFormat;
import java.util.Locale;

import org.controlsfx.control.table.TableRowExpanderColumn;

import com.kk.evemarket.common.trade.Trade;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class TableViewExpandable {

	class DoubleTableCell extends TableCell<Trade, Double> {
		@Override
		public void updateItem(Double margin, boolean empty) {
			super.updateItem(margin, empty);
			if (empty) {
				setText(null);
			} else {
				// setText(String.format("%.2f", margin.doubleValue()));
				setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(margin));
			}
		}
	}

	class IntegerTableCell extends TableCell<Trade, Integer> {
		@Override
		public void updateItem(Integer margin, boolean empty) {
			super.updateItem(margin, empty);
			if (empty) {
				setText(null);
			} else {
				setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(margin));
			}
		}
	}

	private ObservableList<Trade> trades;

	public TableViewExpandable(ObservableList<Trade> observableList) {
		trades = observableList;
	}

	@SuppressWarnings("unchecked")
	public Node getTable() {
		TableView<Trade> tableView = new TableView<>();

		TableRowExpanderColumn<Trade> expanderColumn = new TableRowExpanderColumn<>(this::createDetailPane);
		expanderColumn.setResizable(false);
		expanderColumn.setPrefWidth(25);

		TableColumn<Trade, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setPrefWidth(200);

		TableColumn<Trade, Double> volumeColumn = new TableColumn<>("Volume");
		volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
		volumeColumn.setCellFactory(col -> new DoubleTableCell());
		volumeColumn.setPrefWidth(100);

		TableColumn<Trade, Double> marginColumn = new TableColumn<>("Margin");
		marginColumn.setCellValueFactory(new PropertyValueFactory<>("margin"));
		marginColumn.setCellFactory(col -> new DoubleTableCell());
		marginColumn.setPrefWidth(100);

		TableColumn<Trade, Double> profitColumn = new TableColumn<>("Profit");
		profitColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));
		profitColumn.setCellFactory(col -> new DoubleTableCell());
		profitColumn.setPrefWidth(100);

		TableColumn<Trade, Double> buyPriceColumn = new TableColumn<>("Buy Price");
		buyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
		buyPriceColumn.setCellFactory(col -> new DoubleTableCell());
		buyPriceColumn.setPrefWidth(100);

		TableColumn<Trade, Double> sellPriceColumn = new TableColumn<>("Sell Price");
		sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
		sellPriceColumn.setCellFactory(col -> new DoubleTableCell());
		sellPriceColumn.setPrefWidth(100);

		TableColumn<Trade, String> openInGameColumn = new TableColumn<>("Open Market");
		openInGameColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		openInGameColumn.setPrefWidth(100);
		Callback<TableColumn<Trade, String>, TableCell<Trade, String>> cellFactory = new Callback<TableColumn<Trade, String>, TableCell<Trade, String>>() {

			@Override
			public TableCell<Trade, String> call(final TableColumn<Trade, String> param) {
				final TableCell<Trade, String> cell = new TableCell<Trade, String>() {

					final Button btn = new Button("Open in Game");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								Trade trade = getTableView().getItems().get(getIndex());
								String name = trade.getName();
								getTableView().getSelectionModel().select(getIndex());
								TableEventHandler.getEventAdmin().postEvent(TableEventHandler.getOpenInGameEvent(name));
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};

		openInGameColumn.setCellFactory(cellFactory);

		tableView.getColumns().addAll(expanderColumn, nameColumn, volumeColumn, marginColumn, profitColumn,
				buyPriceColumn, sellPriceColumn, openInGameColumn);

		SortedList<Trade> sortedData = new SortedList<>(trades);
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedData);

		tableView.setMaxHeight(Double.MAX_VALUE);

		return tableView;
	}

	private GridPane createDetailPane(TableRowExpanderColumn.TableRowDataFeatures<Trade> param) {
		GridPane editor = new GridPane();
		editor.setPadding(new Insets(10));
		editor.setHgap(10);
		editor.setVgap(5);

		Trade customer = param.getValue();

		TextField nameField = new TextField(customer.getName());
		TextField buyField = new TextField(String.valueOf(customer.getBuyPrice()));

		editor.addRow(0, new Label("Name"), nameField);
		editor.addRow(1, new Label("Buy Price"), buyField);

		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> {
			customer.setName(nameField.getText());
			customer.setBuyPrice(Double.valueOf(buyField.getText()));
			param.toggleExpanded();
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(event -> param.toggleExpanded());

		editor.addRow(2, saveButton, cancelButton);

		return editor;
	}

	// private ObservableList<Trade> getTrades() {
	// return FXCollections.observableArrayList(new Trade("afterburner", 100.0,
	// 12.0, 10000.0, 1000000.0, 1500000.0),
	// new Trade("shield expander", 250.0, 12.0, 10000.0, 1000000.0, 1500000.0),
	// new Trade("adaptive invul", 300.0, 12.0, 10000.0, 1000000.0, 1500000.0),
	// new Trade("drone damage amplifier", 400.0, 12.0, 10000.0, 1000000.0,
	// 1500000.0));
	// }
}