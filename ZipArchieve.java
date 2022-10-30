import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipArchieve
{
    public ZipArchieve() {}
    public static void Archiving(String filename , String directoryname) throws java.io.IOException {
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(directoryname));
            FileInputStream fis= new FileInputStream(filename);)
        {
            ZipEntry entry1=new ZipEntry(filename.substring(filename.lastIndexOf("\\") + 1));
            zout.putNextEntry(entry1);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void Dearchiving(String filename, String directoryname) throws java.io.IOException {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(filename));
        ZipEntry entry;
        while((entry = zin.getNextEntry()) != null) {
            FileOutputStream fout = new FileOutputStream(directoryname);
            for (int c = zin.read(); c != -1; c = zin.read()) {
                fout.write(c);
            }
            fout.flush();
            zin.closeEntry();
            fout.close();
        }
    }
}
