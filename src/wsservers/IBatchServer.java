package wsservers;

import controller.Meassure;
import controller.ErrorTypes;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface IBatchServer
{
    @WebMethod void acceptData(Meassure m);
    @WebMethod void acceptError(ErrorTypes e);
    @WebMethod byte[] getStatus();
}
