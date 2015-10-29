package reciever;

import controller.Meassure;
import controller.SCADAController;
import controller.ErrorTypes;
import crud.*;

public class BatchReciever implements IBatchReciever
{
    private IBatchCRUD crud = CRUD.get();
    private SCADAController sController = new SCADAController();

    @Override
    public void acceptData(Meassure m)
    {
        new Thread(() ->
        {
            synchronized (crud)
            {
                crud.storeData(m);
            }
            synchronized (sController)
            {
                sController.handleData(m);
            }
        }).start();

    }

    @Override
    public void acceptError(ErrorTypes e)
    {
        new Thread(() ->
        {
            synchronized (sController)
            {
                sController.handleError(e);
            }
        }).start();
    }

    @Override
    public byte[] getStatus()
    {
        return null;
    }

}
