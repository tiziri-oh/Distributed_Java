package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Client {

	public static void main(String[] args) {
		Socket connexion = null;
		try {
			//la connexion du client vers le serveur
			String serverHost = (args.length == 0) ? "localhost" : args[0];
			connexion = new Socket(serverHost, 33333);
			//la lecture du stream envoyé par le serveur
			BufferedReader input = new BufferedReader(new InputStreamReader(connexion.getInputStream(), "8859_1"), 1024);
			StringBuffer sb = new StringBuffer();
			int c;
			while((c = input.read()) != -1) {
				sb.append((char) c);
			}
			System.out.println(sb);
		}catch (IOException e) {
			System.out.println(e);
		}
		finally {
			try {
				if(connexion != null) {
					connexion.close();
				}
			}catch (IOException e) {
				System.out.println(e);
			}
		}

	}

}
