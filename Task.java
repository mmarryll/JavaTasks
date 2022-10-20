import java.io.*;
import java.lang.*;
import java.util.Scanner;
import java.util.zip.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

public class Task {
    public static void main(String[] args) throws Exception {
        //Read and Write to text file
        FileInputStream fin;
        File file;
        PrintWriter pw;
        try {
            fin = new FileInputStream("input.txt");
            file = new File("output.txt");
            pw = new PrintWriter(file);
        } catch (FileNotFoundException exc) {
            System.out.println("Файл не найден");
            return;
        }
        Scanner input = new Scanner(fin);
        while (input.hasNext()) {
            String line = input.nextLine();
            var lst = line.split(" ");

            if (lst[1].endsWith("+")) {
                System.out.println((int) (Double.parseDouble(lst[0]) + Double.parseDouble(lst[2])));
                pw.println(Double.parseDouble(lst[0]) + Double.parseDouble(lst[2]));
            } else if (lst[1].endsWith("-")) {
                System.out.println((int) (Double.parseDouble(lst[0]) - Double.parseDouble(lst[2])));
                pw.println(Double.parseDouble(lst[0]) / Double.parseDouble(lst[2]));
            } else if (lst[1].endsWith("/")) {
                System.out.println(Double.parseDouble(lst[0]) / Double.parseDouble(lst[2]));
                pw.println(Double.parseDouble(lst[0]) / Double.parseDouble(lst[2]));
            } else {
                System.out.println(Double.parseDouble(lst[0]) * Double.parseDouble(lst[2]));
                pw.println(Double.parseDouble(lst[0]) * Double.parseDouble(lst[2]));

            }
        }
        pw.close();
//Make archive ZIP
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream("archive.zip"));
        File myfile = new File("folder");

        doZip(myfile, out);

        out.close();
//Make dearchive ZIP
        if (args.length != 1) {
            System.out.println("Usage: UnzipUtil [zipfile]");
            return;
        }

        File myfile_ = new File(args[0]);
        if (!file.exists() || !file.canRead()) {
            System.out.println("File cannot be read");
            return;
        }

        try {
            ZipFile zip = new ZipFile(args[0]);
            Enumeration entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println(entry.getName());

                if (entry.isDirectory()) {
                    new File(file.getParent(), entry.getName()).mkdirs();
                } else {
                    write(zip.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(
                                    new File(file.getParent(), entry.getName()))));
                }
            }

            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Criptography
        //Write JSON file and Read
        //Write Read XML file
        //Archive Rar

    }

    private static void doZip(File dir, ZipOutputStream out) throws IOException {
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
                doZip(f, out);
            else {
                out.putNextEntry(new ZipEntry(f.getPath()));
                write(new FileInputStream(f), out);
            }
        }

    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }
}
