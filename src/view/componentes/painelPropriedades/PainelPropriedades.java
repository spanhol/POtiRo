package view.componentes.painelPropriedades;

import dao.Dao;
import dao.InterfaceDao;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import view.ModalDialog;
import wrapper.Wrapper;

/**
 * Elemento VBox para ser reutilizado na construção de telas.
 *
 * Implementa funcionalidades dos botoes novo, salvar, atualizar e deletar sobre
 * um Objeto selecionado, utilizando a Propriedade dirty para indicar que algum
 * objeto foi modificado.
 *
 * @author Fernando Spanhol
 * @param <DAO>
 * @param <EntidadeWrapper>
 */
public abstract class PainelPropriedades<DAO extends InterfaceDao, EntidadeWrapper extends Wrapper> extends VBox {

	enum EstadoBotoes {

		NOVO,
		ATUALIZAR;
	};
	EstadoBotoes estado = EstadoBotoes.NOVO;
	HBox botoes;
	Button novo;
	Button salvar;
	Button atualizar;
	Button deletar;

	ArrayList<TextField> campos;
	ArrayList<Node> ordemDefoco;

	ArrayList<Date> datas;
	BooleanProperty dirty;
	String filtro;
	InterfaceDao<EntidadeWrapper> dao;
	EntidadeWrapper selecionado;

	public PainelPropriedades(DAO dao) {
		String css = "/css/estilo_painel.css";
		getStylesheets().add(css);
		setBackground(new Background(new BackgroundFill(Paint.valueOf("white"), CornerRadii.EMPTY, Insets.EMPTY)));
		setMinWidth(220);
		setPadding(new Insets(10, 10, 10, 10));
		setSpacing(4);
		CriarBotoes();
		dirty = new SimpleBooleanProperty(false);
		this.dao = dao;
		addEventos();
		filtro = "";
	}

	final public HBox bigLabelBox(String text, Label node) {
		HBox re = new HBox();
		Label l = new Label(text);
		l.setPadding(new Insets(0, 5, 0, 0));
		node.setMinWidth(200);
		node.setMaxWidth(200);
		re.getChildren().addAll(l, node);
		re.getStyleClass().add("biglabel");
		return re;
	}

	final public HBox labelBox(String text, Label node) {
		HBox re = new HBox();
		Label l = new Label(text);
		l.setPadding(new Insets(0, 5, 0, 0));
		l.setMinWidth(70);
		l.setMaxWidth(70);
		node.setMinWidth(65);
		node.setMaxWidth(65);
		re.getChildren().addAll(l, node);
		re.getStyleClass().add("lbbox");
		node.getStyleClass().add("lb");
		l.getStyleClass().add("lbfor");
		return re;
	}

	final public HBox box(String label, Control node) {
		HBox re = new HBox();
		Label l = new Label(label);
		l.setPadding(new Insets(0, 5, 0, 0));
		node.setMinWidth(130);
		node.setMaxWidth(130);
		re.getChildren().addAll(l, node);
		re.getStyleClass().add("campo");
		return re;
	}

	final public HBox CriarBotoes() {
		botoes = new HBox();
		Image imgnovo = new Image(getClass().getResourceAsStream("novo.png"));
		ImageView novoView = new ImageView(imgnovo);
		novoView.setPreserveRatio(true);
		novoView.setFitHeight(32);
		Image imgAtualizar = new Image(getClass().getResourceAsStream("salvar.png"));
		ImageView salvarView = new ImageView(imgAtualizar);
		salvarView.setPreserveRatio(true);
		salvarView.setFitHeight(32);
		Image imgDelete = new Image(getClass().getResourceAsStream("deletar.png"));
		ImageView deleteView = new ImageView(imgDelete);
		deleteView.setPreserveRatio(true);
		deleteView.setFitHeight(32);
		Image imgEditar = new Image(getClass().getResourceAsStream("atualizar.png"));
		ImageView atualizarView = new ImageView(imgEditar);
		atualizarView.setPreserveRatio(true);
		atualizarView.setFitHeight(32);
		novo = new Button("", novoView);
		salvar = new Button("", salvarView);
		atualizar = new Button("", atualizarView);
		deletar = new Button("", deleteView);
		novo.setPrefWidth(32);
		salvar.setPrefWidth(32);
		atualizar.setPrefWidth(32);
		deletar.setPrefWidth(32);
		deletar.setDisable(true);

		novo.setTooltip(new Tooltip("Limpar campos"));
		salvar.setTooltip(new Tooltip("Criar novo registro"));
		atualizar.setTooltip(new Tooltip("Salvar modificações"));
		deletar.setTooltip(new Tooltip("Deletar registro"));

		botoes.getChildren().addAll(novo, salvar, deletar);
		botoes.getStyleClass().add("botoes");
		botoes.setPadding(new Insets(0, 0, 0, 0));

		novo.setOnAction((ActionEvent event) -> {
			setEstadoBotoesNovo();
		});

		salvar.setOnAction((ActionEvent event) -> {
			EntidadeWrapper e = valida(false);
			if (e != null) {
				limpaErro();
				if (!dao.criar(e)) {
					JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel salvar.");
					return;
				} else {
					setPropriedades(selecionado);
				}
				setEstadoBotoesEditar();
				limpaErro();
				dirty.set(true);
			}
		});

		atualizar.setOnAction((ActionEvent event) -> {
			EntidadeWrapper e = valida(true);
			if (e != null) {
				if (!dao.atualizar(e)) {
					JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel atualizar.");
					setPropriedades(null);
				} else {
					clearCache();
					setPropriedades(selecionado);
				}
				limpaErro();
				limpaAuto();
				dirty.set(true);	//dispara listener em CadCaminhao
			}
		});

		deletar.setOnAction((ActionEvent event) -> {
			EntidadeWrapper e = valida(true);
			String tipo = " o registro";
			if (e != null) {
				limpaErro();
				ModalDialog.Resposta resposta;
				ModalDialog modal = new ModalDialog(new Stage(), ModalDialog.Tipo.simnao, "Tem certeza que deseja deletar este item?");
				resposta = modal.getResposta();

				if (resposta == ModalDialog.Resposta.sim) {
					if (!dao.destruir(e)) {
						JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel deletar.");
						return;
					}
					setEstadoBotoesNovo();
					dirty.set(true);
				} else {
					System.out.println("nao foi deletado");
				}
			}
		});
		return botoes;
	}

	public void setEstadoBotoesNovo() {
		selecionado = null;
		estado = EstadoBotoes.NOVO;
		limpaTexto();
		limpaErro();
		limpaAuto();
		botoes.getChildren().retainAll();
		deletar.setDisable(true);
		botoes.getChildren().addAll(novo, salvar, deletar);
	}

	public void setEstadoBotoesEditar() {
		estado = EstadoBotoes.ATUALIZAR;
		botoes.getChildren().retainAll();
		deletar.setDisable(false);
		botoes.getChildren().addAll(novo, atualizar, deletar);
	}

	final public void limpaTexto() {
		campos.stream().forEach((campo) -> {
			campo.setText("");
			campo.getStyleClass().remove("erro");
		});
		limpaOutros();
	}

	final public void limpaAuto() {
		campos.stream().forEach((campo) -> {
			campo.getStyleClass().remove("auto");
			campo.getStyleClass().remove("dif");
		});
	}

	final public void limpaErro() {
		campos.stream().forEach((campo) -> {
			campo.getStyleClass().remove("erro");
		});
		limpaOutrosErros();
	}

	public BooleanProperty getDirty() {
		return dirty;
	}

	public void setDirty(boolean bool) {
		dirty.set(bool);
	}

	public String getFiltroData() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public abstract void limpaOutros();

	public abstract void limpaOutrosErros();

	public abstract void setPropriedades(EntidadeWrapper e);

	public abstract EntidadeWrapper valida(boolean validaID);

	private void addEventos() {
		try {
			addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				Robot eventRobot = new Robot();

				@Override
				public void handle(KeyEvent KV) {
					switch (KV.getCode()) {
						case ENTER:
							if ((KV.getTarget() instanceof TextField) || (KV.getTarget() instanceof ComboBox)) {
								eventRobot.keyPress(java.awt.event.KeyEvent.VK_TAB);
								eventRobot.keyRelease(java.awt.event.KeyEvent.VK_TAB);
								KV.consume();
							}
							break;
						case F10:
							if (estado == EstadoBotoes.ATUALIZAR) {
								EntidadeWrapper e = valida(true);
								if (e != null) {
									if (!dao.atualizar(e)) {
										JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel atualizar.");
										return;
									} else {
										setPropriedades(e);
									}
									limpaErro();
									limpaAuto();
									dirty.set(true);	//dispara listener em CadCaminhao
								}
							} else if (estado == EstadoBotoes.NOVO) {
								EntidadeWrapper e = valida(false);
								if (e != null) {
									limpaErro();
									if (!dao.criar(e)) {
										JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel salvar.");
										return;
									} else {
										setPropriedades(e);
									}
									setEstadoBotoesEditar();
									limpaErro();
									dirty.set(true);
								}
							}
							KV.consume();
							botoes.getChildren().get(0).requestFocus();
							break;
					}
				}
			});
		} catch (AWTException ex) {
			Logger.getLogger(PainelPropriedades.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public ArrayList<Date> getDatas() {
		return datas;
	}

	public void setDatas(ArrayList<Date> datas) {
		this.datas = datas;
	}

	public void clearCache() {
		Dao.ClearCache();
		dirty.set(true);
	}

	public EntidadeWrapper getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(EntidadeWrapper selecionado) {
		this.selecionado = selecionado;
	}
}
