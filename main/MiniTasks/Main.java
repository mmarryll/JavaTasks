package common.MiniTasks;

import java.io.File;

class Main {
    public static void main(String[] args) {
        while(true) {
            Server serverget = new Server();
            serverget.initServer(9527);
            System.out.println("Please wait");
            String getf = serverget.getFile();
            File f1 = new File(getf);
            f1.deleteOnExit();
            serverget.RPN();

            String trFile = serverget.transformFile();
            System.out.println("Done!");
            serverget.serverStop();
            Server serversend = new Server();
            serversend.initServer(9528);
            serversend.sendFile(trFile);
            File f2 = new File(trFile);
            f2.deleteOnExit();
            serversend.serverStop();
        }
    }
}