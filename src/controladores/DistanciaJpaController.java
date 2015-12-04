package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidades.Distancia;
import entidades.DistanciaPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Fernando Spanhol
 */
public class DistanciaJpaController implements Serializable {

	public DistanciaJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Distancia distancia) throws PreexistingEntityException, Exception {
		if (distancia.getDistanciaPK() == null) {
			distancia.setDistanciaPK(new DistanciaPK());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(distancia);
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findDistancia(distancia.getDistanciaPK()) != null) {
				throw new PreexistingEntityException("Distancia " + distancia + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Distancia distancia) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			distancia = em.merge(distancia);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				DistanciaPK id = distancia.getDistanciaPK();
				if (findDistancia(id) == null) {
					throw new NonexistentEntityException("The distancia with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(DistanciaPK id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Distancia distancia;
			try {
				distancia = em.getReference(Distancia.class, id);
				distancia.getDistanciaPK();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The distancia with id " + id + " no longer exists.", enfe);
			}
			em.remove(distancia);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Distancia> findDistanciaEntities() {
		return findDistanciaEntities(true, -1, -1);
	}

	public List<Distancia> findDistanciaEntities(int maxResults, int firstResult) {
		return findDistanciaEntities(false, maxResults, firstResult);
	}

	private List<Distancia> findDistanciaEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Distancia.class));
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

	public Distancia findDistancia(DistanciaPK id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Distancia.class, id);
		} finally {
			em.close();
		}
	}

	public int getDistanciaCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Distancia> rt = cq.from(Distancia.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
