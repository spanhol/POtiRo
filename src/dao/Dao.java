package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fernando Spanhol
 */
public class Dao {

	public static EntityManagerFactory POtiRoPU = Persistence.createEntityManagerFactory("POtiRoPU");
	public static EntityManagerFactory ControleViagensPU = Persistence.createEntityManagerFactory("ControleViagensPU");

	public static void ClearCache() {
		POtiRoPU.getCache().evictAll();
		ControleViagensPU.getCache().evictAll();
	}
}
