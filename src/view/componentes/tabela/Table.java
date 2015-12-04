package view.componentes.tabela;

import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import modelo.CustomDate;
import wrapper.Wrapper;

/**
 * classe TableView para ser reutilizada como um componente. Implementa selecao,
 * duplo clique (com modificadores) e destaque de linhas de acordo com criterios
 * definidos no IdCellFactory
 *
 * @author Fernando Spanhol
 * @param <EntidadeWrapper>
 */
public abstract class Table<EntidadeWrapper extends Wrapper> extends TableView<EntidadeWrapper> {

	ArrayList<TableColumn<EntidadeWrapper, String>> colunas;
	ObservableList<EntidadeWrapper> elementos;

	EntidadeWrapper selecionado;
	SimpleBooleanProperty novaSelecao;
	SimpleBooleanProperty duploClick;
	SimpleBooleanProperty duploClickShift;
	SimpleBooleanProperty duploClickCtrl;
	ObservableList<Integer> idindex;

	ObservableList<Integer> greenInteger;

	public Table(ObservableList<EntidadeWrapper> elementos) {
		super(elementos);
		String css = "/css/estilo_tabela.css";
		getStylesheets().add(css);
		colunas = new ArrayList<>();
		novaSelecao = new SimpleBooleanProperty(false);
		duploClick = new SimpleBooleanProperty(false);
		duploClickShift = new SimpleBooleanProperty(false);
		duploClickCtrl = new SimpleBooleanProperty(false);

		idindex = FXCollections.observableArrayList();
		greenInteger = FXCollections.observableArrayList();

		setEditable(false);
	}

	public SimpleBooleanProperty getNovaSelecao() {
		return novaSelecao;
	}

	public void setNovaSelecao(boolean novaSelecao) {
		this.novaSelecao.set(novaSelecao);
	}

	public EntidadeWrapper getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(EntidadeWrapper selecionado) {
		this.selecionado = selecionado;
	}

	public ObservableList<EntidadeWrapper> getElementos() {
		return elementos;
	}

	public void setElementos(ObservableList<EntidadeWrapper> elementos) {
		this.elementos.clear();
		this.elementos.addAll(elementos);
	}

	public SimpleBooleanProperty getDuploClickProperty() {
		return duploClick;
	}

	public void setDuploClick(boolean duploClick) {
		this.duploClick.setValue(duploClick);
	}

	public SimpleBooleanProperty getDuploClickShiftProperty() {
		return duploClickShift;
	}

	public void setDuploClickShift(boolean duploClickShift) {
		this.duploClickShift.setValue(duploClickShift);
	}

	public SimpleBooleanProperty getDuploClickCtrlProperty() {
		return duploClickCtrl;
	}

	public void setDuploClickCtrl(boolean duploClick) {
		this.duploClickCtrl.setValue(duploClick);
	}

	public ArrayList<TableColumn<EntidadeWrapper, String>> getColunas() {
		return colunas;
	}

	final public Callback<TableColumn<EntidadeWrapper, Integer>, TableCell<EntidadeWrapper, Integer>> getIdCellFactory(String classe) {

		Callback<TableColumn<EntidadeWrapper, Integer>, TableCell<EntidadeWrapper, Integer>> cf
			= (TableColumn<EntidadeWrapper, Integer> param) -> {
				TableCell<EntidadeWrapper, Integer> cell = new TableCell<EntidadeWrapper, Integer>() {

					@Override
					public void updateItem(final Integer item, boolean empty) {
						if (item != null) {
							String str = String.format("%d", item);
							setText(str);
							if (!getStyleClass().contains("alinhadireita")) {
								getStyleClass().add("alinhadireita");
							}
							if (idindex.contains(item)) {
								greenInteger.add(getIndex());
								if (!getStyleClass().contains("fundoverde")) {
									getStyleClass().add("fundoverde");
								}
							}
						} else {
							getStyleClass().remove("fundoverde");
							setText("");
						}
					}
				};
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyMouseEventHandler());
				cell.addEventHandler(KeyEvent.KEY_TYPED, new MyKeyEventHandler());
				return cell;
			};
		return cf;
	}

	final public Callback<TableColumn<EntidadeWrapper, String>, TableCell<EntidadeWrapper, String>> getStringCellFactory(String classe) {

		Callback<TableColumn<EntidadeWrapper, String>, TableCell<EntidadeWrapper, String>> cf
			= (TableColumn<EntidadeWrapper, String> param) -> {
				TableCell<EntidadeWrapper, String> cell = new TableCell<EntidadeWrapper, String>() {

					@Override
					public void updateItem(final String item, boolean empty) {
//						getStyleClass().remove("fundoverde");
						if (item != null && !empty) {
							setText(item);
							if (greenInteger.contains(getIndex())) {
								if (!getStyleClass().contains("fundoverde")) {
									getStyleClass().add("fundoverde");
								}
							}
						} else {
							setText("");
						}
					}
				};
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyMouseEventHandler());
				cell.addEventHandler(KeyEvent.KEY_TYPED, new MyKeyEventHandler());
				return cell;
			};
		return cf;
	}

	final public Callback<TableColumn<EntidadeWrapper, CustomDate>, TableCell<EntidadeWrapper, CustomDate>> getDateCellFactory(String classe) {

		Callback<TableColumn<EntidadeWrapper, CustomDate>, TableCell<EntidadeWrapper, CustomDate>> cf
			= (TableColumn<EntidadeWrapper, CustomDate> param) -> {
				TableCell<EntidadeWrapper, CustomDate> cell = new TableCell<EntidadeWrapper, CustomDate>() {

					@Override
					public void updateItem(final CustomDate item, boolean empty) {
						if (item != null && !empty) {
							setText(item.toString());
//							getStyleClass().remove("fundoverde");
							if (greenInteger.contains(getIndex())) {
								if (!getStyleClass().contains("fundoverde")) {
									getStyleClass().add("fundoverde");
								}
							}
						} else {
							getStyleClass().remove("fundoverde");
							setText("");
						}
					}
				};
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyMouseEventHandler());
				cell.addEventHandler(KeyEvent.KEY_TYPED, new MyKeyEventHandler());
				return cell;
			};
		return cf;
	}

	final public Callback<TableColumn<EntidadeWrapper, Integer>, TableCell<EntidadeWrapper, Integer>> getIntegerCellFactory(String classe) {

		Callback<TableColumn<EntidadeWrapper, Integer>, TableCell<EntidadeWrapper, Integer>> cf
			= (TableColumn<EntidadeWrapper, Integer> param) -> {
				TableCell<EntidadeWrapper, Integer> cell = new TableCell<EntidadeWrapper, Integer>() {

					@Override
					public void updateItem(final Integer item, boolean empty) {
						if (item != null) {
							String str = String.format("%,3d", item);
							setText(str);
//							getStyleClass().remove("fundoverde");
							if (!getStyleClass().contains("alinhadireita")) {
								getStyleClass().add("alinhadireita");
							}
							if (greenInteger.contains(getIndex())) {
								if (!getStyleClass().contains("fundoverde")) {
									getStyleClass().add("fundoverde");
								}
							}
						} else {
							getStyleClass().remove("fundoverde");
							setText("");
						}
					}
				};
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyMouseEventHandler());
				cell.addEventHandler(KeyEvent.KEY_TYPED, new MyKeyEventHandler());
				return cell;
			};
		return cf;
	}

	final public Callback<TableColumn<EntidadeWrapper, Integer>, TableCell<EntidadeWrapper, Integer>> getWholeIntegerCellFactory(String classe) {

		Callback<TableColumn<EntidadeWrapper, Integer>, TableCell<EntidadeWrapper, Integer>> cf
			= (TableColumn<EntidadeWrapper, Integer> param) -> {
				TableCell<EntidadeWrapper, Integer> cell = new TableCell<EntidadeWrapper, Integer>() {

					@Override
					public void updateItem(final Integer item, boolean empty) {
						if (item != null) {
							String str = String.format("%d", item);
							setText(str);
//							getStyleClass().remove("fundoverde");
							if (greenInteger.contains(getIndex())) {
								if (!getStyleClass().contains("fundoverde")) {
									getStyleClass().add("fundoverde");
								}
							}
						} else {
							getStyleClass().remove("fundoverde");
							setText("");
						}
					}
				};
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyMouseEventHandler());
				cell.addEventHandler(KeyEvent.KEY_TYPED, new MyKeyEventHandler());
				return cell;
			};
		return cf;
	}

	final public Callback<TableColumn<EntidadeWrapper, Double>, TableCell<EntidadeWrapper, Double>> getDoubleCellFactory(String classe) {

		Callback<TableColumn<EntidadeWrapper, Double>, TableCell<EntidadeWrapper, Double>> cf
			= (TableColumn<EntidadeWrapper, Double> param) -> {
				TableCell<EntidadeWrapper, Double> cell = new TableCell<EntidadeWrapper, Double>() {

					@Override
					public void updateItem(final Double item, boolean empty) {
						if (item != null) {
							String str = String.format("%,3.2f", item);
							setText(str);
							if (!getStyleClass().contains("alinhadireita")) {
								getStyleClass().add("alinhadireita");
							}
							if (greenInteger.contains(getIndex())) {
								if (!getStyleClass().contains("fundoverde")) {
									getStyleClass().add("fundoverde");
								}
							}
						} else {
							getStyleClass().remove("fundoverde");
							setText("");
						}
					}
				};
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyMouseEventHandler());
				cell.addEventHandler(KeyEvent.KEY_TYPED, new MyKeyEventHandler());
				return cell;
			};
		return cf;
	}

	final public Callback<TableColumn<EntidadeWrapper, Boolean>, TableCell<EntidadeWrapper, Boolean>> getBooleanCellFactory(String classe) {

		Callback<TableColumn<EntidadeWrapper, Boolean>, TableCell<EntidadeWrapper, Boolean>> cf
			= (TableColumn<EntidadeWrapper, Boolean> param) -> {
				TableCell<EntidadeWrapper, Boolean> cell = new TableCell<EntidadeWrapper, Boolean>() {

					@Override
					public void updateItem(final Boolean item, boolean empty) {
						if (item != null && !empty) {
							setText(item ? "Sim" : "Não");
//							getStyleClass().remove("fundoverde");
							if (greenInteger.contains(getIndex())) {
								if (!getStyleClass().contains("fundoverde")) {
									getStyleClass().add("fundoverde");
								}
							}
						} else {
							getStyleClass().remove("fundoverde");
							setText("");
						}
					}
				};
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyMouseEventHandler());
				cell.addEventHandler(KeyEvent.KEY_TYPED, new MyKeyEventHandler());
				return cell;
			};
		return cf;
	}

	class MyMouseEventHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent t) {

			EntidadeWrapper dataRow = (EntidadeWrapper) getSelectionModel().getSelectedItem();
			if (dataRow != null) {
				setSelecionado(dataRow);
				novaSelecao.set(true);	//dispara listener de Cad<T>
			}
			if (t.getClickCount() == 2 && t.getButton() == MouseButton.PRIMARY) {
				if (t.isShiftDown()) {
					duploClickShift.setValue(true);	//dispara listener de Cad<T>
				}
				if (t.isControlDown()) {
					duploClickCtrl.setValue(true);	//dispara listener de Cad<T>
				}
				if (!t.isControlDown() && !t.isShiftDown()) {
					duploClick.setValue(true);	//dispara listener de Cad<T>
				}
			}
		}
	}

	class MyKeyEventHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent t) {
			if (t.getCode() == KeyCode.UP || t.getCode() == KeyCode.DOWN) {
				EntidadeWrapper dataRow = (EntidadeWrapper) getSelectionModel().getSelectedItem();
				if (dataRow != null) {
					setSelecionado(dataRow);
					novaSelecao.set(true);	//dispara listener de Cad<T>
				}
			}
		}
	}

	public void estiloAtivo(TableColumn coluna, String classe) {
		coluna.setMinWidth(55);
		coluna.setMaxWidth(55);
		coluna.setStyle("-fx-alignment: CENTER-LEFT;");
	}

	public void estiloCodigo(TableColumn coluna, String classe) {
		coluna.setMinWidth(55);
		coluna.setMaxWidth(55);
		coluna.setStyle("-fx-alignment: CENTER-RIGHT;");
	}

	public void estiloDouble(TableColumn coluna, String classe) {
		coluna.setMinWidth(75);
		coluna.setMaxWidth(75);
		coluna.setStyle("-fx-alignment: CENTER-RIGHT;");
	}

	public void estiloData(TableColumn coluna, String classe) {
		coluna.setMinWidth(80);
		coluna.setMaxWidth(80);
		coluna.setStyle("-fx-alignment: CENTER;");
	}

	public ObservableList<Integer> getGreenInteger() {
		return greenInteger;
	}

	public void setGreenInteger(ObservableList<Integer> green) {
		this.greenInteger = green;
	}

	public ObservableList<Integer> getIdindex() {
		return idindex;
	}

	public void setIdindex(ObservableList<Integer> idindex) {
		this.idindex = idindex;
	}
}
