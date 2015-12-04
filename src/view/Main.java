package view;

import algoritmos.Greedy;
import algoritmos.TwoOpt;
import modelo.Vertice;
import algoritmos.aEstrela;
import dao.GeoDao;
import entidades.Geo;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import modelo.Ponto;
import view.telas.MainScene;
import view.telas.SceneOtimizacao;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class Main extends Application {

	//tela
	Stage stage;
	double largura;
	double altura;

	static final String tituloMainMenu = "Planejamento e Otimizacao de Rotas";	//Planejamento e otimizacao de rotas
	static final String tituloShort = "POtiRô";	//Planejamento e otimizacao de rotas
	Scene mainScene;
	Scene otimizacao;

	@Override
	public void start(Stage primaryStage) {

		stage = primaryStage;

		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage.setX(0);
		stage.setY(0);
		stage.setWidth(bounds.getWidth());
		largura = bounds.getWidth();
		stage.setHeight(bounds.getHeight());
		altura = bounds.getHeight();
		BuildMain();
//		BuildOtimizacao();
		stage.show();
//		aestrela();
//		greedy();
//		twoOpt();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public MenuBar BuildMenuBar() {
		//menubar
		final Menu menuMapa = new Menu("Mapa");
		final MenuItem menuMapaOtimizacao = new MenuItem("Otmização");
		final MenuItem menuMapaRotas = new MenuItem("Rotas");
//		final Menu menuCad = new Menu("Cadastro");
//		final MenuItem menuCadViagens = new MenuItem("Viagens");
//		final MenuItem menuCadMotorista = new MenuItem("Mototorista");
//		final MenuItem menuCadCaminhao = new MenuItem("Caminhões");
//		final MenuItem menuCadRegiao = new MenuItem("Regiões");

		MenuBar m = new MenuBar();
		menuMapaRotas.setOnAction((ActionEvent event) -> {
//			BuildRotas();
		});
		menuMapaOtimizacao.setOnAction((ActionEvent event) -> {
			BuildOtimizacao();
		});
//		menuCadViagens.setOnAction((ActionEvent event) -> {
//			BuildMain();
//			stage.setTitle(tituloMainMenu);
//		});
//		menuCadMotorista.setOnAction((ActionEvent event) -> {
////			stage.setTitle(tituloCadastroMotorista);
////			BuildCadastroMotorista();
//		});
//		menuCadCaminhao.setOnAction((ActionEvent event) -> {
////			stage.setTitle(tituloCadastroCaminhao);
////			BuildCadastroCaminhao();
//		});
//		menuCadRegiao.setOnAction((ActionEvent event) -> {
////			stage.setTitle(tituloCadastroRegiao);
////			BuildCadastroRegiao();
//		});
//		menuCad.getItems().addAll(menuCadViagens, menuCadMotorista, menuCadCaminhao, menuCadRegiao);
		menuMapa.getItems().addAll(menuMapaRotas, menuMapaOtimizacao);

		m.getMenus().addAll(menuMapa);
		return m;
	}

	public void BuildMain() {
		if (mainScene == null) {
			VBox root = new VBox();
			root.getChildren().addAll(BuildMenuBar());
			mainScene = new MainScene(root);
			stage.setScene(mainScene);
		} else {
			stage.setScene(mainScene);
		}
	}

	public void BuildOtimizacao() {
		if (otimizacao == null) {
			VBox root = new VBox();
			root.getChildren().addAll(BuildMenuBar());
			otimizacao = new SceneOtimizacao(root);
			stage.setScene(otimizacao);
		} else {
			stage.setScene(otimizacao);
		}
	}

	private void greedy() {
		System.out.println("-----------------------------greedy----------------------------");
		GeoDao dao = new GeoDao();
		GeoWrapper g = new GeoWrapper(new Geo("ChIJ-cKmDq328JQRXYzuA52tmxk"));
		GeoWrapper ori = dao.getInstance(g);
//		g = new GeoWrapper(new Geo("ChIJixR9NT383pQR3jBrUEmMbSA"));

		LinkedList<GeoWrapper> entregas = new LinkedList<>();
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJj-o7RpBH8JQR0kf8cBFK16E"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJ04NtfX66pQRkZnRVFGbOdA"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJasuwWmp4pQRYSW-TLbVbYQ"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjb_Vp9Vy8JQR5Utnb_w2_Fo"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjcbKbiFa7JQRIEJL3yegMKE"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjRyJIg3V55QRV9I3IrosErM"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJRzkf51_45QRVxiWQXnSSoY"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJixR9NT383pQR3jBrUEmMbSA"))));

		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ6bDLfCsi-5QRSQ3lXBHb4qY"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ6dHK_h3vuZQR1XiACK0QtGA"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ6Qn8Y-W_3pQRPFgs5Oj5zpI"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ6Zx2gAdmGJURWB4MikdlzLI"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ6zyHl4Gg9JQRfPU92M6dUWk"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ73BOuPh335QR4eh5gI_jXFk"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ79zqVCXI45QRg2nZd86s3A4"))));

		List<Vertice> res = Greedy.run(entregas);

		System.out.println("--------------------caminho-----------------");
		System.out.println(res);
	}

	private void twoOpt() {
		System.out.println("-----------------------------twoOpt----------------------------");
		GeoDao dao = new GeoDao();
		GeoWrapper g = new GeoWrapper(new Geo("ChIJ-cKmDq328JQRXYzuA52tmxk"));
		GeoWrapper ori = dao.getInstance(g);
//		g = new GeoWrapper(new Geo("ChIJixR9NT383pQR3jBrUEmMbSA"));

		LinkedList<GeoWrapper> entregas = new LinkedList<>();
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJj-o7RpBH8JQR0kf8cBFK16E"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJ04NtfX66pQRkZnRVFGbOdA"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJasuwWmp4pQRYSW-TLbVbYQ"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjb_Vp9Vy8JQR5Utnb_w2_Fo"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjcbKbiFa7JQRIEJL3yegMKE"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjRyJIg3V55QRV9I3IrosErM"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJRzkf51_45QRVxiWQXnSSoY"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJixR9NT383pQR3jBrUEmMbSA"))));

		TwoOpt a = new TwoOpt(ori, entregas);
		Ponto res = a.run();

		System.out.println("--------------------caminho-----------------");
		Ponto atual = res;
		System.out.println(atual);
		System.out.println("distancia: " + atual.getDistancia());
	}

	private void aestrela() {
		System.out.println("-----------------------------aestrela----------------------------");
		GeoDao dao = new GeoDao();
		GeoWrapper g = new GeoWrapper(new Geo("ChIJ-cKmDq328JQRXYzuA52tmxk"));
		GeoWrapper ori = dao.getInstance(g);
//		g = new GeoWrapper(new Geo("ChIJixR9NT383pQR3jBrUEmMbSA"));
		g = new GeoWrapper(new Geo("ChIJjW9Qn1FwGZURrfniyBKETGI"));
		GeoWrapper des = dao.getInstance(g);

		LinkedList<GeoWrapper> entregas = new LinkedList<>();

		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJj-o7RpBH8JQR0kf8cBFK16E"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJ04NtfX66pQRkZnRVFGbOdA"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJasuwWmp4pQRYSW-TLbVbYQ"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjb_Vp9Vy8JQR5Utnb_w2_Fo"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjcbKbiFa7JQRIEJL3yegMKE"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjRyJIg3V55QRV9I3IrosErM"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJRzkf51_45QRVxiWQXnSSoY"))));
		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJixR9NT383pQR3jBrUEmMbSA"))));

		aEstrela a = new aEstrela(ori, des, entregas);
		Ponto res = a.run();

		System.out.println("--------------------caminho-----------------");
		Ponto atual = res;
		System.out.println(atual);
		System.out.println("distancia: " + atual.getDistancia());
	}

}
