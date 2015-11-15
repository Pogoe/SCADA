package server;

import data.BatchError;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import order.Order;

public interface IMesReciever extends Remote
{
    boolean queueOrder(Order o) throws RemoteException;
    List<BatchError> getDailyErrors(Date date) throws RemoteException;
    int getMaxCapacity() throws RemoteException;
    int getCurrentCapacity() throws RemoteException;
    int getRemovedUnits() throws RemoteException;
    int getHarvestedUnits() throws RemoteException;
}
