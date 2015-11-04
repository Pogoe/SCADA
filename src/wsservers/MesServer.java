package wsservers;

import javax.xml.ws.Endpoint;

public class MesServer
{
    public static final int PORT = 8001;
    public static final String NAME = "MES";
    public static final String WSURL = "http://localhost:" + PORT + "/ws/" + NAME;
    
    public static void main(String[] args)
    {
        Endpoint.publish(WSURL, new MesServerImpl());
        System.out.println("MES server is running on port " + PORT);
        System.out.println("WSDL avaiable at " + WSURL + "?wsdl");
    }
}
