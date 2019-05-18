package parser;

import java.util.*;
import java.io.*;


/**
 * Le rôle de cette classe est de lire le contenu des fichiers CSV
 */
public class CSVReader {


    private static BufferedReader read;
    public int num_cols;
    public String header[];
    public ArrayList data;
    private String inputFile;


    /**
     * @param inputF correspond au nom du fichier
     */
    public CSVReader(String inputF) {
        this.setInputFile(inputF);
        this.data = new ArrayList();
    }

    /**
     * Modifie la variable inputFile
     * @param inputFile correspond au nom du fichier
     */
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Lis et tokenize le fichier CSV (transformation en objet Java utilisé après pour parser et serialiser
     */
    public void read() {
        File csv = new File(this.inputFile);
        try {
            read = new BufferedReader(new FileReader(csv));

            String line;
            String tokens[];
            LinkedHashMap occ = new LinkedHashMap();

            // récupère les headers (nom des colonnes).
            line = read.readLine();
            this.header = line.split(";");
            this.num_cols = this.header.length;

            // Transformation de la matrice sous forme de token (= un tuple).
            int i = 0;
            while ((line = read.readLine()) != null) {
                tokens = line.split(";");
                for (String token : tokens) {
                    if (!token.equals(""))
                        occ.put(this.header[i], token);
                    i++;
                }
                data.add(occ);
                occ = new LinkedHashMap();
                i = 0;

            }
            read.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @return Retourne le tableau data
     */
    public ArrayList getData() {
        return this.data;

    }

    /**
     * @return Retourne la taille du tableau data
     */
    public int getDataSize() {
        return this.data.size();

    }

    /**
     * @return Retourne le nombre de colonne du tableau
     */
    public int getNum_cols() {
        return this.num_cols;
    }

    /**
     * @return Retourne le nom du fichier lu
     */
    public String getFilename() {
        return this.inputFile;
    }

}
