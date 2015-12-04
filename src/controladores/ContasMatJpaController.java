/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.ContasMat;
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
public class ContasMatJpaController implements Serializable {

	public ContasMatJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(ContasMat contasMat) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(contasMat);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(ContasMat contasMat) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			contasMat = em.merge(contasMat);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = contasMat.getId();
				if (findContasMat(id) == null) {
					throw new NonexistentEntityException("The contasMat with id " + id + " no longer exists.");
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
			ContasMat contasMat;
			try {
				contasMat = em.getReference(ContasMat.class, id);
				contasMat.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The contasMat with id " + id + " no longer exists.", enfe);
			}
			em.remove(contasMat);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<ContasMat> findContasMatEntities() {
		return findContasMatEntities(true, -1, -1);
	}

	public List<ContasMat> findContasMatEntities(int maxResults, int firstResult) {
		return findContasMatEntities(false, maxResults, firstResult);
	}

	private List<ContasMat> findContasMatEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(ContasMat.class));
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

	public ContasMat findContasMat(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(ContasMat.class, id);
		} finally {
			em.close();
		}
	}

	public int getContasMatCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<ContasMat> rt = cq.from(ContasMat.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
