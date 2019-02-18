package Server;

import ServerInterface.MainInterface;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.Endpoint;

// Указывается для style = SOAPBinding.Style.DOCUMENT
@SuppressWarnings("DefaultAnnotationParam")
//Доступ - тип кодирования файла.
@XmlAccessorType(XmlAccessType.FIELD)
@WebService(targetNamespace = "http://localhost:8080/")
//Переплёт - стиль отправки сообщений, может быть использованно RPC
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)

public class MainOfTheWeb implements MainInterface
{
    public static void main(String[] args)
    {
        // У класса Endpoint в методе .publish указывается класс(в данном случае вебсервис) который кидается на указанную ссылку
        Endpoint.publish("http://localhost:8080/", new MainOfTheWeb());
        System.out.println("service started!");
        // Добавляем слушатель
        MyQueue.addListener(new HandlerQueue());
        System.out.println("queue processor added to queue listeners");
    }

    // Тут обрабатываю запросы от клиентов
    @WebMethod
    public Integer SOAPRequest(String message) {
        // сообщение отправляется в очередь входящих
        // где вызывает событие "очередь входящих изменена"
        // это событие запускает обработчик очереди и далее обрабатывается
        System.out.println("addDataRequest inbound");

        Integer inID = MyQueue.addInbound(message);
        System.out.println("addDataRequest added id = " + inID);
        // Возвращает клиенту ID
        return inID;
    }

    //Сервер не обрабатывает ответы, так как клиент не отвечает, этот метод прописам в классе client
    @WebMethod
    public String SOAPResponse (Integer message)
    {
        return MyQueue.getOutbound(message);
    }
}
