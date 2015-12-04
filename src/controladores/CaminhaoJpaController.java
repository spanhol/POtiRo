/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.Caminhao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Viagem;
import java.util.ArrayList;
import java.util.Collection;
import entidades.Custocaminhao;
import entidades.Motorista;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Fernando Spanhol
 */
public class CaminhaoJpaController implements Serializable {

	public CaminhaoJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Caminhao caminhao) {
		if (caminhao.getViagemCollection() == null) {
			caminhao.setViagemCollection(new ArrayList<Viagem>());
		}
		if (caminhao.getCustocaminhaoCollection() == null) {
			caminhao.setCustocaminhaoCollection(new ArrayList<Custocaminhao>());
		}
		if (caminhao.getMotoristaCollection() == null) {
			caminhao.setMotoristaCollection(new ArrayList<Motorista>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Collection<Viagem> attachedViagemCollection = new ArrayList<Viagem>();
			for (Viagem viagemCollectionViagemToAttach : caminhao.getViagemCollection()) {
				viagemCollectionViagemToAttach = em.getReference(viagemCollectionViagemToAttach.getClass(), viagemCollectionViagemToAttach.getId());
				attachedViagemCollection.add(viagemCollectionViagemToAttach);
			}
			caminhao.setViagemCollection(attachedViagemCollection);
			Collection<Custocaminhao> attachedCustocaminhaoCollection = new ArrayList<Custocaminhao>();
			for (Custocaminhao custocaminhaoCollectionCustocaminhaoToAttach : caminhao.getCustocaminhaoCollection()) {
				custocaminhaoCollectionCustocaminhaoToAttach = em.getReference(custocaminhaoCollectionCustocaminhaoToAttach.getClass(), custocaminhaoCollectionCustocaminhaoToAttach.getId());
				attachedCustocaminhaoCollection.add(custocaminhaoCollectionCustocaminhaoToAttach);
			}
			caminhao.setCustocaminhaoCollection(attachedCustocaminhaoCollection);
			Collection<Motorista> attachedMotoristaCollection = new ArrayList<Motorista>();
			for (Motorista motoristaCollectionMotoristaToAttach : caminhao.getMotoristaCollection()) {
				motoristaCollectionMotoristaToAttach = em.getReference(motoristaCollectionMotoristaToAttach.getClass(), motoristaCollectionMotoristaToAttach.getId());
				attachedMotoristaCollection.add(motoristaCollectionMotoristaToAttach);
			}
			caminhao.setMotoristaCollection(attachedMotoristaCollection);
			em.persist(caminhao);
			for (Viagem viagemCollectionViagem : caminhao.getViagemCollection()) {
				Caminhao oldIdcaminhaoOfViagemCollectionViagem = viagemCollectionViagem.getIdcaminhao();
				viagemCollectionViagem.setIdcaminhao(caminhao);
				viagemCollectionViagem = em.merge(viagemCollectionViagem);
				if (oldIdcaminhaoOfViagemCollectionViagem != null) {
					oldIdcaminhaoOfViagemCollectionViagem.getViagemCollection().remove(viagemCollectionViagem);
					oldIdcaminhaoOfViagemCollectionViagem = em.merge(oldIdcaminhaoOfViagemCollectionViagem);
				}
			}
			for (Custocaminhao custocaminhaoCollectionCustocaminhao : caminhao.getCustocaminhaoCollection()) {
				Caminhao oldIdcaminhaoOfCustocaminhaoCollectionCustocaminhao = custocaminhaoCollectionCustocaminhao.getIdcaminhao();
				custocaminhaoCollectionCustocaminhao.setIdcaminhao(caminhao);
				custocaminhaoCollectionCustocaminhao = em.merge(custocaminhaoCollectionCustocaminhao);
				if (oldIdcaminhaoOfCustocaminhaoCollectionCustocaminhao != null) {
					oldIdcaminhaoOfCustocaminhaoCollectionCustocaminhao.getCustocaminhaoCollection().remove(custocaminhaoCollectionCustocaminhao);
					oldIdcaminhaoOfCustocaminhaoCollectionCustocaminhao = em.merge(oldIdcaminhaoOfCustocaminhaoCollectionCustocaminhao);
				}
			}
			for (Motorista motoristaCollectionMotorista : caminhao.getMotoristaCollection()) {
				Caminhao oldFavcaminhaoidOfMotoristaCollectionMotorista = motoristaCollectionMotorista.getFavcaminhaoid();
				motoristaCollectionMotorista.setFavcaminhaoid(caminhao);
				motoristaCollectionMotorista = em.merge(motoristaCollectionMotorista);
				if (oldFavcaminhaoidOfMotoristaCollectionMotorista != null) {
					oldFavcaminhaoidOfMotoristaCollectionMotorista.getMotoristaCollection().remove(motoristaCollectionMotorista);
					oldFavcaminhaoidOfMotoristaCollectionMotorista = em.merge(oldFavcaminhaoidOfMotoristaCollectionMotorista);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Caminhao caminhao) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Caminhao persistentCaminhao = em.find(Caminhao.class, caminhao.getId());
			Collection<Viagem> viagemCollectionOld = persistentCaminhao.getViagemCollection();
			Collection<Viagem> viagemCollectionNew = caminhao.getViagemCollection();
			Collection<Custocaminhao> custocaminhaoCollectionOld = persistentCaminhao.getCustocaminhaoCollection();
			Collection<Custocaminhao> custocaminhaoCollectionNew = caminhao.getCustocaminhaoCollection();
			Collection<Motorista> motoristaCollectionOld = persistentCaminhao.getMotoristaCollection();
			Collection<Motorista> motoristaCollectionNew = caminhao.getMotoristaCollection();
			List<String> illegalOrphanMessages = null;
			for (Viagem viagemCollectionOldViagem : viagemCollectionOld) {
				if (!viagemCollectionNew.contains(viagemCollectionOldViagem)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Viagem " + viagemCollectionOldViagem + " since its idcaminhao field is not nullable.");
				}
			}
			for (Custocaminhao custocaminhaoCollectionOldCustocaminhao : custocaminhaoCollectionOld) {
				if (!custocaminhaoCollectionNew.contains(custocaminhaoCollectionOldCustocaminhao)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Custocaminhao " + custocaminhaoCollectionOldCustocaminhao + " since its idcaminhao field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Collection<Viagem> attachedViagemCollectionNew = new ArrayList<Viagem>();
			for (Viagem viagemCollectionNewViagemToAttach : viagemCollectionNew) {
				viagemCollectionNewViagemToAttach = em.getReference(viagemCollectionNewViagemToAttach.getClass(), viagemCollectionNewViagemToAttach.getId());
				attachedViagemCollectionNew.add(viagemCollectionNewViagemToAttach);
			}
			viagemCollectionNew = attachedViagemCollectionNew;
			caminhao.setViagemCollection(viagemCollectionNew);
			Collection<Custocaminhao> attachedCustocaminhaoCollectionNew = new ArrayList<Custocaminhao>();
			for (Custocaminhao custocaminhaoCollectionNewCustocaminhaoToAttach : custocaminhaoCollectionNew) {
				custocaminhaoCollectionNewCustocaminhaoToAttach = em.getReference(custocaminhaoCollectionNewCustocaminhaoToAttach.getClass(), custocaminhaoCollectionNewCustocaminhaoToAttach.getId());
				attachedCustocaminhaoCollectionNew.add(custocaminhaoCollectionNewCustocaminhaoToAttach);
			}
			custocaminhaoCollectionNew = attachedCustocaminhaoCollectionNew;
			caminhao.setCustocaminhaoCollection(custocaminhaoCollectionNew);
			Collection<Motorista> attachedMotoristaCollectionNew = new ArrayList<Motorista>();
			for (Motorista motoristaCollectionNewMotoristaToAttach : motoristaCollectionNew) {
				motoristaCollectionNewMotoristaToAttach = em.getReference(motoristaCollectionNewMotoristaToAttach.getClass(), motoristaCollectionNewMotoristaToAttach.getId());
				attachedMotoristaCollectionNew.add(motoristaCollectionNewMotoristaToAttach);
			}
			motoristaCollectionNew = attachedMotoristaCollectionNew;
			caminhao.setMotoristaCollection(motoristaCollectionNew);
			caminhao = em.merge(caminhao);
			for (Viagem viagemCollectionNewViagem : viagemCollectionNew) {
				if (!viagemCollectionOld.contains(viagemCollectionNewViagem)) {
					Caminhao oldIdcaminhaoOfViagemCollectionNewViagem = viagemCollectionNewViagem.getIdcaminhao();
					viagemCollectionNewViagem.setIdcaminhao(caminhao);
					viagemCollectionNewViagem = em.merge(viagemCollectionNewViagem);
					if (oldIdcaminhaoOfViagemCollectionNewViagem != null && !oldIdcaminhaoOfViagemCollectionNewViagem.equals(caminhao)) {
						oldIdcaminhaoOfViagemCollectionNewViagem.getViagemCollection().remove(viagemCollectionNewViagem);
						oldIdcaminhaoOfViagemCollectionNewViagem = em.merge(oldIdcaminhaoOfViagemCollectionNewViagem);
					}
				}
			}
			for (Custocaminhao custocaminhaoCollectionNewCustocaminhao : custocaminhaoCollectionNew) {
				if (!custocaminhaoCollectionOld.contains(custocaminhaoCollectionNewCustocaminhao)) {
					Caminhao oldIdcaminhaoOfCustocaminhaoCollectionNewCustocaminhao = custocaminhaoCollectionNewCustocaminhao.getIdcaminhao();
					custocaminhaoCollectionNewCustocaminhao.setIdcaminhao(caminhao);
					custocaminhaoCollectionNewCustocaminhao = em.merge(custocaminhaoCollectionNewCustocaminhao);
					if (oldIdcaminhaoOfCustocaminhaoCollectionNewCustocaminhao != null && !oldIdcaminhaoOfCustocaminhaoCollectionNewCustocaminhao.equals(caminhao)) {
						oldIdcaminhaoOfCustocaminhaoCollectionNewCustocaminhao.getCustocaminhaoCollection().remove(custocaminhaoCollectionNewCustocaminhao);
						oldIdcaminhaoOfCustocaminhaoCollectionNewCustocaminhao = em.merge(oldIdcaminhaoOfCustocaminhaoCollectionNewCustocaminhao);
					}
				}
			}
			for (Motorista motoristaCollectionOldMotorista : motoristaCollectionOld) {
				if (!motoristaCollectionNew.contains(motoristaCollectionOldMotorista)) {
					motoristaCollectionOldMotorista.setFavcaminhaoid(null);
					motoristaCollectionOldMotorista = em.merge(motoristaCollectionOldMotorista);
				}
			}
			for (Motorista motoristaCollectionNewMotorista : motoristaCollectionNew) {
				if (!motoristaCollectionOld.contains(motoristaCollectionNewMotorista)) {
					Caminhao oldFavcaminhaoidOfMotoristaCollectionNewMotorista = motoristaCollectionNewMotorista.getFavcaminhaoid();
					motoristaCollectionNewMotorista.setFavcaminhaoid(caminhao);
					motoristaCollectionNewMotorista = em.merge(motoristaCollectionNewMotorista);
					if (oldFavcaminhaoidOfMotoristaCollectionNewMotorista != null && !oldFavcaminhaoidOfMotoristaCollectionNewMotorista.equals(caminhao)) {
						oldFavcaminhaoidOfMotoristaCollectionNewMotorista.getMotoristaCollection().remove(motoristaCollectionNewMotorista);
						oldFavcaminhaoidOfMotoristaCollectionNewMotorista = em.merge(oldFavcaminhaoidOfMotoristaCollectionNewMotorista);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = caminhao.getId();
				if (findCaminhao(id) == null) {
					throw new NonexistentEntityException("The caminhao with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Caminhao caminhao;
			try {
				caminhao = em.getReference(Caminhao.class, id);
				caminhao.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The caminhao with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			Collection<Viagem> viagemCollectionOrphanCheck = caminhao.getViagemCollection();
			for (Viagem viagemCollectionOrphanCheckViagem : viagemCollectionOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Caminhao (" + caminhao + ") cannot be destroyed since the Viagem " + viagemCollectionOrphanCheckViagem + " in its viagemCollection field has a non-nullable idcaminhao field.");
			}
			Collection<Custocaminhao> custocaminhaoCollectionOrphanCheck = caminhao.getCustocaminhaoCollection();
			for (Custocaminhao custocaminhaoCollectionOrphanCheckCustocaminhao : custocaminhaoCollectionOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Caminhao (" + caminhao + ") cannot be destroyed since the Custocaminhao " + custocaminhaoCollectionOrphanCheckCustocaminhao + " in its custocaminhaoCollection field has a non-nullable idcaminhao field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Collection<Motorista> motoristaCollection = caminhao.getMotoristaCollection();
			for (Motorista motoristaCollectionMotorista : motoristaCollection) {
				motoristaCollectionMotorista.setFavcaminhaoid(null);
				motoristaCollectionMotorista = em.merge(motoristaCollectionMotorista);
			}
			em.remove(caminhao);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Caminhao> findCaminhaoEntities() {
		return findCaminhaoEntities(true, -1, -1);
	}

	public List<Caminhao> findCaminhaoEntities(int maxResults, int firstResult) {
		return findCaminhaoEntities(false, maxResults, firstResult);
	}

	private List<Caminhao> findCaminhaoEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Caminhao.class));
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

	public Caminhao findCaminhao(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Caminhao.class, id);
		} finally {
			em.close();
		}
	}

	public int getCaminhaoCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Caminhao> rt = cq.from(Caminhao.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
