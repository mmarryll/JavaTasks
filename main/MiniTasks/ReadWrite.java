package MiniTasks;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWrite implements IData {
    public ReadWrite() {
    }

    public void write(String filename, String str) {
        try {
            FileWriter fw = new FileWriter(filename, false);

            try {
                fw.write(str);
                fw.flush();
            } catch (Throwable var7) {
                try {
                    fw.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            fw.close();
        } catch (IOException var8) {
            System.out.println(var8.getMessage());
        }

    }
    public String read(String filename) {
        String str = "";

        try {
            FileReader fr = new FileReader(filename);

            int c;
            try {
                while((c = fr.read()) != -1) {
                    str = str + (char)c;
                }
            } catch (Throwable var7) {
                try {
                    fr.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            fr.close();
        } catch (IOException var8) {
            System.out.println(var8.getMessage());
        }

        return str;
    }

   
}

