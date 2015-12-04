/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Calendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 *
 * @author Fernando Spanhol
 */
public class InterfaceFactory {

	/**
	 * Gera um botao com as caracteristicas passadas por parametro
	 *
	 * @param imageView - icone ou animacao do botao
	 * @param size - Tamanho do botao
	 * @param nome
	 * @param toolTip
	 * @return Nova instancia de Button.
	 */
	public static Button getButton(ImageView imageView, int size, String nome, String toolTip) {
		if (nome == null) {
			nome = "";
		}
		Button b = new Button(nome, imageView);
		b.setPrefWidth(32);
		if (toolTip != null) {
			b.setTooltip(new Tooltip(toolTip));
		}
		return b;
	}

	public static TextField getTextFieldDate() {
		TextField tf = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				ArrayList<String> reg = new ArrayList<>();
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[/]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[/]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]?");
				reg.add("[0-9]?");
				if (getCaretPosition() >= reg.size()) {
					return;
				}
				if (text.matches(reg.get(getCaretPosition())) || text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					if (end == 1) {
						super.replaceText(2, 2, "/");
						positionCaret(3);
					}
					if (end == 4) {
						super.replaceText(5, 5, "/");
						positionCaret(6);
					}
				}
				if (super.getText().length() > reg.size()) {
					int caret = getCaretPosition();
					super.setText(super.getText().substring(0, caret));
					super.positionCaret(caret);
				}
			}

			@Override
			public void replaceSelection(String text) {
				String reg = "[0-9]{0,2}/?[0-9]{0,2}/?[0-9]{0,4}";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceSelection(text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}
		};
		tf.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
			if (!newPropertyValue) {
				if (tf.getText().matches("[0-9][0-9]/[0-9][0-9]/?")) {
					tf.setText(tf.getText().substring(0, 5) + "/" + Calendar.getInstance().get(Calendar.YEAR));
				}
				if (tf.getText().matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9]")) {
					tf.setText(tf.getText().substring(0, 6) + "20" + tf.getText().substring(6, 8));
				}
				if (tf.getText().matches("[0-9][0-9][0-9][0-9][0-9][0-9]")) {
					String str = tf.getText().substring(0, 2);
					str = str + "/" + tf.getText().substring(2, 4);
					str = str + "/20" + tf.getText().substring(4, 6);
					tf.setText(str);
				}
				if (tf.getText().matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]")) {
					String str = tf.getText().substring(0, 2);
					str = str + "/" + tf.getText().substring(2, 4);
					str = str + "/" + tf.getText().substring(4, 8);
					tf.setText(str);
				}
			}
		});
		return tf;
	}

	public static TextField getTextFieldRG() {
		return new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				ArrayList<String> reg = new ArrayList<>();
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[-]");
				reg.add("[0-9]");
				if (getCaretPosition() >= reg.size()) {
					return;
				}
				if (text.matches(reg.get(getCaretPosition())) || text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					if (end == 6) {
						super.replaceText(7, 7, "-");
						positionCaret(8);
					}
				}
				if (super.getText().length() > reg.size()) {
					int caret = getCaretPosition();
					super.setText(super.getText().substring(0, caret));
					super.positionCaret(caret);
				}
			}

			@Override
			public void replaceSelection(String text) {
				String reg = "[0-9]{0,7}-?[0-9]?";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceSelection(text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}

		};
	}

	public static TextField getTextFieldCPF() {
		return new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				ArrayList<String> reg = new ArrayList<>();
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[.]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[.]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[-]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				if (getCaretPosition() >= reg.size()) {
					return;
				}
				if (text.matches(reg.get(getCaretPosition())) || text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					if (end == 2) {
						super.replaceText(3, 3, ".");
						positionCaret(4);
					}
					if (end == 6) {
						super.replaceText(7, 7, ".");
						positionCaret(8);
					}
					if (end == 10) {
						super.replaceText(11, 11, "-");
						positionCaret(12);
					}
				}
				if (super.getText().length() > reg.size()) {
					int caret = getCaretPosition();
					super.setText(super.getText().substring(0, caret));
					super.positionCaret(caret);
				}
			}

			@Override
			public void replaceSelection(String text) {

				String reg = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceSelection(text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}
		};
	}

	public static TextField getTextFieldTelefone() {
		return new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				ArrayList<String> reg = new ArrayList<>();
				reg.add("[a-zA-Z]");
				reg.add("[a-zA-Z]");
				reg.add("[a-zA-Z]");
				reg.add("-");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				if (getCaretPosition() >= reg.size()) {
					return;
				}
				if (text.matches(reg.get(getCaretPosition())) || text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					if (end == 2) {
						super.replaceText(3, 3, "-");
						positionCaret(4);
					}
				}
				if (super.getText().length() > reg.size()) {
					int caret = getCaretPosition();
					super.setText(super.getText().substring(0, caret));
					super.positionCaret(caret);
				}
			}

			@Override
			public void replaceSelection(String text) {
				String reg = "[a-zA-Z][a-zA-Z][a-zA-Z]-[0-9][0-9][0-9][0-9]";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceSelection(text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}
		};
	}

	public static TextField getTextFieldMoney() {
		String reg = "[\\d*\\.*\\d*]*,?\\d?\\d?";
		return getTextField("", reg);
	}

	public static TextField getTextFieldInteger() {
		String reg = "\\d*";
		return getTextField("", reg);
	}

	public static TextField getTextField(String text, ArrayList<String> regExConteudo) {
		TextField tf = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				if (getCaretPosition() >= regExConteudo.size()) {
					return;
				}
				if (text.matches(regExConteudo.get(getCaretPosition())) || text.compareTo("") == 0) {
					super.replaceText(start, end, text);
				}
				if (super.getText().length() > regExConteudo.size()) {
					int caret = getCaretPosition();
					super.setText(super.getText().substring(0, caret));
					super.positionCaret(caret);
				}
			}
		};
		tf.setText(text);
		return tf;
	}

	public static TextField getTextField(String text, String regExConteudo) {
		TextField tf = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceText(start, end, text);
				if (!super.getText().matches(regExConteudo) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}
		};
		tf.setText(text);
		return tf;
	}

	public static TableColumn getColumn(String titulo, PropertyValueFactory propertyValueFactory, Callback cellFactory) {
		TableColumn tc = new TableColumn<>(titulo);
		tc.setCellValueFactory(propertyValueFactory);
		tc.setCellFactory(cellFactory);
		return tc;
	}
}
