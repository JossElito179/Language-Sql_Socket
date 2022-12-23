package requete;
import java.util.ArrayList;
import java.io.*;
import source.*;

public class Operation {

    public Operation() {}

    public void soratana(String ts,Table tb) throws IOException{
        FileWriter file=new FileWriter("Data.txt",true);
            file.append(ts +" | ");
        file.close();
    }
    
    public void sauteLine(String nomFichier){
        try {
            FileWriter file=new FileWriter(nomFichier,true);
            file.append("\n");
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //give champ'name as create a relation with column
    public void GiveChampComponent(Table relation,ArrayList<Range> tab  ,ArrayList<String> nom) throws IOException{
        for(int i=0; i<tab.size();i++){
            relation.getChamp().add(tab.get(i));
            relation.getChamp().get(i).setName(nom.get(i));
        }
    }

public void writeName(Table tab,String nomFichier) {
try {
    FileWriter write=new FileWriter(nomFichier,true);
    write.append(tab.getNomTable());
    write.close();   
} catch (Exception e) {
    e.printStackTrace();
}
}

    public void NomChampenFichier(String nomFichier,Table tab){
       try {
            writeName(tab, nomFichier);
            sauteLine(nomFichier);
            for (int i = 0; i < tab.getChamp().size(); i++) {
                soratana(tab.getChamp().get(i).getName(),tab); 
            } 
       } catch (Exception e) {
        e.printStackTrace();
       } 
    }

    //Union 
        public void Union(ArrayList<String> valeur,Table tab)throws Exception{
            if (valeur.size()==tab.getChamp().size()) {
                for (int i = 0; i < tab.getChamp().size(); i++) {
                    tab.getChamp().get(i).add(valeur.get(i));
                }   
            }else{
                throw new Exception("Review the Column length");
            }
        }

        public void ecrireFichier(String nomFichier, ArrayList<String> lignes){
            Writer fluxSortie=null;
            try{
                fluxSortie = new PrintWriter(new BufferedWriter(new FileWriter(nomFichier,true)));
                    fluxSortie.write(lignes.get(lignes.size()-1) +" | ");   
                }
            catch(IOException exc){
                exc.printStackTrace();
            }
            try{
                fluxSortie.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

public void ecrireValeur(Table tab,String nomFichier){
    sauteLine(nomFichier);
    for (int j = 0; j < tab.getChamp().size(); j++) {
        ecrireFichier(nomFichier, tab.getChamp().get(j));   
    }
}

        //Projection in all
        public ArrayList<String> projectionAll(String nomFichier){
        ArrayList<String> valeur =lireFichier(nomFichier);
            return valeur;
        }

        //Projection with some Champ name  
        public void projectionColumn(Table relation,String nom, ArrayList<String> champ)throws Exception{
            if (nom.equals(relation.getNomTable())) {
                ArrayList<Range> ran=new ArrayList<Range>();
            for (int k=0;k<champ.size();k++) {
                for ( int i = 0; i < relation.getChamp().size(); i++) {
                    if (champ.get(k).equals(relation.getChamp().get(i).getName())) {
                        ran.add(relation.getChamp().get(i));    
                    }
                }
            }   
            for (int i = 0; i < ran.size(); i++) {
                System.out.print(ran.get(i).getName() +" | ");
                for (int j = 0; j < ran.get(i).size(); j++) {
                    System.out.print(ran.get(i).get(j)+ " | ");
                }
                System.out.print("\n");
            }
            }
        } 

public void makeObject(String nomFichier,Table relation){
    ArrayList<String> mots=this.lireFichier(nomFichier);
    String [][] attr=new String [mots.size()][30];
    ArrayList<ListCond> content=new ArrayList<ListCond>();
    ArrayList<Range> column=new ArrayList<Range>();
    String regex=" ";
    for (int index = 0; index < mots.size(); index++) {
        content.add(new ListCond());
    }
    if (mots.size()>0) {
        relation.setNomTable( mots.get(0));
        for (int i = 1; i < mots.size(); i++) {
            attr[i]=mots.get(i).split(regex);
        }
        for (int i = 0; i < attr[1].length; i+=2) {
            content.get(1).add(attr[1][i]); 
            column.add(new Range());  
        }
        try {
            GiveChampComponent(relation, column, content.get(1));
        } catch (Exception e) {
           e.printStackTrace();
        }
        if (mots.size()>2) {
            for (int j = 2; j <attr.length; j++) {
                for (int i = 0; i < attr[j].length; i+=2) {
                    content.get(j).add(attr[j][i]);
                }
                 try {
                    Union(content.get(j), relation);   
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }   
        }        
    }
}

        public ArrayList<String> lireFichier(String nomFichier){
            BufferedReader fluxEntree=null;
            String ligneLue;
            ArrayList<String> lignes = new ArrayList<String>();
            try{
                fluxEntree = new BufferedReader(new FileReader(nomFichier));
                ligneLue = fluxEntree.readLine();
                while(ligneLue!=null){
                    lignes.add(ligneLue);
                    ligneLue = fluxEntree.readLine();
                }
            }
            catch(IOException exc){
                exc.printStackTrace();
            }
            try{
                fluxEntree.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return lignes;
        }


    public void projectPredicat(ArrayList<String> nomChamp,String nomTable,Table tab, String condition, String valeur) {
        ArrayList<Integer> indice=new ArrayList<Integer>();   
        if (nomTable.equals(tab.getNomTable())) {
            for (int i = 0; i < tab.getChamp().size(); i++) {
                if (condition.equals(tab.getChamp().get(i).getName())) {
                    //System.out.println(tab.getChamp().get(i).getName());
                    for (int j = 0; j < tab.getChamp().get(i).size(); j++) {
                        if (tab.getChamp().get(i).get(j).equals(valeur)) {
                           //System.out.println(tab.getChamp().get(i).get(j));
                           indice.add(Integer.valueOf(j));
                        }
                    }   
                }
            }
             int [] table=new int[nomChamp.size()];
            System.out.print("\n");
            for ( int y=0 ; y < nomChamp.size() ; y++) {
                for (int i = 0; i < tab.getChamp().size(); i++) {
                    if (nomChamp.get(y).equals(tab.getChamp().get(i).getName())) {
                        table[y]=i;
                        System.out.print(tab.getChamp().get(i).getName()+" | ");
                    }
                }   
            }
            System.out.println("");
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < indice.size(); j++) {
                    System.out.print(tab.getChamp().get(table[i]).get(indice.get(j))+" | " ); 
                }
            }            
        }
    }

    //condiion multiple id = 2 && age =19
    //get indice du Premier condition && indice du deuxieme 
    //comparaison 
    public void projectEitherAnd(ArrayList<ListCond> conditions, Table entreprise, String[] nomChamp) {
        int [] indiceVal=new int[nomChamp.length];
        int [] indiceChamp=new int[nomChamp.length];
        for (int i = 0; i < conditions.size(); i++) {
            for (int j = 0; j < entreprise.getChamp().size(); j++) {
                if (entreprise.getChamp().get(j).getName()==conditions.get(i).get(1)) {
                    indiceChamp[j]=j;
                    for (int j2 = 0; j2 <entreprise.getChamp().get(j).size() ; j2++) {
                        if (entreprise.getChamp().get(j).get(j2)==conditions.get(i).get(2)) {
                            indiceVal[j2]=j2;
                        }   
                    }
                }
            }
        }
    }

}
