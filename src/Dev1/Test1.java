/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dev1;

import Controller.AddressJpaController;
import Controller.DegreeJpaController;
import Controller.EmployeeJpaController;
import Controller.HeadquarterInfoJpaController;
import Controller.PositieDescriptionJpaController;
import Controller.PositieEmployerJpaController;
import Controller.ProjectJpaController;
import Controller.SchoolJpaController;
import Controller.WorkingAddressJpaController;
import Model.Address;
import Model.AddressPK;
import Model.Degree;
import Model.DegreePK;
import Model.Employee;
import Model.HeadquarterInfo;
import Model.PositieDescription;
import Model.PositieEmployer;
import Model.PositieEmployerPK;
import Model.Project;
import Model.School;
import Model.WorkingAddress;
import Model.WorkingAddressPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;

/**
 *
 * @author Benny
 */
public class Test1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //     JFrame as= new Test2();
        //    as.show();
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Dev1PU");
//        AddressJpaController ajc = new AddressJpaController(emf);
//        EmployeeJpaController ejc = new EmployeeJpaController(emf);
//        WorkingAddressJpaController wajc = new WorkingAddressJpaController(emf);
//        
//        
//        Employee e = new Employee();
//        e.setBsn(122);
//        e.setEmployeename("Benny");
//        e.setSurname("Haaa");
//        
//        AddressPK adPK = new AddressPK();
//        adPK.setCountry("Holland");
//        adPK.setPostalcode("1223TA");
//        
//           WorkingAddress 
//        
//        Address ad = new Address();
//        WorkingAddress wa = new WorkingAddress();
//      
//       
//         Address 
//        ad.setAddressPK(adPK);
//        ad.setCity("Rotterdam");
//        ad.setStreetname("Wijnhaven");
//        ad.setBuildingnumber(99);       
//        ad.setBuildingletter('a');
//        ad.setBuildingnumber(3);
//     
//        
//        
//        WorkingAddressPK workingaddresspk = new WorkingAddressPK();
//        workingaddresspk.setBsn(e.getBsn());
//        workingaddresspk.setCountry(adPK.getCountry());
//        workingaddresspk.setPostalcode(adPK.getPostalcode());
//        
//        wa.setWorkingAddressPK(workingaddresspk);
//        wa.setAddress(ad);
//        wa.setEmployee(e);
//        wa.setStartdate(new Date());
//        wa.setEnddate(null);
//        
//        Employee workingaddres collection
//       ArrayList<WorkingAddress> col = new ArrayList<>();
//       col.add(wa);
//       e.setWorkingAddressCollection(col);
//       
//     wajc.create(wa);
//       ejc.create(e);
//        ajc.create(ad);

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Dev1PU");

        
        EmployeeJpaController ejc = new EmployeeJpaController(emfactory);
        AddressJpaController ajc = new AddressJpaController(emfactory);
        WorkingAddressJpaController wajc = new WorkingAddressJpaController(emfactory);
        SchoolJpaController sjc = new SchoolJpaController(emfactory);
        DegreeJpaController djc = new DegreeJpaController(emfactory);

        HeadquarterInfoJpaController hijc = new HeadquarterInfoJpaController(emfactory);
        ProjectJpaController pjc = new ProjectJpaController(emfactory);
        PositieEmployerJpaController pejc = new PositieEmployerJpaController(emfactory);
        PositieDescriptionJpaController pdjc = new PositieDescriptionJpaController(emfactory);

        AddressPK addressPK = new AddressPK();
        addressPK.setCountry("Netherlands");
        addressPK.setPostalcode("3042AZ");

        Address address = new Address();
        address.setAddressPK(addressPK);
        address.setCity("Rotterdam");
        address.setStreetname("Wijnhaven");
        address.setBuildingnumber(16);
        address.setBuildingletter('C');

        Employee employee = new Employee();
        employee.setBsn(4);
        employee.setEmployeename("Donovan");
        employee.setSurname("Ninja");

        School school = new School();
        school.setSchoolid(1);
        school.setSchoolname("Hogeschool");
        school.setSchoollevel("HBO");

        DegreePK degreepk = new DegreePK();
        degreepk.setBsn(employee.getBsn());
        degreepk.setDegreeid(1);
        degreepk.setSchoolid(school.getSchoolid());

        Degree degree = new Degree();
        degree.setCoursename("Informatica");
        degree.setDegreePK(degreepk);
        degree.setEmployee(employee);
        degree.setSchool(school);

        WorkingAddressPK workingaddresspk = new WorkingAddressPK();
        workingaddresspk.setBsn(employee.getBsn());
        workingaddresspk.setCountry(addressPK.getCountry());
        workingaddresspk.setPostalcode(addressPK.getPostalcode());

        WorkingAddress workingaddress = new WorkingAddress();
        workingaddress.setWorkingAddressPK(workingaddresspk);
        workingaddress.setAddress(address);
        workingaddress.setEmployee(employee);



    

        //Project Side
        HeadquarterInfo headquarterinfo = new HeadquarterInfo();
        headquarterinfo.setHeadquarterid(1);
        headquarterinfo.setBuildingname("HES");
        headquarterinfo.setRoomamount(150);
        headquarterinfo.setMonthrent(44.75);
        
        Project project = new Project();
        project.setProjectid(2);
        project.setProjectname("Information");
        project.setBudget(35.75);
        project.setAllocatedhour(10);
        project.setCompanyname("Micrsoft");
        project.setHeadquarterid(headquarterinfo);
        

        
        PositieDescription positiedescription = new PositieDescription();
        
        positiedescription.setPositieid(25);
        positiedescription.setPositiename("Directeur");
        positiedescription.setPositiedescription("Benny");
        positiedescription.setAmountshours(40);
        positiedescription.setHourfee(2600);
        //positiedescription.set
        
        PositieEmployerPK positieemployerpk = new PositieEmployerPK();
        positieemployerpk.setBsn(employee.getBsn());
        positieemployerpk.setPositieid(positiedescription.getPositieid());
        
        
        PositieEmployer positieemployer = new PositieEmployer();
        positieemployer.setPositieEmployerPK(positieemployerpk);
        positieemployer.setEmployee(employee);
        positieemployer.setProjectid(project);
         positieemployer.setHeadquarterid(headquarterinfo);
         

         
        //  Opslaan tabelen in Database 
        
        ejc.create(employee);
        djc.create(degree);
       
        ajc.create(address); 
        sjc.create(school);

        wajc.create(workingaddress);
        
       hijc.create(headquarterinfo);
        pjc.create(project);
        pdjc.create(positiedescription);
  
       

    }

}
