package Entity;
import java.lang.*;
import java.io.*;
import java.util.*;
import DoctorFrame.*;

public class Doctor {
    private String id;
    private String name;

    File file;
    FileWriter fw;
    Scanner sc;

    public Doctor() {
        this.name="";
    }

    public Doctor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    public String getId() { return id; }
    public String getName() { return name; }

    public void insertDoctor() {
        try {
            file = new File("./DoctorData.txt");
            file.createNewFile();
            fw = new FileWriter(file, true);
            fw.write(getId() + "\t" + getName() + "\n");
            fw.flush();
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public boolean getDoctor(String id, String name) {
        boolean flag = false;
        file = new File("./DoctorData.txt");
        try {
            
            sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split("\t");
                if (parts[0].equals(id) && parts[1].equals(name)) {
                    flag = true;
                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();

}
return flag;
    }
}