package Client;

import ServerInterface.MainInterface;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.*;
import java.net.URL;

public class Client
{

    public static void main(String[] args) throws IOException
    {
        URL wsdlURL = new URL("http://localhost:8080/?wsdl");
        QName qNameService = new QName("http://localhost:8080/", "MainOfTheWebService");
        QName qNamePort = new QName("http://localhost:8080/", "MainOfTheWebPort");
        Service service = Service.create(wsdlURL, qNameService); //Что значит create???
        System.out.println("trying to connect");
        //Вместо слова this, который передаёт данный класс в параметры мы передаём класс MainInterface.class
        MainInterface ps= service.getPort(qNamePort, MainInterface.class);
        System.out.println("connected");
        Client client= new Client();
        Integer i = client.send(ps);
        client.poll(ps, i);
    }

    private Integer send(MainInterface ps)
    {
        String data = "INN";

        //тут формируется сообщение
        Integer requestID= ps.SOAPRequest(data);

        System.out.println("sent!");
        return requestID;
    }

    private Integer poll(MainInterface ps, Integer requestID)
    {
        String response= null;
        while ( response == null )
        {
            System.out.println("делаем запросы");
            response= ps.SOAPResponse(requestID);
            if( response != null)
            {
                break;
            }
        }
        System.out.println(response);
        return null;
    }

}
