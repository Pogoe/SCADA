package reciever;

import controller.Meassure;
import javax.jws.WebMethod;
import javax.jws.WebService;
import controller.ErrorTypes;

@WebService
public interface IReciever
{
    @WebMethod
    void acceptData(Meassure m);
    @WebMethod
    void acceptError(ErrorTypes e);
    @WebMethod
    byte[] getStatus();
}
