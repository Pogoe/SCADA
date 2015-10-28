package hmi;

public class GUIController
{
    private static GUIController instance;
    
    public static GUIController get()
    {
        if(instance == null)
        {
            instance = new GUIController();
        }
        return instance;
    }
    
    
}
