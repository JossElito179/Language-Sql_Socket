
package wera;

import java.io.IOException;
import java.util.*;

import gestionReq.Input;
import requete.*;
import source.*;


public class Main{

    public static void main(String[] args) {
    
    String []nom=new String[4];
    nom[0]="NomClient";
    nom[1]="Salaire";
    nom[2]="Mgr";
    nom[3]="dept";

   ArrayList<String> valeur=new ArrayList<String>();
valeur.add("Rasoa");
valeur.add("1123.00");
valeur.add("23");
valeur.add("20");

ArrayList<String> valeur2=new ArrayList<String>();
valeur2.add("Rakoto");
valeur2.add("1.00");
valeur2.add("30");
valeur2.add("10");

    ArrayList<ListCond> conditions=new ArrayList<ListCond>();
    ArrayList<Range> rg=new ArrayList<Range>();
    for (int index = 0; index < 4; index++) {
        rg.add(new Range());
    }
    Operation req=new Operation();
    ArrayList<String> nomChamp=new ArrayList<String>();
    nomChamp.add("name");
    // nomChamp.add("age");

    // String [] nomChamp=new String [3];
    // nomChamp[0]="name";
    // nomChamp[1]="age";
    // nomChamp[2]="id";
    try {
        Input in=new Input();
        Table Entreprise=new Table();
        //System.out.println(Entreprise.getNomTable());
       //req.GiveChampComponent(Entreprise, rg,valeur);
        req.makeObject("Data.txt", Entreprise);
        //in.giveRequest(Entreprise);
        //req.NomChampenFichier("Data.txt",Entreprise); 
        // req.Union(valeur,Entreprise); 
        // req.Union(valeur2, Entreprise);
        //req.ecrireValeur(Entreprise, "Data.txt");  
      //  req.projectionAll("Data.txt");
        //req.projectionColumn(Entreprise,nomChamp);
        System.out.println("perso");
        req.projectPredicat(nomChamp,"perso",Entreprise,"id","1");
        // req.projectEitherAnd(conditions,Entreprise,nomChamp);
        //System.out.println(Entreprise.getChamp().get(0).getName());
        } catch (Exception e) {
        e.printStackTrace();
    }

    }
}
