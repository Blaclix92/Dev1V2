/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dev1;


import Controller.AddressJpaController;
import Controller.DegreeJpaController;
import Controller.EmployeeJpaController;
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
        JFrame as = new Test2();
        as.show();

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Dev1PU");
        EmployeeJpaController ejc = new EmployeeJpaController(emfactory);
        AddressJpaController ajc = new AddressJpaController(emfactory);
        WorkingAddressJpaController wajc = new WorkingAddressJpaController(emfactory);
        SchoolJpaController sjc = new SchoolJpaController(emfactory);
        DegreeJpaController djc = new DegreeJpaController(emfactory);
 
        //Employee Side
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
        employee.setBsn(5);
        employee.setEmployeename("Donovan");
        employee.setSurname("Ninja");

        School school = new School();
        school.setSchoolid(2);
        school.setSchoolname("Hogeschool");
        school.setSchoollevel("HBO");

        DegreePK degreepk = new DegreePK();
        degreepk.setBsn(employee.getBsn());
        degreepk.setDegreeid(2);
        degreepk.setSchoolid(school.getSchoolid());

        Degree degree = new Degree();
        degree.setCoursename("Informatica");
        degree.setDegreePK(degreepk);
        degree.setEmployee(employee);
        degree.setSchool(school);

        WorkingAddressPK workingaddresspk = new WorkingAddressPK();
        workingaddresspk.setBsn(employee.getBsn());
        workingaddresspk.setCountry("Rotterdam");
        workingaddresspk.setPostalcode("3042AZ");

        WorkingAddress workingaddress = new WorkingAddress();
        workingaddress.setAddress(address);
        workingaddress.setEmployee(employee);
            //workingaddress.setStartdate(01012012);
        //workingaddress.setStartdate(null);
        
        //Employee Side
        //Project Side
        HeadquarterInfo headquarterinfo = new HeadquarterInfo();
        headquarterinfo.setHeadquarterid(2);
        headquarterinfo.setBuildingname("HES");
        headquarterinfo.setRoomamount(150);
        headquarterinfo.setMonthrent(44.75);
        
        Project project = new Project();
        project.setProjectid(3);
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

        
        //Opslaan tabelen in Database Employee Side
        ejc.create(employee);
        ajc.create(address);
        wajc.create(workingaddress);
        sjc.create(school);
        djc.create(degree);
        //Opslaan tabelen in Database Employee Side
        //Opslaan tabelen in Database Project Side       
    }
}
