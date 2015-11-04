package wsservers;

import crud.CRUD;
import crud.IMESCRUD;
import javax.jws.WebService;

@WebService(endpointInterface = "wsservers.IMesServer")
public class MesServerImpl implements IMesServer
{
    private IMESCRUD crud = CRUD.get();
    
    @Override
    public void storeTask()
    {
        crud.storeTask();
    }
}
