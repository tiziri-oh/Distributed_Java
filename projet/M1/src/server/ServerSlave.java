package server;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import server.myProtocols.myfile.Content;

import java.util.Date;

public class ServerSlave {

	public static void main(String[] args) throws Exception{
		try {
			if(args.length == 0) {
				System.out.println("Error : please enter an url to the social network data file.");
				return;
			}
			ContentClass content = new ContentClass(args[0]);
			Registry registry = LocateRegistry.createRegistry(1099);
			UnicastRemoteObject.exportObject((Content)content, 1099);
			registry.bind("rmi://localhost/ContentDistant", (Content)content);
			Random rand = new Random(); 
			int randInt = 0, max = 3, min = 1;
			while(true) {
				randInt = rand.nextInt(max - min + 1) + min;
				Thread.sleep((long)( randInt * 1000));
				Date   date= new Date();
				content.read(date);
				content.update30Sec(date);
				content.computeBestMessages();
			}
		}catch (Exception e) {
			System.out.println(e);
		}
	}

}
