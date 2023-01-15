package MiniTasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    private Socket socket;
    OutputStream ostr;
    InputStream istr;
    private String flag;
    private String filename;

    public Server() {
    }

    void RPN() {
        ReadWrite rw = new ReadWrite();
        String str = rw.read(this.filename);
        Pattern pattern = Pattern.compile("[()0-9]*( ){0,}([+-/*]( ){0,}[()0-9]{0,})*");
        Matcher m = pattern.matcher(str);

        while(m.find()) {
            String expression = m.group();
            if (!expression.equals("") && !expression.equals(" ")) {
                try {
                    str = str.replace(expression, Ideone.calc(ExpressionParser.parse(expression)));
                } catch (Exception var7) {
                }
            }
        }

        rw.write(this.filename, str);
    }

    String transformFile() {
        ReadWrite rw = new ReadWrite();
        create("new" + this.filename);
        rw.write("new" + this.filename, rw.read(this.filename));
        delete(this.filename);
        this.filename = "new" + this.filename;
        if (this.flag.equals("")) {
            return this.filename;
        } else {
            String command = null;

            while(true) {
                String str;
                while(true) {
                    ZipDecorator zip;
                    while(true) {
                        if (this.flag.equals("")) {
                            return this.filename;
                        }

                        command = this.flag.substring(0, 2);
                        this.flag = this.flag.substring(2);
                        EncryptionDecorator encryptionDecorator;
                        if (command.equals("Ee")) {
                            if (this.filename.contains(".zip")) {
                                delete(this.filename);
                                this.filename = this.filename.replaceAll(".zip", "");
                            }

                            encryptionDecorator = new EncryptionDecorator(rw);
                            str = rw.read(this.filename);
                            encryptionDecorator.write(this.filename, str);
                        }

                        if (command.equals("Ed")) {
                            if (this.filename.contains(".zip")) {
                                delete(this.filename);
                                this.filename = this.filename.replaceAll(".zip", "");
                            }

                            encryptionDecorator = new EncryptionDecorator(rw);
                            str = encryptionDecorator.read(this.filename);
                            rw.write(this.filename, str);
                        }

                        if (!command.equals("Zz")) {
                            break;
                        }

                        if (!this.filename.contains(".zip")) {
                            zip = new ZipDecorator(rw);
                            str = rw.read(this.filename);
                            zip.write(this.filename, str);
                            this.filename = this.filename + ".zip";
                            break;
                        }
                    }

                    if (!command.equals("Zu")) {
                        break;
                    }

                    if (this.filename.contains(".zip")) {
                        zip = new ZipDecorator(rw);
                        str = zip.read(this.filename);
                        rw.write(this.filename, str);
                        delete(this.filename);
                        this.filename = this.filename.replaceAll(".zip", "");
                        break;
                    }
                }

                CompressDecorator comp;
                if (command.equals("Cc")) {
                    if (this.filename.contains(".zip")) {
                        delete(this.filename);
                        this.filename = this.filename.replaceAll(".zip", "");
                    }

                    comp = new CompressDecorator(rw);
                    str = rw.read(this.filename);
                    comp.write(this.filename, str);
                }

                if (command.equals("Cd")) {
                    if (this.filename.contains(".zip")) {
                        delete(this.filename);
                        this.filename = this.filename.replaceAll(".zip", "");
                    }

                    comp = new CompressDecorator(rw);
                    str = comp.read(this.filename);
                    rw.write(this.filename, str);
                }
            }
        }
    }

    public void connectSocketServer(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            this.socket = server.accept();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        try {
            this.istr = this.socket.getInputStream();
            this.ostr = this.socket.getOutputStream();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public void initServer(int port) {
        while(this.socket == null) {
            try {
                this.socket = new Socket(InetAddress.getLocalHost(), port);
            } catch (IOException var3) {
            }
        }

        try {
            this.ostr = this.socket.getOutputStream();
            this.istr = this.socket.getInputStream();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void serverStop() {
        try {
            this.socket.close();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public String getFile() {
        byte[] b = new byte[1024];

        try {
            InputStream in = this.istr;
            DataInputStream din = new DataInputStream(new BufferedInputStream(in));
            this.filename = din.readLine();
            this.flag = din.readLine();
            File f = new File(this.filename);
            RandomAccessFile fw = new RandomAccessFile(f, "rw");

            for(int num = din.read(b); num != -1; num = din.read(b)) {
                fw.write(b, 0, num);
                fw.skipBytes(num);
            }

            din.close();
            fw.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return this.filename;
    }

    public void sendFile(String filename) {
        try {
            PrintWriter printWriter = new PrintWriter(this.ostr, true);
            printWriter.println(filename);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        byte[] b = new byte[1];
        File f = new File(filename);

        try {
            OutputStream dout = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
            InputStream ins = new FileInputStream(f);

            for(int n = ins.read(b); n != -1; n = ins.read(b)) {
                dout.write(b);
                dout.flush();
            }

            ins.close();
            dout.close();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

    }

    public static void create(String filename) {
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException var2) {
            System.out.println("An error occurred.");
            var2.printStackTrace();
        }

    }

    public static void delete(String filename) {
        File file = new File(filename);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }

    }
}

