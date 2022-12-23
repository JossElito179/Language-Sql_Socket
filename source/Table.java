
package source;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

public class Table implements Serializable{

    String nomTable=new String(); 
    ArrayList<Range> champ=new ArrayList<Range>();

    public String getNomTable() {
        return nomTable;
    }

    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }

    public ArrayList<Range> getChamp() {
        return champ;
    }
    
    public void setChamp(ArrayList<Range> champ) {
        this.champ = champ;
    }



}