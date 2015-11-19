package crud;

import data.BatchError;
import data.Meassure;
import java.util.Date;
import java.util.List;

public interface ISCADAControllerCRUD
{
    int storeError(BatchError e);
    int storeData(Meassure m);
    List<BatchError> getDailyErrors(Date from, Date to);
}
