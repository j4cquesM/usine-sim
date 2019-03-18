package parser;
import java.util.*;
import java.io.*;

public class CSVReader {

    private String inputFile;
    private static BufferedReader read;
    public int num_cols;
    public String header[];
    public ArrayList data;


    public CSVReader(String inputF) {
        this.setInputFile(inputF);
        this.data = new ArrayList();
    }
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void read() {
        File csv = new File(this.inputFile);
        try {
            read = new BufferedReader(new FileReader(csv));

            String line;
            String tokens[];
            LinkedHashMap occ = new LinkedHashMap();

            // cols
            line = read.readLine();
            this.header = line.split(";");
            this.num_cols = this.header.length;

            int i = 0;
            while ((line = read.readLine()) != null) {
                tokens = line.split(";");
                for (String token : tokens) { // probl�me avec le split � mon avis, il cr�e pas les cases "" pour des valeurs genre ok;ok;;;;;;
                    if(!token.equals(""))
                        occ.put(this.header[i], token);
                    //else
                    //occ.put(this.header[i],null);
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

    public ArrayList getData() {
        return this.data;

    }

    public int getDataSize() {
        return this.data.size();

    }

    public int getNum_cols() {
        return this.num_cols;
    }

    public String getFilename() {
        return this.inputFile;
    }

}
