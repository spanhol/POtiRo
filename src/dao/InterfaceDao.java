package dao;

import javafx.collections.ObservableList;
import wrapper.Wrapper;

/**
 *
 * @author Fernando Spanhol
 * @param <TIPOWRAPPER>
 */
public interface InterfaceDao<TIPOWRAPPER extends Wrapper> {

	public void clearCache();
	
	public boolean criar(TIPOWRAPPER e);

	public boolean atualizar(TIPOWRAPPER e);

	public boolean destruir(TIPOWRAPPER e);

	public ObservableList<TIPOWRAPPER> listarWrapper();

	public int count();

//	public TIPOWRAPPER getById(Object id);
}
