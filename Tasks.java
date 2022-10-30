import java.awt.image.CropImageFilter;
import java.io.*;
import java.util.Scanner;
import java.util.zip.*;
import java.lang.*;

public class Tasks {
    public static void main(String[] args){

        // TXT
        FileInputStream fin;
        File file;
        PrintWriter pw;
        try {
            fin =new FileInputStream("src/in.txt");
            file = new File("src/out.txt");
            pw = new PrintWriter(file);
        }catch (FileNotFoundException exc){
            System.out.println("Файл не найден");
            return;
        }
        Scanner input = new Scanner(fin);
        while(input.hasNext()) {
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

        // Zip
        String inFileName = "C:\\Users\\37529\\IdeaProjects\\Tasks\\src\\in.txt";
        String outZipFileName = "C:\\Users\\37529\\IdeaProjects\\Tasks\\src\\outfromZip.txt";
        String wayName = "C:\\Users\\37529\\IdeaProjects\\Tasks\\src\\out.zip";
        ZipArchieve za = new ZipArchieve();
        za.Archiving(inFileName, wayName);
        za.Dearchiving(wayName, outZipFileName);

        //Cryptography
        String outputFileName = "C:\\Users\\37529\\IdeaProjects\\Tasks\\src\\out.txt";
        String encryptedFileName = "C:\\Users\\37529\\IdeaProjects\\Tasks\\src\\encrOut.txt";
        String decryptedFileName = "C:\\Users\\37529\\IdeaProjects\\Tasks\\src\\decrOut.txt";
        Criptography cr = new Criptography();
        cr.EncryptFile(outputFileName, encryptedFileName);
        cr.DecryptFile(encryptedFileName, decryptedFileName);

    }
}
