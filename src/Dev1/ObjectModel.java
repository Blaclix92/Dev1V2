/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dev1;

/**
 *
 * @author Benny
 */
public class ObjectModel {
 private int id;
 private int idsec;
 private int idthird;
 private String idFourth;
 private String idFifth;
 public String description;

    @Override
    public String toString() {
        //Een JList of JComboBox laat deze return waarde zien
        return description;
    }

    public int getId() {
        return id;
    }

    public int getIdsec() {
        return idsec;
    }

    public int getIdthird() {
        return idthird;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdsec(int idsec) {
        this.idsec = idsec;
    }

    public void setIdthird(int idthird) {
        this.idthird = idthird;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdFourth() {
        return idFourth;
    }

    public String getIdFifth() {
        return idFifth;
    }

    public void setIdFourth(String idFourth) {
        this.idFourth = idFourth;
    }

    public void setIdFifth(String idFifth) {
        this.idFifth = idFifth;
    }


   
}
