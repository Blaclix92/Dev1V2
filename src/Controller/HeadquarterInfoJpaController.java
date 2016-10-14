/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Address;
import Model.HeadquarterInfo;
import java.util.ArrayList;
import java.util.Collection;
import Model.Project;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Donovan
 */
public class HeadquarterInfoJpaController implements Serializable {

    public HeadquarterInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HeadquarterInfo headquarterInfo) throws PreexistingEntityException, Exception {
        if (headquarterInfo.getAddressCollection() == null) {
            headquarterInfo.setAddressCollection(new ArrayList<Address>());
        }
        if (headquarterInfo.getProjectCollection() == null) {
            headquarterInfo.setProjectCollection(new ArrayList<Project>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Address> attachedAddressCollection = new ArrayList<Address>();
            for (Address addressCollectionAddressToAttach : headquarterInfo.getAddressCollection()) {
                addressCollectionAddressToAttach = em.getReference(addressCollectionAddressToAttach.getClass(), addressCollectionAddressToAttach.getAddressPK());
                attachedAddressCollection.add(addressCollectionAddressToAttach);
            }
            headquarterInfo.setAddressCollection(attachedAddressCollection);
            Collection<Project> attachedProjectCollection = new ArrayList<Project>();
            for (Project projectCollectionProjectToAttach : headquarterInfo.getProjectCollection()) {
                projectCollectionProjectToAttach = em.getReference(projectCollectionProjectToAttach.getClass(), projectCollectionProjectToAttach.getProjectid());
                attachedProjectCollection.add(projectCollectionProjectToAttach);
            }
            headquarterInfo.setProjectCollection(attachedProjectCollection);
            em.persist(headquarterInfo);
            for (Address addressCollectionAddress : headquarterInfo.getAddressCollection()) {
                addressCollectionAddress.getHeadquarterInfoCollection().add(headquarterInfo);
                addressCollectionAddress = em.merge(addressCollectionAddress);
            }
            for (Project projectCollectionProject : headquarterInfo.getProjectCollection()) {
                HeadquarterInfo oldHeadquarteridOfProjectCollectionProject = projectCollectionProject.getHeadquarterid();
                projectCollectionProject.setHeadquarterid(headquarterInfo);
                projectCollectionProject = em.merge(projectCollectionProject);
                if (oldHeadquarteridOfProjectCollectionProject != null) {
                    oldHeadquarteridOfProjectCollectionProject.getProjectCollection().remove(projectCollectionProject);
                    oldHeadquarteridOfProjectCollectionProject = em.merge(oldHeadquarteridOfProjectCollectionProject);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHeadquarterInfo(headquarterInfo.getHeadquarterid()) != null) {
                throw new PreexistingEntityException("HeadquarterInfo " + headquarterInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HeadquarterInfo headquarterInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HeadquarterInfo persistentHeadquarterInfo = em.find(HeadquarterInfo.class, headquarterInfo.getHeadquarterid());
            Collection<Address> addressCollectionOld = persistentHeadquarterInfo.getAddressCollection();
            Collection<Address> addressCollectionNew = headquarterInfo.getAddressCollection();
            Collection<Project> projectCollectionOld = persistentHeadquarterInfo.getProjectCollection();
            Collection<Project> projectCollectionNew = headquarterInfo.getProjectCollection();
            Collection<Address> attachedAddressCollectionNew = new ArrayList<Address>();
            for (Address addressCollectionNewAddressToAttach : addressCollectionNew) {
                addressCollectionNewAddressToAttach = em.getReference(addressCollectionNewAddressToAttach.getClass(), addressCollectionNewAddressToAttach.getAddressPK());
                attachedAddressCollectionNew.add(addressCollectionNewAddressToAttach);
            }
            addressCollectionNew = attachedAddressCollectionNew;
            headquarterInfo.setAddressCollection(addressCollectionNew);
            Collection<Project> attachedProjectCollectionNew = new ArrayList<Project>();
            for (Project projectCollectionNewProjectToAttach : projectCollectionNew) {
                projectCollectionNewProjectToAttach = em.getReference(projectCollectionNewProjectToAttach.getClass(), projectCollectionNewProjectToAttach.getProjectid());
                attachedProjectCollectionNew.add(projectCollectionNewProjectToAttach);
            }
            projectCollectionNew = attachedProjectCollectionNew;
            headquarterInfo.setProjectCollection(projectCollectionNew);
            headquarterInfo = em.merge(headquarterInfo);
            for (Address addressCollectionOldAddress : addressCollectionOld) {
                if (!addressCollectionNew.contains(addressCollectionOldAddress)) {
                    addressCollectionOldAddress.getHeadquarterInfoCollection().remove(headquarterInfo);
                    addressCollectionOldAddress = em.merge(addressCollectionOldAddress);
                }
            }
            for (Address addressCollectionNewAddress : addressCollectionNew) {
                if (!addressCollectionOld.contains(addressCollectionNewAddress)) {
                    addressCollectionNewAddress.getHeadquarterInfoCollection().add(headquarterInfo);
                    addressCollectionNewAddress = em.merge(addressCollectionNewAddress);
                }
            }
            for (Project projectCollectionOldProject : projectCollectionOld) {
                if (!projectCollectionNew.contains(projectCollectionOldProject)) {
                    projectCollectionOldProject.setHeadquarterid(null);
                    projectCollectionOldProject = em.merge(projectCollectionOldProject);
                }
            }
            for (Project projectCollectionNewProject : projectCollectionNew) {
                if (!projectCollectionOld.contains(projectCollectionNewProject)) {
                    HeadquarterInfo oldHeadquarteridOfProjectCollectionNewProject = projectCollectionNewProject.getHeadquarterid();
                    projectCollectionNewProject.setHeadquarterid(headquarterInfo);
                    projectCollectionNewProject = em.merge(projectCollectionNewProject);
                    if (oldHeadquarteridOfProjectCollectionNewProject != null && !oldHeadquarteridOfProjectCollectionNewProject.equals(headquarterInfo)) {
                        oldHeadquarteridOfProjectCollectionNewProject.getProjectCollection().remove(projectCollectionNewProject);
                        oldHeadquarteridOfProjectCollectionNewProject = em.merge(oldHeadquarteridOfProjectCollectionNewProject);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = headquarterInfo.getHeadquarterid();
                if (findHeadquarterInfo(id) == null) {
                    throw new NonexistentEntityException("The headquarterInfo with id " + id + " no longer exists.");
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
            HeadquarterInfo headquarterInfo;
            try {
                headquarterInfo = em.getReference(HeadquarterInfo.class, id);
                headquarterInfo.getHeadquarterid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The headquarterInfo with id " + id + " no longer exists.", enfe);
            }
            Collection<Address> addressCollection = headquarterInfo.getAddressCollection();
            for (Address addressCollectionAddress : addressCollection) {
                addressCollectionAddress.getHeadquarterInfoCollection().remove(headquarterInfo);
                addressCollectionAddress = em.merge(addressCollectionAddress);
            }
            Collection<Project> projectCollection = headquarterInfo.getProjectCollection();
            for (Project projectCollectionProject : projectCollection) {
                projectCollectionProject.setHeadquarterid(null);
                projectCollectionProject = em.merge(projectCollectionProject);
            }
            em.remove(headquarterInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HeadquarterInfo> findHeadquarterInfoEntities() {
        return findHeadquarterInfoEntities(true, -1, -1);
    }

    public List<HeadquarterInfo> findHeadquarterInfoEntities(int maxResults, int firstResult) {
        return findHeadquarterInfoEntities(false, maxResults, firstResult);
    }

    private List<HeadquarterInfo> findHeadquarterInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HeadquarterInfo.class));
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

    public HeadquarterInfo findHeadquarterInfo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HeadquarterInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getHeadquarterInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HeadquarterInfo> rt = cq.from(HeadquarterInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
