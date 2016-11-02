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
        JFrame as = new Test3();
        as.show();

//        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Dev1PU");
//        EmployeeJpaController ejc = new EmployeeJpaController(emfactory);
//        AddressJpaController ajc = new AddressJpaController(emfactory);
//        WorkingAddressJpaController wajc = new WorkingAddressJpaController(emfactory);
//        SchoolJpaController sjc = new SchoolJpaController(emfactory);
//        DegreeJpaController djc = new DegreeJpaController(emfactory);
//        
//        HeadquarterInfoJpaController hijc = new HeadquarterInfoJpaController(emfactory);
//        ProjectJpaController pjc = new ProjectJpaController(emfactory);
//        PositieEmployerJpaController pejc = new PositieEmployerJpaController(emfactory);
//        PositieDescriptionJpaController pdjc = new PositieDescriptionJpaController(emfactory);
 
        //Employee Side
        
//        AddressPK addressPK = new AddressPK();
//        addressPK.setCountry("Netherlands");
//        addressPK.setPostalcode("3041AJ");
//
//        Address address = new Address();
//        address.setAddressPK(addressPK);
//        address.setCity("Rotterdam");
//        address.setStreetname("Weena");
//        address.setBuildingnumber(17);
//        address.setBuildingletter('C');
//
//        Employee employee = new Employee();
//        employee.setBsn(7);
//        employee.setEmployeename("Mia");
//        employee.setSurname("Ninja");
//
//        School school = new School();
//        school.setSchoolid(5);
//        school.setSchoolname("Erasmus");
//        school.setSchoollevel("WO");
//
//        DegreePK degreepk = new DegreePK();
//        degreepk.setBsn(employee.getBsn());
//        degreepk.setDegreeid(5);
//        degreepk.setSchoolid(school.getSchoolid());
//
//        Degree degree = new Degree();
//        degree.setCoursename("Neijw");
//        degree.setDegreePK(degreepk);
//        degree.setEmployee(employee);
//        degree.setSchool(school);
//
//        WorkingAddressPK workingaddresspk = new WorkingAddressPK();
//        workingaddresspk.setBsn(employee.getBsn());
//        workingaddresspk.setCountry("Kralingen");
//        workingaddresspk.setPostalcode("3023AL");
//
//        WorkingAddress workingaddress = new WorkingAddress();
//        workingaddress.setAddress(address);
//        workingaddress.setEmployee(employee);
        
            //workingaddress.setStartdate(01012012);
        //workingaddress.setStartdate(null);
        
        //Employee Side
        //Project Side
        
//        ArrayList<Address> a = new ArrayList<>();
//        a.add(address);
//        HeadquarterInfo headquarterinfo = new HeadquarterInfo();
//        headquarterinfo.setHeadquarterid(5);
//        headquarterinfo.setBuildingname("ZPS");
//        headquarterinfo.setRoomamount(350);
//        headquarterinfo.setMonthrent(85.75);
//        headquarterinfo.setAddressCollection(a);
//        
//        
//        Project project = new Project();
//        project.setProjectid(6);
//        project.setProjectname("Yearlo");
//        project.setBudget(154.75);
//        project.setAllocatedhour(25);
//        project.setCompanyname("Nujioal");
//        project.setHeadquarterid(headquarterinfo);
//        
//        PositieDescription positiedescription = new PositieDescription();
//        positiedescription.setPositieid(25);
//        positiedescription.setPositiename("Worker");
//        positiedescription.setPositiedescription("Doing fun stuff");
//        positiedescription.setAmountshours(33);
//        positiedescription.setHourfee(2100);
//        //positiedescription.set
//        
//        PositieEmployerPK positieemployerpk = new PositieEmployerPK();
//        positieemployerpk.setBsn(employee.getBsn());
//        positieemployerpk.setPositieid(positiedescription.getPositieid());
//        
//        PositieEmployer positieemployer = new PositieEmployer();
//        positieemployer.setPositieEmployerPK(positieemployerpk);
//        positieemployer.setPositieDescription(positiedescription);
//        positieemployer.setEmployee(employee);
//        positieemployer.setProjectid(project);
//        positieemployer.setHeadquarterid(headquarterinfo);

        
        //Opslaan tabelen in Database Employee Side
        
//        ejc.create(employee);
//        ajc.create(address);
//        wajc.create(workingaddress);
//        sjc.create(school);
//        djc.create(degree);
        
        //Opslaan tabelen in Database Employee Side
        //Opslaan tabelen in Database Project Side
        
//        hijc.create(headquarterinfo);
//        pjc.create(project);
//        pdjc.create(positiedescription);
//        pejc.create(positieemployer);
    }
}
