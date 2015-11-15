package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IBatchExporter extends Remote
{
    void startOrder(Map<String, Double> order) throws RemoteException;
    void setTemp(double temp) throws RemoteException;
    void addMoist(int level) throws RemoteException;
    void addWater(int sec) throws RemoteException;
    void setRedLight(int level) throws RemoteException;
    void setBlueLight(int level) throws RemoteException;
    void setFanSpeed(int level) throws RemoteException;
    boolean isExecuting() throws RemoteException;
}
