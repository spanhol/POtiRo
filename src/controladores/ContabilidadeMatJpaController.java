/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.ContabilidadeMat;
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
public class ContabilidadeMatJpaController implements Serializable {

	public ContabilidadeMatJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(ContabilidadeMat contabilidadeMat) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(contabilidadeMat);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(ContabilidadeMat contabilidadeMat) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			contabilidadeMat = em.merge(contabilidadeMat);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = contabilidadeMat.getId();
				if (findContabilidadeMat(id) == null) {
					throw new NonexistentEntityException("The contabilidadeMat with id " + id + " no longer exists.");
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
			ContabilidadeMat contabilidadeMat;
			try {
				contabilidadeMat = em.getReference(ContabilidadeMat.class, id);
				contabilidadeMat.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The contabilidadeMat with id " + id + " no longer exists.", enfe);
			}
			em.remove(contabilidadeMat);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<ContabilidadeMat> findContabilidadeMatEntities() {
		return findContabilidadeMatEntities(true, -1, -1);
	}

	public List<ContabilidadeMat> findContabilidadeMatEntities(int maxResults, int firstResult) {
		return findContabilidadeMatEntities(false, maxResults, firstResult);
	}

	private List<ContabilidadeMat> findContabilidadeMatEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(ContabilidadeMat.class));
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

	public ContabilidadeMat findContabilidadeMat(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(ContabilidadeMat.class, id);
		} finally {
			em.close();
		}
	}

	public int getContabilidadeMatCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<ContabilidadeMat> rt = cq.from(ContabilidadeMat.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
