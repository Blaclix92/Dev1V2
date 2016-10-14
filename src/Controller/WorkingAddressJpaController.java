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
import Model.Employee;
import Model.Address;
import Model.WorkingAddress;
import Model.WorkingAddressPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Donovan
 */
public class WorkingAddressJpaController implements Serializable {

    public WorkingAddressJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(WorkingAddress workingAddress) throws PreexistingEntityException, Exception {
        if (workingAddress.getWorkingAddressPK() == null) {
            workingAddress.setWorkingAddressPK(new WorkingAddressPK());
        }
        workingAddress.getWorkingAddressPK().setBsn(workingAddress.getEmployee().getBsn());
        workingAddress.getWorkingAddressPK().setCountry(workingAddress.getAddress().getAddressPK().getCountry());
        workingAddress.getWorkingAddressPK().setPostalcode(workingAddress.getAddress().getAddressPK().getPostalcode());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee = workingAddress.getEmployee();
            if (employee != null) {
                employee = em.getReference(employee.getClass(), employee.getBsn());
                workingAddress.setEmployee(employee);
            }
            Address address = workingAddress.getAddress();
            if (address != null) {
                address = em.getReference(address.getClass(), address.getAddressPK());
                workingAddress.setAddress(address);
            }
            em.persist(workingAddress);
            if (employee != null) {
                employee.getWorkingAddressCollection().add(workingAddress);
                employee = em.merge(employee);
            }
            if (address != null) {
                address.getWorkingAddressCollection().add(workingAddress);
                address = em.merge(address);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWorkingAddress(workingAddress.getWorkingAddressPK()) != null) {
                throw new PreexistingEntityException("WorkingAddress " + workingAddress + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(WorkingAddress workingAddress) throws NonexistentEntityException, Exception {
        workingAddress.getWorkingAddressPK().setBsn(workingAddress.getEmployee().getBsn());
        workingAddress.getWorkingAddressPK().setCountry(workingAddress.getAddress().getAddressPK().getCountry());
        workingAddress.getWorkingAddressPK().setPostalcode(workingAddress.getAddress().getAddressPK().getPostalcode());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WorkingAddress persistentWorkingAddress = em.find(WorkingAddress.class, workingAddress.getWorkingAddressPK());
            Employee employeeOld = persistentWorkingAddress.getEmployee();
            Employee employeeNew = workingAddress.getEmployee();
            Address addressOld = persistentWorkingAddress.getAddress();
            Address addressNew = workingAddress.getAddress();
            if (employeeNew != null) {
                employeeNew = em.getReference(employeeNew.getClass(), employeeNew.getBsn());
                workingAddress.setEmployee(employeeNew);
            }
            if (addressNew != null) {
                addressNew = em.getReference(addressNew.getClass(), addressNew.getAddressPK());
                workingAddress.setAddress(addressNew);
            }
            workingAddress = em.merge(workingAddress);
            if (employeeOld != null && !employeeOld.equals(employeeNew)) {
                employeeOld.getWorkingAddressCollection().remove(workingAddress);
                employeeOld = em.merge(employeeOld);
            }
            if (employeeNew != null && !employeeNew.equals(employeeOld)) {
                employeeNew.getWorkingAddressCollection().add(workingAddress);
                employeeNew = em.merge(employeeNew);
            }
            if (addressOld != null && !addressOld.equals(addressNew)) {
                addressOld.getWorkingAddressCollection().remove(workingAddress);
                addressOld = em.merge(addressOld);
            }
            if (addressNew != null && !addressNew.equals(addressOld)) {
                addressNew.getWorkingAddressCollection().add(workingAddress);
                addressNew = em.merge(addressNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                WorkingAddressPK id = workingAddress.getWorkingAddressPK();
                if (findWorkingAddress(id) == null) {
                    throw new NonexistentEntityException("The workingAddress with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(WorkingAddressPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WorkingAddress workingAddress;
            try {
                workingAddress = em.getReference(WorkingAddress.class, id);
                workingAddress.getWorkingAddressPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workingAddress with id " + id + " no longer exists.", enfe);
            }
            Employee employee = workingAddress.getEmployee();
            if (employee != null) {
                employee.getWorkingAddressCollection().remove(workingAddress);
                employee = em.merge(employee);
            }
            Address address = workingAddress.getAddress();
            if (address != null) {
                address.getWorkingAddressCollection().remove(workingAddress);
                address = em.merge(address);
            }
            em.remove(workingAddress);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<WorkingAddress> findWorkingAddressEntities() {
        return findWorkingAddressEntities(true, -1, -1);
    }

    public List<WorkingAddress> findWorkingAddressEntities(int maxResults, int firstResult) {
        return findWorkingAddressEntities(false, maxResults, firstResult);
    }

    private List<WorkingAddress> findWorkingAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(WorkingAddress.class));
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

    public WorkingAddress findWorkingAddress(WorkingAddressPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(WorkingAddress.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkingAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<WorkingAddress> rt = cq.from(WorkingAddress.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
