/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Model.Address;
import Model.AddressPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.HeadquarterInfo;
import java.util.ArrayList;
import java.util.Collection;
import Model.WorkingAddress;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Donovan
 */
public class AddressJpaController implements Serializable {

    public AddressJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Address address) throws PreexistingEntityException, Exception {
        if (address.getAddressPK() == null) {
            address.setAddressPK(new AddressPK());
        }
        if (address.getHeadquarterInfoCollection() == null) {
            address.setHeadquarterInfoCollection(new ArrayList<HeadquarterInfo>());
        }
        if (address.getWorkingAddressCollection() == null) {
            address.setWorkingAddressCollection(new ArrayList<WorkingAddress>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<HeadquarterInfo> attachedHeadquarterInfoCollection = new ArrayList<HeadquarterInfo>();
            for (HeadquarterInfo headquarterInfoCollectionHeadquarterInfoToAttach : address.getHeadquarterInfoCollection()) {
                headquarterInfoCollectionHeadquarterInfoToAttach = em.getReference(headquarterInfoCollectionHeadquarterInfoToAttach.getClass(), headquarterInfoCollectionHeadquarterInfoToAttach.getHeadquarterid());
                attachedHeadquarterInfoCollection.add(headquarterInfoCollectionHeadquarterInfoToAttach);
            }
            address.setHeadquarterInfoCollection(attachedHeadquarterInfoCollection);
            Collection<WorkingAddress> attachedWorkingAddressCollection = new ArrayList<WorkingAddress>();
            for (WorkingAddress workingAddressCollectionWorkingAddressToAttach : address.getWorkingAddressCollection()) {
                workingAddressCollectionWorkingAddressToAttach = em.getReference(workingAddressCollectionWorkingAddressToAttach.getClass(), workingAddressCollectionWorkingAddressToAttach.getWorkingAddressPK());
                attachedWorkingAddressCollection.add(workingAddressCollectionWorkingAddressToAttach);
            }
            address.setWorkingAddressCollection(attachedWorkingAddressCollection);
            em.persist(address);
            for (HeadquarterInfo headquarterInfoCollectionHeadquarterInfo : address.getHeadquarterInfoCollection()) {
                headquarterInfoCollectionHeadquarterInfo.getAddressCollection().add(address);
                headquarterInfoCollectionHeadquarterInfo = em.merge(headquarterInfoCollectionHeadquarterInfo);
            }
            for (WorkingAddress workingAddressCollectionWorkingAddress : address.getWorkingAddressCollection()) {
                Address oldAddressOfWorkingAddressCollectionWorkingAddress = workingAddressCollectionWorkingAddress.getAddress();
                workingAddressCollectionWorkingAddress.setAddress(address);
                workingAddressCollectionWorkingAddress = em.merge(workingAddressCollectionWorkingAddress);
                if (oldAddressOfWorkingAddressCollectionWorkingAddress != null) {
                    oldAddressOfWorkingAddressCollectionWorkingAddress.getWorkingAddressCollection().remove(workingAddressCollectionWorkingAddress);
                    oldAddressOfWorkingAddressCollectionWorkingAddress = em.merge(oldAddressOfWorkingAddressCollectionWorkingAddress);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAddress(address.getAddressPK()) != null) {
                throw new PreexistingEntityException("Address " + address + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Address address) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Address persistentAddress = em.find(Address.class, address.getAddressPK());
            Collection<HeadquarterInfo> headquarterInfoCollectionOld = persistentAddress.getHeadquarterInfoCollection();
            Collection<HeadquarterInfo> headquarterInfoCollectionNew = address.getHeadquarterInfoCollection();
            Collection<WorkingAddress> workingAddressCollectionOld = persistentAddress.getWorkingAddressCollection();
            Collection<WorkingAddress> workingAddressCollectionNew = address.getWorkingAddressCollection();
            List<String> illegalOrphanMessages = null;
            for (WorkingAddress workingAddressCollectionOldWorkingAddress : workingAddressCollectionOld) {
                if (!workingAddressCollectionNew.contains(workingAddressCollectionOldWorkingAddress)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain WorkingAddress " + workingAddressCollectionOldWorkingAddress + " since its address field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<HeadquarterInfo> attachedHeadquarterInfoCollectionNew = new ArrayList<HeadquarterInfo>();
            for (HeadquarterInfo headquarterInfoCollectionNewHeadquarterInfoToAttach : headquarterInfoCollectionNew) {
                headquarterInfoCollectionNewHeadquarterInfoToAttach = em.getReference(headquarterInfoCollectionNewHeadquarterInfoToAttach.getClass(), headquarterInfoCollectionNewHeadquarterInfoToAttach.getHeadquarterid());
                attachedHeadquarterInfoCollectionNew.add(headquarterInfoCollectionNewHeadquarterInfoToAttach);
            }
            headquarterInfoCollectionNew = attachedHeadquarterInfoCollectionNew;
            address.setHeadquarterInfoCollection(headquarterInfoCollectionNew);
            Collection<WorkingAddress> attachedWorkingAddressCollectionNew = new ArrayList<WorkingAddress>();
            for (WorkingAddress workingAddressCollectionNewWorkingAddressToAttach : workingAddressCollectionNew) {
                workingAddressCollectionNewWorkingAddressToAttach = em.getReference(workingAddressCollectionNewWorkingAddressToAttach.getClass(), workingAddressCollectionNewWorkingAddressToAttach.getWorkingAddressPK());
                attachedWorkingAddressCollectionNew.add(workingAddressCollectionNewWorkingAddressToAttach);
            }
            workingAddressCollectionNew = attachedWorkingAddressCollectionNew;
            address.setWorkingAddressCollection(workingAddressCollectionNew);
            address = em.merge(address);
            for (HeadquarterInfo headquarterInfoCollectionOldHeadquarterInfo : headquarterInfoCollectionOld) {
                if (!headquarterInfoCollectionNew.contains(headquarterInfoCollectionOldHeadquarterInfo)) {
                    headquarterInfoCollectionOldHeadquarterInfo.getAddressCollection().remove(address);
                    headquarterInfoCollectionOldHeadquarterInfo = em.merge(headquarterInfoCollectionOldHeadquarterInfo);
                }
            }
            for (HeadquarterInfo headquarterInfoCollectionNewHeadquarterInfo : headquarterInfoCollectionNew) {
                if (!headquarterInfoCollectionOld.contains(headquarterInfoCollectionNewHeadquarterInfo)) {
                    headquarterInfoCollectionNewHeadquarterInfo.getAddressCollection().add(address);
                    headquarterInfoCollectionNewHeadquarterInfo = em.merge(headquarterInfoCollectionNewHeadquarterInfo);
                }
            }
            for (WorkingAddress workingAddressCollectionNewWorkingAddress : workingAddressCollectionNew) {
                if (!workingAddressCollectionOld.contains(workingAddressCollectionNewWorkingAddress)) {
                    Address oldAddressOfWorkingAddressCollectionNewWorkingAddress = workingAddressCollectionNewWorkingAddress.getAddress();
                    workingAddressCollectionNewWorkingAddress.setAddress(address);
                    workingAddressCollectionNewWorkingAddress = em.merge(workingAddressCollectionNewWorkingAddress);
                    if (oldAddressOfWorkingAddressCollectionNewWorkingAddress != null && !oldAddressOfWorkingAddressCollectionNewWorkingAddress.equals(address)) {
                        oldAddressOfWorkingAddressCollectionNewWorkingAddress.getWorkingAddressCollection().remove(workingAddressCollectionNewWorkingAddress);
                        oldAddressOfWorkingAddressCollectionNewWorkingAddress = em.merge(oldAddressOfWorkingAddressCollectionNewWorkingAddress);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AddressPK id = address.getAddressPK();
                if (findAddress(id) == null) {
                    throw new NonexistentEntityException("The address with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AddressPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Address address;
            try {
                address = em.getReference(Address.class, id);
                address.getAddressPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The address with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<WorkingAddress> workingAddressCollectionOrphanCheck = address.getWorkingAddressCollection();
            for (WorkingAddress workingAddressCollectionOrphanCheckWorkingAddress : workingAddressCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Address (" + address + ") cannot be destroyed since the WorkingAddress " + workingAddressCollectionOrphanCheckWorkingAddress + " in its workingAddressCollection field has a non-nullable address field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<HeadquarterInfo> headquarterInfoCollection = address.getHeadquarterInfoCollection();
            for (HeadquarterInfo headquarterInfoCollectionHeadquarterInfo : headquarterInfoCollection) {
                headquarterInfoCollectionHeadquarterInfo.getAddressCollection().remove(address);
                headquarterInfoCollectionHeadquarterInfo = em.merge(headquarterInfoCollectionHeadquarterInfo);
            }
            em.remove(address);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Address> findAddressEntities() {
        return findAddressEntities(true, -1, -1);
    }

    public List<Address> findAddressEntities(int maxResults, int firstResult) {
        return findAddressEntities(false, maxResults, firstResult);
    }

    private List<Address> findAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Address.class));
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

    public Address findAddress(AddressPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Address.class, id);
        } finally {
            em.close();
        }
    }

    public int getAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Address> rt = cq.from(Address.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
