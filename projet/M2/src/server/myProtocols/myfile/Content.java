package server.myProtocols.myfile;

import java.rmi.*;

public interface Content extends Remote{
	public String getBestMsg() throws RemoteException, Exception;
}
