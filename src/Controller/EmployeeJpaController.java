/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Degree;
import Model.Employee;
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
public class EmployeeJpaController implements Serializable {

    public EmployeeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employee employee) throws PreexistingEntityException, Exception {
        if (employee.getDegreeCollection() == null) {
            employee.setDegreeCollection(new ArrayList<Degree>());
        }
        if (employee.getWorkingAddressCollection() == null) {
            employee.setWorkingAddressCollection(new ArrayList<WorkingAddress>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Degree> attachedDegreeCollection = new ArrayList<Degree>();
            for (Degree degreeCollectionDegreeToAttach : employee.getDegreeCollection()) {
                degreeCollectionDegreeToAttach = em.getReference(degreeCollectionDegreeToAttach.getClass(), degreeCollectionDegreeToAttach.getDegreePK());
                attachedDegreeCollection.add(degreeCollectionDegreeToAttach);
            }
            employee.setDegreeCollection(attachedDegreeCollection);
            Collection<WorkingAddress> attachedWorkingAddressCollection = new ArrayList<WorkingAddress>();
            for (WorkingAddress workingAddressCollectionWorkingAddressToAttach : employee.getWorkingAddressCollection()) {
                workingAddressCollectionWorkingAddressToAttach = em.getReference(workingAddressCollectionWorkingAddressToAttach.getClass(), workingAddressCollectionWorkingAddressToAttach.getWorkingAddressPK());
                attachedWorkingAddressCollection.add(workingAddressCollectionWorkingAddressToAttach);
            }
            employee.setWorkingAddressCollection(attachedWorkingAddressCollection);
            em.persist(employee);
            for (Degree degreeCollectionDegree : employee.getDegreeCollection()) {
                Employee oldEmployeeOfDegreeCollectionDegree = degreeCollectionDegree.getEmployee();
                degreeCollectionDegree.setEmployee(employee);
                degreeCollectionDegree = em.merge(degreeCollectionDegree);
                if (oldEmployeeOfDegreeCollectionDegree != null) {
                    oldEmployeeOfDegreeCollectionDegree.getDegreeCollection().remove(degreeCollectionDegree);
                    oldEmployeeOfDegreeCollectionDegree = em.merge(oldEmployeeOfDegreeCollectionDegree);
                }
            }
            for (WorkingAddress workingAddressCollectionWorkingAddress : employee.getWorkingAddressCollection()) {
                Employee oldEmployeeOfWorkingAddressCollectionWorkingAddress = workingAddressCollectionWorkingAddress.getEmployee();
                workingAddressCollectionWorkingAddress.setEmployee(employee);
                workingAddressCollectionWorkingAddress = em.merge(workingAddressCollectionWorkingAddress);
                if (oldEmployeeOfWorkingAddressCollectionWorkingAddress != null) {
                    oldEmployeeOfWorkingAddressCollectionWorkingAddress.getWorkingAddressCollection().remove(workingAddressCollectionWorkingAddress);
                    oldEmployeeOfWorkingAddressCollectionWorkingAddress = em.merge(oldEmployeeOfWorkingAddressCollectionWorkingAddress);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmployee(employee.getBsn()) != null) {
                throw new PreexistingEntityException("Employee " + employee + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employee employee) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee persistentEmployee = em.find(Employee.class, employee.getBsn());
            Collection<Degree> degreeCollectionOld = persistentEmployee.getDegreeCollection();
            Collection<Degree> degreeCollectionNew = employee.getDegreeCollection();
            Collection<WorkingAddress> workingAddressCollectionOld = persistentEmployee.getWorkingAddressCollection();
            Collection<WorkingAddress> workingAddressCollectionNew = employee.getWorkingAddressCollection();
            List<String> illegalOrphanMessages = null;
            for (Degree degreeCollectionOldDegree : degreeCollectionOld) {
                if (!degreeCollectionNew.contains(degreeCollectionOldDegree)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Degree " + degreeCollectionOldDegree + " since its employee field is not nullable.");
                }
            }
            for (WorkingAddress workingAddressCollectionOldWorkingAddress : workingAddressCollectionOld) {
                if (!workingAddressCollectionNew.contains(workingAddressCollectionOldWorkingAddress)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain WorkingAddress " + workingAddressCollectionOldWorkingAddress + " since its employee field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Degree> attachedDegreeCollectionNew = new ArrayList<Degree>();
            for (Degree degreeCollectionNewDegreeToAttach : degreeCollectionNew) {
                degreeCollectionNewDegreeToAttach = em.getReference(degreeCollectionNewDegreeToAttach.getClass(), degreeCollectionNewDegreeToAttach.getDegreePK());
                attachedDegreeCollectionNew.add(degreeCollectionNewDegreeToAttach);
            }
            degreeCollectionNew = attachedDegreeCollectionNew;
            employee.setDegreeCollection(degreeCollectionNew);
            Collection<WorkingAddress> attachedWorkingAddressCollectionNew = new ArrayList<WorkingAddress>();
            for (WorkingAddress workingAddressCollectionNewWorkingAddressToAttach : workingAddressCollectionNew) {
                workingAddressCollectionNewWorkingAddressToAttach = em.getReference(workingAddressCollectionNewWorkingAddressToAttach.getClass(), workingAddressCollectionNewWorkingAddressToAttach.getWorkingAddressPK());
                attachedWorkingAddressCollectionNew.add(workingAddressCollectionNewWorkingAddressToAttach);
            }
            workingAddressCollectionNew = attachedWorkingAddressCollectionNew;
            employee.setWorkingAddressCollection(workingAddressCollectionNew);
            employee = em.merge(employee);
            for (Degree degreeCollectionNewDegree : degreeCollectionNew) {
                if (!degreeCollectionOld.contains(degreeCollectionNewDegree)) {
                    Employee oldEmployeeOfDegreeCollectionNewDegree = degreeCollectionNewDegree.getEmployee();
                    degreeCollectionNewDegree.setEmployee(employee);
                    degreeCollectionNewDegree = em.merge(degreeCollectionNewDegree);
                    if (oldEmployeeOfDegreeCollectionNewDegree != null && !oldEmployeeOfDegreeCollectionNewDegree.equals(employee)) {
                        oldEmployeeOfDegreeCollectionNewDegree.getDegreeCollection().remove(degreeCollectionNewDegree);
                        oldEmployeeOfDegreeCollectionNewDegree = em.merge(oldEmployeeOfDegreeCollectionNewDegree);
                    }
                }
            }
            for (WorkingAddress workingAddressCollectionNewWorkingAddress : workingAddressCollectionNew) {
                if (!workingAddressCollectionOld.contains(workingAddressCollectionNewWorkingAddress)) {
                    Employee oldEmployeeOfWorkingAddressCollectionNewWorkingAddress = workingAddressCollectionNewWorkingAddress.getEmployee();
                    workingAddressCollectionNewWorkingAddress.setEmployee(employee);
                    workingAddressCollectionNewWorkingAddress = em.merge(workingAddressCollectionNewWorkingAddress);
                    if (oldEmployeeOfWorkingAddressCollectionNewWorkingAddress != null && !oldEmployeeOfWorkingAddressCollectionNewWorkingAddress.equals(employee)) {
                        oldEmployeeOfWorkingAddressCollectionNewWorkingAddress.getWorkingAddressCollection().remove(workingAddressCollectionNewWorkingAddress);
                        oldEmployeeOfWorkingAddressCollectionNewWorkingAddress = em.merge(oldEmployeeOfWorkingAddressCollectionNewWorkingAddress);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employee.getBsn();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
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
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getBsn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Degree> degreeCollectionOrphanCheck = employee.getDegreeCollection();
            for (Degree degreeCollectionOrphanCheckDegree : degreeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Degree " + degreeCollectionOrphanCheckDegree + " in its degreeCollection field has a non-nullable employee field.");
            }
            Collection<WorkingAddress> workingAddressCollectionOrphanCheck = employee.getWorkingAddressCollection();
            for (WorkingAddress workingAddressCollectionOrphanCheckWorkingAddress : workingAddressCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the WorkingAddress " + workingAddressCollectionOrphanCheckWorkingAddress + " in its workingAddressCollection field has a non-nullable employee field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
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

    public Employee findEmployee(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
