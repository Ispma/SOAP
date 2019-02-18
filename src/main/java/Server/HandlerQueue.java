package Server;

import java.util.Map;

// Класс обработчик
public class HandlerQueue implements QueueListener, Runnable
{

    @Override
    public void onInboundQueueChanged()
    {
        // this - означает - этот класс
        new Thread(this).start();
    }

    // ОБРАБОТКА ОЧЕРЕДИ
    @Override
    public void run()
    {
        // берём сообщение из массива полученых запросов с помощью .getInbound()
        Map.Entry<Integer, String>  inboundEntry= MyQueue.getInbound();
        String dataOut= "";
        if (inboundEntry.getValue().equals("INN")){
            dataOut = "7731000101923";
        } else {
            dataOut = "error";
        }
        // переводим в массив отправляемых писем
        MyQueue.addOutbound(inboundEntry.getKey(), dataOut);
    }

}
