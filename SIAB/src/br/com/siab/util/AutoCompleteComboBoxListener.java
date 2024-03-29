package br.com.siab.util;

import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AutoCompleteComboBoxListener<T>{

	private ComboBox<T> cmb;
	String filter = "";
	private ObservableList<T> originalItems;

	public AutoCompleteComboBoxListener(ComboBox<T> cmb) {
		this.cmb = cmb;
		originalItems = FXCollections.observableArrayList(cmb.getItems());
//		cmb.setTooltip(new Tooltip());
		cmb.setOnKeyPressed(this::handleOnKeyPressed);
		cmb.setOnHidden(this::handleOnHiding);
	}

	public void handleOnKeyPressed(KeyEvent e) {
		ObservableList<T> filteredList = FXCollections.observableArrayList();
		KeyCode code = e.getCode();

		if (code.isLetterKey()) {
			filter += e.getText();
		}
		if (code == KeyCode.BACK_SPACE && filter.length() > 0) {
			filter = filter.substring(0, filter.length() - 1);
			cmb.getItems().setAll(originalItems);
		}
		if (code == KeyCode.ESCAPE) {
			filter = "";
		}
		if (filter.length() == 0) {
			filteredList = originalItems;
//			cmb.getTooltip().hide();
		} else {
			Stream<T> itens = cmb.getItems().stream();
			String txtUsr = filter.toString().toLowerCase();
			itens.filter(el -> el.toString().toLowerCase().contains(txtUsr)).forEach(filteredList::add);
//			cmb.getTooltip().setText(txtUsr);
//			Point2D p = cmb.localToScene(0.0, 0.0);

//			double posX = p.getX() + cmb.getScene().getX() + cmb.getScene().getWindow().getX();
//			double posY = p.getY() + cmb.getScene().getY() + cmb.getScene().getWindow().getY();
//			cmb.getTooltip().show(cmb, posX, posY);
			cmb.show();
		}
		cmb.getItems().setAll(filteredList);
	}

	public void handleOnHiding(Event e) {
		filter = "";
//		cmb.getTooltip().hide();
		T s = cmb.getSelectionModel().getSelectedItem();
		if(s == null){
			cmb.getSelectionModel().clearSelection();
		}else{
			cmb.getItems().setAll(originalItems);
			cmb.getSelectionModel().select(s);
		}
	}


}