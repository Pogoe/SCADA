package crud;

import data.ErrorTypes;
import data.Meassure;

public interface ISCADAControllerCRUD
{
    int storeError(ErrorTypes e);
    int storeData(Meassure m);
}
