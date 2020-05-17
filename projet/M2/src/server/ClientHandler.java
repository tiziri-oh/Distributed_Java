package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.io.*;
import javax.xml.bind.*;

import server.myProtocols.myfile.Content;

public class ClientHandler implements Runnable {
	private final Socket socketClient;
	private final Content content;
	
	public ClientHandler(Socket socketClient, Content content) {
		this.socketClient = socketClient;
		this.content = content;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Connection Client");
			OutputStream out = socketClient.getOutputStream();

			String xmlStringMessages = "";
			//la reference content etant la meme, se bloc peut etre proteger de la concurent en utilisant synchronized
			synchronized (content) {
				xmlStringMessages = content.getBestMsg(); //appel objet distant via RMIs
			}
			System.out.println("Meuilleurs messages XML : \n" + xmlStringMessages);

			//convertir XML String en Objet Java
			StringReader sr     = new StringReader(xmlStringMessages);
			JAXBContext context = JAXBContext.newInstance(BestMessages.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			BestMessages bestMessages     = (BestMessages) unmarshaller.unmarshal(sr);
			
			//Envoi du message décodé au client
			System.out.println("Envoi vers le client du message décodé : " + bestMessages);
			out.write(bestMessages.toString().getBytes());
			out.flush();
			socketClient.shutdownOutput();
			System.out.println("Déconnection Client\n");
		}catch (SocketException e) {
			System.out.println(e);
		}
		catch (IOException e) {
			System.out.println(e);
		}catch (Exception e) {
			System.out.println(e);
		}
	}

}
