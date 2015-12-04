package view;

import javafx.beans.property.StringProperty;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Mostra janela popup do tipo selecionado
 *
 * @author Fernando Spanhol
 */
public class ModalDialog {

	Button ok;
	Button sim;
	Button nao;
	Button cancelar;
	Resposta resposta;
	Stage stage;

	public enum Resposta {

		cancelar,
		ok,
		sim,
		nao;
	}

	public enum Tipo {

		processando,
		mensagem,
		simnao,
		simnaocancelar;
	}

	public ModalDialog(final Stage stg, Tipo t, StringProperty mensagem) {
		resposta = Resposta.cancelar;
		ok = new Button("OK");
		ok.setPrefWidth(80);
		sim = new Button("Sim");
		sim.setPrefWidth(80);
		nao = new Button("Não");
		nao.setPrefWidth(80);
		cancelar = new Button("Cancelar");
		cancelar.setPrefWidth(80);

		Image warning = new Image(getClass().getResourceAsStream("warning.png"));
		ImageView warningView = new ImageView(warning);
		warningView.setPreserveRatio(true);

		Image loading = new Image(getClass().getResourceAsStream("loading.gif"));
		ImageView loadingView = new ImageView(loading);
		loadingView.setPreserveRatio(true);
		loadingView.setFitHeight(50);
		Label mens = new Label(mensagem.get(), warningView);

		if (t == Tipo.processando) {
			mens = new Label(mensagem.get(), loadingView);
		}

		mens.textProperty().bind(mensagem);

		stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(stg);
		stage.setOnShowing((WindowEvent event) -> {
			resposta = Resposta.cancelar;
		});

		VBox root = new VBox();
		HBox m = new HBox();
		m.setPadding(new Insets(20));
		m.setAlignment(Pos.BASELINE_CENTER);
		HBox botao = new HBox();
		botao.setSpacing(10);
		botao.setPadding(new Insets(20));
		botao.setAlignment(Pos.BASELINE_CENTER);

		m.getChildren().addAll(mens);
		m.setPrefWidth(350);

		root.getChildren().addAll(m, botao);
		Scene scene = new Scene(root);

		ok.setOnAction((ActionEvent event) -> {
			resposta = Resposta.ok;
			stage.hide();
		});
		sim.setOnAction((ActionEvent event) -> {
			resposta = Resposta.sim;
			stage.hide();
		});
		nao.setOnAction((ActionEvent event) -> {
			resposta = Resposta.nao;
			stage.hide();
		});
		cancelar.setOnAction((ActionEvent event) -> {
			resposta = Resposta.cancelar;
			stage.hide();
		});
		if (t == Tipo.mensagem) {
			botao.getChildren().addAll(ok);
		} else if (t == Tipo.simnao) {
			botao.getChildren().addAll(sim, nao);
		} else if (t == Tipo.simnaocancelar) {
			botao.getChildren().addAll(sim, nao, cancelar);
		} else if (t == Tipo.processando) {
			botao.getChildren().addAll();
		}
//		if (t == Tipo.processando) {
		stage.setScene(scene);
//			stage.show();
//		} else {
//			stage.setScene(scene);
//			stage.showAndWait();
//		}
	}

	public ModalDialog(final Stage stg, Tipo t, String mensagem) {
		resposta = Resposta.cancelar;
		ok = new Button("OK");
		ok.setPrefWidth(80);
		sim = new Button("Sim");
		sim.setPrefWidth(80);
		nao = new Button("Não");
		nao.setPrefWidth(80);
		cancelar = new Button("Cancelar");
		cancelar.setPrefWidth(80);

		Image warning = new Image(getClass().getResourceAsStream("warning.png"));
		ImageView warningView = new ImageView(warning);
		warningView.setPreserveRatio(true);

		Image loading = new Image(getClass().getResourceAsStream("loading.gif"));
		ImageView loadingView = new ImageView(loading);
		loadingView.setPreserveRatio(true);
		loadingView.setFitHeight(50);
		Label mens = new Label(mensagem, warningView);

		if (t == Tipo.processando) {
			mens = new Label(mensagem, loadingView);
		}

		stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(stg);
		stage.setOnShowing((WindowEvent event) -> {
			resposta = Resposta.cancelar;
		});

		VBox root = new VBox();
		HBox m = new HBox();
		m.setPadding(new Insets(20));
		m.setAlignment(Pos.BASELINE_CENTER);
		HBox botao = new HBox();
		botao.setSpacing(10);
		botao.setPadding(new Insets(20));
		botao.setAlignment(Pos.BASELINE_CENTER);

		m.getChildren().addAll(mens);
		m.setPrefWidth(350);

		root.getChildren().addAll(m, botao);
		Scene scene = new Scene(root);

		ok.setOnAction((ActionEvent event) -> {
			resposta = Resposta.ok;
			stage.hide();
		});
		sim.setOnAction((ActionEvent event) -> {
			resposta = Resposta.sim;
			stage.hide();
		});
		nao.setOnAction((ActionEvent event) -> {
			resposta = Resposta.nao;
			stage.hide();
		});
		cancelar.setOnAction((ActionEvent event) -> {
			resposta = Resposta.cancelar;
			stage.hide();
		});
		if (t == Tipo.mensagem) {
			botao.getChildren().addAll(ok);
		} else if (t == Tipo.simnao) {
			botao.getChildren().addAll(sim, nao);
		} else if (t == Tipo.simnaocancelar) {
			botao.getChildren().addAll(sim, nao, cancelar);
		} else if (t == Tipo.processando) {
			botao.getChildren().addAll();
		}
		if (t == Tipo.processando) {
			stage.setScene(scene);
			stage.show();
		} else {
			stage.setScene(scene);
			stage.showAndWait();
		}
	}

	public Resposta getResposta() {
		return resposta;
	}

	public void hide() {
		stage.hide();
	}

	public void show() {
		stage.show();
	}

	public void showAndWait() {
		stage.showAndWait();
	}
}
