package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.myProtocols.myfile.Content;

public class Server {
	private final ServerSocket serverSocket;
	private final ExecutorService pool;
	
	public Server(int port, int poolSize) throws IOException{
		serverSocket = new ServerSocket(port);
		pool = Executors.newFixedThreadPool(poolSize);
	}
	
	public void start(String rmiHost) throws Exception{
		try {
			//Content doit se trouver dans le meme package que Content defini dans le serveur RMI
			//sinon une exception est levee par la methode lookup
			Content content = (Content) LocateRegistry.getRegistry(rmiHost, 1099).lookup("rmi://"+rmiHost+"/ContentDistant");
			while(true){
				pool.execute(new ClientHandler(serverSocket.accept(), content));
			}
		}catch (IOException e) {
			System.out.println(e);
			pool.shutdown();
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		try {
			String rmiHost = (args.length == 0) ? "localhost" : args[0];
			Server server = new Server(33333, 5);
			server.start(rmiHost);
			
		}catch (IOException ex) {
			System.out.println(ex);
		}
		
	}

}
