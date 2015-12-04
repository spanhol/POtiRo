package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Caminhao;
import entidades.Custocaminhao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Fernando Spanhol
 */
public class CustocaminhaoJpaController implements Serializable {

	public CustocaminhaoJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Custocaminhao custocaminhao) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Caminhao idcaminhao = custocaminhao.getIdcaminhao();
			if (idcaminhao != null) {
				idcaminhao = em.getReference(idcaminhao.getClass(), idcaminhao.getId());
				custocaminhao.setIdcaminhao(idcaminhao);
			}
			em.persist(custocaminhao);
			if (idcaminhao != null) {
				idcaminhao.getCustocaminhaoCollection().add(custocaminhao);
				idcaminhao = em.merge(idcaminhao);
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Custocaminhao custocaminhao) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Custocaminhao persistentCustocaminhao = em.find(Custocaminhao.class, custocaminhao.getId());
			Caminhao idcaminhaoOld = persistentCustocaminhao.getIdcaminhao();
			Caminhao idcaminhaoNew = custocaminhao.getIdcaminhao();
			if (idcaminhaoNew != null) {
				idcaminhaoNew = em.getReference(idcaminhaoNew.getClass(), idcaminhaoNew.getId());
				custocaminhao.setIdcaminhao(idcaminhaoNew);
			}
			custocaminhao = em.merge(custocaminhao);
			if (idcaminhaoOld != null && !idcaminhaoOld.equals(idcaminhaoNew)) {
				idcaminhaoOld.getCustocaminhaoCollection().remove(custocaminhao);
				idcaminhaoOld = em.merge(idcaminhaoOld);
			}
			if (idcaminhaoNew != null && !idcaminhaoNew.equals(idcaminhaoOld)) {
				idcaminhaoNew.getCustocaminhaoCollection().add(custocaminhao);
				idcaminhaoNew = em.merge(idcaminhaoNew);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = custocaminhao.getId();
				if (findCustocaminhao(id) == null) {
					throw new NonexistentEntityException("The custocaminhao with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Custocaminhao custocaminhao;
			try {
				custocaminhao = em.getReference(Custocaminhao.class, id);
				custocaminhao.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The custocaminhao with id " + id + " no longer exists.", enfe);
			}
			Caminhao idcaminhao = custocaminhao.getIdcaminhao();
			if (idcaminhao != null) {
				idcaminhao.getCustocaminhaoCollection().remove(custocaminhao);
				idcaminhao = em.merge(idcaminhao);
			}
			em.remove(custocaminhao);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Custocaminhao> findCustocaminhaoEntities() {
		return findCustocaminhaoEntities(true, -1, -1);
	}

	public List<Custocaminhao> findCustocaminhaoEntities(int maxResults, int firstResult) {
		return findCustocaminhaoEntities(false, maxResults, firstResult);
	}

	private List<Custocaminhao> findCustocaminhaoEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Custocaminhao.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Custocaminhao findCustocaminhao(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Custocaminhao.class, id);
		} finally {
			em.close();
		}
	}

	public int getCustocaminhaoCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Custocaminhao> rt = cq.from(Custocaminhao.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
