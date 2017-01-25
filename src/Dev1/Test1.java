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
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        JFrame as = new Test3();
        as.show();
    }
}
