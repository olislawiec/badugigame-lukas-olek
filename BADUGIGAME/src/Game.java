import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

public class Game implements Runnable {
        // port,liczba graczy,liczba botow,stawka
        // poczatkowa,NazwaGracza1,NazwaGracza2...
        Server server;
        boolean wait=true;
        Random random = new Random();
        boolean c= true;
        ServerSocket socket_ser;

        public Game(Server server,int port) {
                this.server = server;
                try {
                         socket_ser = new ServerSocket(port);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                System.out.println("Server is run on port: "+ port);

        }

        public void run() {
               
                while(true)
                {
                        try {
                                new ThreatServer(socket_ser.accept(),Server.server).start();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                       
                       
                }
        }
        
        }
