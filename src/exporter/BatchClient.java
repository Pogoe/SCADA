package exporter;

public class BatchClient
{
    private SCADAServerImplService service;
    private ISCADAServer server;
    
    public BatchClient()
    {
        service = new SCADAServerImplService();
        server = service.getSCADAServerImplPort();
    }
    
    public void setTemperature(int kelvin)
    {
        server.setTemperature(kelvin);
    }
    
    public void setMoistrue(int level)
    {
        server.setMoisture(level);
    }
    
    public void setRedLight(int level)
    {
        server.setRedLight(level);
    }
    
    public void setBlueLight(int level)
    {
        server.setBlueLight(level);
    }
    
    public void addWater(int sec)
    {
        server.addWater(sec);
    }
    
    public void addFertilizer(int sec)
    {
        server.addFertilizer(sec);
    }
    
    public void addCO2(int sec)
    {
        server.addCO2(sec);
    }
    
    public void executeOrder(String order)
    {
        server.executeOrder(order);
    }
}
