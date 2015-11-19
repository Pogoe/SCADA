package server;

import batchserver.IBatchExporter;
import data.BatchError;
import data.Meassure;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBatchReciever extends Remote
{
    void sendTemp1(Meassure temp) throws RemoteException;
    void sendTemp2(Meassure temp) throws RemoteException;
    void sendMoist(Meassure moist) throws RemoteException;
    void sendWaterLevel(Meassure level) throws RemoteException;
    void sendError(BatchError error) throws RemoteException;
    void connectToServer(IBatchExporter bc);
}
