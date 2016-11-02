/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Model.PositieDescription;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.PositieEmployer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Benny
 */
public class PositieDescriptionJpaController implements Serializable {

    public PositieDescriptionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PositieDescription positieDescription) {
        if (positieDescription.getPositieEmployerCollection() == null) {
            positieDescription.setPositieEmployerCollection(new ArrayList<PositieEmployer>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PositieEmployer> attachedPositieEmployerCollection = new ArrayList<PositieEmployer>();
            for (PositieEmployer positieEmployerCollectionPositieEmployerToAttach : positieDescription.getPositieEmployerCollection()) {
                positieEmployerCollectionPositieEmployerToAttach = em.getReference(positieEmployerCollectionPositieEmployerToAttach.getClass(), positieEmployerCollectionPositieEmployerToAttach.getPositieEmployerPK());
                attachedPositieEmployerCollection.add(positieEmployerCollectionPositieEmployerToAttach);
            }
            positieDescription.setPositieEmployerCollection(attachedPositieEmployerCollection);
            em.persist(positieDescription);
            for (PositieEmployer positieEmployerCollectionPositieEmployer : positieDescription.getPositieEmployerCollection()) {
                PositieDescription oldPositieDescriptionOfPositieEmployerCollectionPositieEmployer = positieEmployerCollectionPositieEmployer.getPositieDescription();
                positieEmployerCollectionPositieEmployer.setPositieDescription(positieDescription);
                positieEmployerCollectionPositieEmployer = em.merge(positieEmployerCollectionPositieEmployer);
                if (oldPositieDescriptionOfPositieEmployerCollectionPositieEmployer != null) {
                    oldPositieDescriptionOfPositieEmployerCollectionPositieEmployer.getPositieEmployerCollection().remove(positieEmployerCollectionPositieEmployer);
                    oldPositieDescriptionOfPositieEmployerCollectionPositieEmployer = em.merge(oldPositieDescriptionOfPositieEmployerCollectionPositieEmployer);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PositieDescription positieDescription) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PositieDescription persistentPositieDescription = em.find(PositieDescription.class, positieDescription.getPositieid());
            Collection<PositieEmployer> positieEmployerCollectionOld = persistentPositieDescription.getPositieEmployerCollection();
            Collection<PositieEmployer> positieEmployerCollectionNew = positieDescription.getPositieEmployerCollection();
            List<String> illegalOrphanMessages = null;
            for (PositieEmployer positieEmployerCollectionOldPositieEmployer : positieEmployerCollectionOld) {
                if (!positieEmployerCollectionNew.contains(positieEmployerCollectionOldPositieEmployer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PositieEmployer " + positieEmployerCollectionOldPositieEmployer + " since its positieDescription field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PositieEmployer> attachedPositieEmployerCollectionNew = new ArrayList<PositieEmployer>();
            for (PositieEmployer positieEmployerCollectionNewPositieEmployerToAttach : positieEmployerCollectionNew) {
                positieEmployerCollectionNewPositieEmployerToAttach = em.getReference(positieEmployerCollectionNewPositieEmployerToAttach.getClass(), positieEmployerCollectionNewPositieEmployerToAttach.getPositieEmployerPK());
                attachedPositieEmployerCollectionNew.add(positieEmployerCollectionNewPositieEmployerToAttach);
            }
            positieEmployerCollectionNew = attachedPositieEmployerCollectionNew;
            positieDescription.setPositieEmployerCollection(positieEmployerCollectionNew);
            positieDescription = em.merge(positieDescription);
            for (PositieEmployer positieEmployerCollectionNewPositieEmployer : positieEmployerCollectionNew) {
                if (!positieEmployerCollectionOld.contains(positieEmployerCollectionNewPositieEmployer)) {
                    PositieDescription oldPositieDescriptionOfPositieEmployerCollectionNewPositieEmployer = positieEmployerCollectionNewPositieEmployer.getPositieDescription();
                    positieEmployerCollectionNewPositieEmployer.setPositieDescription(positieDescription);
                    positieEmployerCollectionNewPositieEmployer = em.merge(positieEmployerCollectionNewPositieEmployer);
                    if (oldPositieDescriptionOfPositieEmployerCollectionNewPositieEmployer != null && !oldPositieDescriptionOfPositieEmployerCollectionNewPositieEmployer.equals(positieDescription)) {
                        oldPositieDescriptionOfPositieEmployerCollectionNewPositieEmployer.getPositieEmployerCollection().remove(positieEmployerCollectionNewPositieEmployer);
                        oldPositieDescriptionOfPositieEmployerCollectionNewPositieEmployer = em.merge(oldPositieDescriptionOfPositieEmployerCollectionNewPositieEmployer);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = positieDescription.getPositieid();
                if (findPositieDescription(id) == null) {
                    throw new NonexistentEntityException("The positieDescription with id " + id + " no longer exists.");
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
            PositieDescription positieDescription;
            try {
                positieDescription = em.getReference(PositieDescription.class, id);
                positieDescription.getPositieid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The positieDescription with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PositieEmployer> positieEmployerCollectionOrphanCheck = positieDescription.getPositieEmployerCollection();
            for (PositieEmployer positieEmployerCollectionOrphanCheckPositieEmployer : positieEmployerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PositieDescription (" + positieDescription + ") cannot be destroyed since the PositieEmployer " + positieEmployerCollectionOrphanCheckPositieEmployer + " in its positieEmployerCollection field has a non-nullable positieDescription field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(positieDescription);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PositieDescription> findPositieDescriptionEntities() {
        return findPositieDescriptionEntities(true, -1, -1);
    }

    public List<PositieDescription> findPositieDescriptionEntities(int maxResults, int firstResult) {
        return findPositieDescriptionEntities(false, maxResults, firstResult);
    }

    private List<PositieDescription> findPositieDescriptionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PositieDescription.class));
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

    public PositieDescription findPositieDescription(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PositieDescription.class, id);
        } finally {
            em.close();
        }
    }

    public int getPositieDescriptionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PositieDescription> rt = cq.from(PositieDescription.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
