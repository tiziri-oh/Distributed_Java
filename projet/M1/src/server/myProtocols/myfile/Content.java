package server.myProtocols.myfile;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Content extends Remote{
	public String getBestMsg() throws RemoteException, Exception;

}
