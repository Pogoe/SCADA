package wsservers;

import controller.SCADAController;
import crud.CRUD;
import crud.IMESCRUD;
import javax.jws.WebService;

@WebService(endpointInterface = "wsservers.IMesServer")
public class MesServerImpl implements IMesServer
{
    private SCADAController controller = new SCADAController();
    private IMESCRUD crud = CRUD.get();
    
    @Override
    public void storeOrder(String order)
    {
        crud.storeTask();
    }

    @Override
    public void executeOrder(String order)
    {
        controller.handleOrder(order);
    }
}
