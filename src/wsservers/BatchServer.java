package wsservers;

import javax.xml.ws.Endpoint;

public class BatchServer
{
    public static final int PORT = 8003;
    public static final String NAME = "Batch";
    public static final String WSURL = "http://localhost:" + PORT + "/ws/" + NAME;
    
    public static void main(String[] args)
    {
        Endpoint.publish(WSURL, new BatchServerImpl());
        System.out.println("Batch server is running on port " + PORT);
        System.out.println("WSDL avaiable at " + WSURL + "?wsdl");
    }
}
