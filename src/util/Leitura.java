package util;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;

/**
 *
 * @author Fernando Spanhol
 *
 * Forcene funções para leitura de arquivos suportados
 *
 */
public class Leitura {

	ImmutableList<String> linhas;
	File arquivo;

	public Leitura() {
		arquivo = null;
	}

	/**
	 * Le arquivo TXT e invoca função para atualizar o banco.
	 *
	 */
	public void lerTxt() {
		FileChooser fc = new FileChooser();
		File f = fc.showOpenDialog(null);
		if (f != null) {
			arquivo = f;
			try {
				linhas = Files.asCharSource(f, Charsets.ISO_8859_1).readLines();
			} catch (IOException ex) {
				Logger.getLogger(Leitura.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void reloadTxt() {
		if (arquivo == null) {
			lerTxt();
		} else {
			try {
				linhas = Files.asCharSource(arquivo, Charsets.ISO_8859_1).readLines();
			} catch (IOException ex) {
				Logger.getLogger(Leitura.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
