package gestionReq;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import requete.*;
import source.*;
public class Input {
   
    public void selectManage(Table tr,String [] request,ObjectOutputStream out){
        try {
            ArrayList<String> Allvalue;
            if (request[1].equals("*")) {
                Allvalue=SelectAll(tr, request);            
                out.writeObject(Allvalue);
                out.flush();    
            } 
            for (int i = 0; i < tr.getChamp().size(); i++) {
                if (request[1].equals(tr.getChamp().get(i).getName())) { 
                    Allvalue=Select(tr, request);
                    for (int j = 0; j < Allvalue.size(); j++) {
                        System.out.println(Allvalue.get(j));
                    }
                    out.writeObject(Allvalue);    
                }
            }    
            // out.writeObject("ohatra"); 
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public void giveRequest(Table tr,String [] request,ObjectOutputStream out){
        try{
            ArrayList<String> mess=new ArrayList<String>();
            mess.add("Done");
            if (request[0].equals("create")) {
                this.gestionCreation(request, tr);  
                out.writeObject(mess);
                out.flush();
            }                
            if (request[0].equals("insert")) {
                this.Insert(tr, request);
                out.writeObject(mess);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }


    public void Insert(Table tr, String[] request) {
        try {
        ArrayList<String> values=new ArrayList<String>();
        if (request[1].equals("into") && request[2].equals(tr.getNomTable()) && request[3].equals("value")) {
            for (int i = 5; i < request.length; i+=2) {
                values.add(request[i]);
            }
        }else{
            throw new Exception("Syntax Error");
        }
        Operation on=new Operation();
            on.Union(values, tr);
           // System.out.println(tr.getChamp().get(1).get(1));
            on.ecrireValeur(tr,"Data.txt");   
        } catch (Exception e) {
             e.printStackTrace();// TODO: handle exception
        }
    }

    public Table gestionCreation(String [] request,Table tab)throws Exception{
        ArrayList<String> values=new ArrayList<String>();
        ArrayList<Range> rang=new ArrayList<Range>();
        String nomtable=new String();
        if (request[0].equals("create") && request[1].equals("table") ) {
            nomtable=request[2];
            for (int i = 4; i < request.length; i+=2) {
                values.add(request[i]);
            } 
            for (int i = 0; i < values.size(); i++) {
                rang.add(new Range());
            }
        }else{
            throw new Exception("Syntax error");
        }
        tab.setNomTable(nomtable);
        Operation op=new Operation();
        String f=new String("Data.txt");
        op.GiveChampComponent(tab, rang, values);
        op.NomChampenFichier(f, tab);
        return tab;
    }

//SELECT id FROM  perso where id = 2

    public ArrayList<String> SelectAll(Table tab,String [] req)throws Exception{
        Operation on=new Operation();
        ArrayList<String> show=new ArrayList<String>();
        if (req.length==4) {
            if (req[0].equals("select") && req[1].equals("*") && req[2].equals("from")) {
                if (req[3].equals(tab.getNomTable())) {
                    show=on.projectionAll("Data.txt");
                }else{
                    throw new Exception("Syntax error");
                }
            }
        }
        show.add("everything");
    return show;
    }


    public ArrayList<String> Select(Table tab,String [] req)throws Exception{
        //SELECT id FROM  perso where id = 2
        ArrayList<String> champ=new ArrayList<String>();
        ArrayList<String> container=new ArrayList<>();
        int farany=tab.getChamp().size()*2;
        try {
            // on.projectionColumn(tab, champ); only one column  
            if (req.length==4 && req[req.length-1].equals(tab.getNomTable())){
                for (int index = 0; index <tab.getChamp().size(); index++) {
                    if(req[1].equals(tab.getChamp().get(index).getName())){
                        champ.add(req[1]);
                    }
                }
                container.add(tab.getNomTable());
                container.add(champ.get(0));
                container.add("one column");
            }  
            // on.projectionColumn(tab, champ);   many column whithout predicat
            if (req.length>4 && req.length<8) {
                for (int i = 1; i <farany ; i+=2) {
                    for (int k = 0; k < tab.getChamp().size(); k++) {
                        if (req[i].equals(tab.getChamp().get(k).getName()) && req[req.length-2].equals("from") && req[req.length-1].equals(tab.getNomTable())) {
                            champ.add(req[i]);          
                        }
                    }
                } 
                container.add(tab.getNomTable());
                for (int j = 0; j <champ.size(); j++) {
                    container.add(champ.get(j));
                }
                container.add("columns");
            }
            // on.projectPredicat(champ, tab,req[5], req[7]); all, one column whith a condition  
            if (req.length==8) {
                if (req[req.length-4].equals("where")) {
                    for (int index = 0; index <tab.getChamp().size(); index++) {
                        if(req[1].equals(tab.getChamp().get(index).getName())){
                            champ.add(req[1]);      
                        }   
                    }               
                }
                container.add(tab.getNomTable());
                container.add(champ.get(0)); 
                container.add(req[5]);
                container.add(req[7]);
                container.add("=");
                // on.projectPredicat(champ, tab,req[req.length-3], req[req.length-1]) champ recommanded
            }else if (req.length>8) {
                for (int i = 1; i <farany ; i+=2) {
                    for (int k = 0; k < tab.getChamp().size(); k++) {
                        if (req[i].equals(tab.getChamp().get(k).getName()) && req[req.length-6].equals("from") && req[req.length-5].equals(tab.getNomTable())) {
                            if (req[req.length-4].equals("where")) {
                                champ.add(req[i]);      
                            }       
                        }  
                    }
                }    
                container.add(tab.getNomTable());
                for (int i = 0; i <champ.size(); i++) {
                    container.add(champ.get(i));
                }
                container.add(req[req.length-3]);
                container.add(req[req.length-1]);
                container.add("=");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        // System.out.println(req[req.length-3]);
        // System.out.println(req[req.length-1]);
        // for (int i = 0; i < champ.size(); i++) {
        //     System.out.println(champ.get(i));   
        // }    
    return container;
    } 


}