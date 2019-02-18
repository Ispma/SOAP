package ServerInterface;


import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

// Указывается для style = SOAPBinding.Style.DOCUMENT
@SuppressWarnings("DefaultAnnotationParam")
//Доступ - тип кодирования файла.
@XmlAccessorType(XmlAccessType.FIELD)
@WebService(targetNamespace = "http://localhost:8080/")
//Переплёт - стиль отправки сообщений, может быть использованно RPC
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)

public interface MainInterface
{
    String SOAPResponse(Integer message);
    // В данном случае return - возвращает в клиент
    // Integer - получаешь, String отправляешь
    default Integer SOAPRequest(String message){return null;}
}
