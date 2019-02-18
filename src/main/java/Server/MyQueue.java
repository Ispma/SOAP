package Server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyQueue
{

    private static ArrayList<QueueListener> listeners = new ArrayList<QueueListener>();
    private static int inID = -1;
    // Подобные массивы создаются для сохранения сообщений, на случай если входящих запросов будет больше чем выходящих
    private static LinkedHashMap<Integer, String> outboundQueue = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, String> inboundQueue = new LinkedHashMap<>();


    //Добавляем во входящие
    public static Integer addInbound(String message)
    {
        //Считаем ID сообщений
        inID++;
        System.out.println("addInbound() id = " + inID);
        inboundQueue.put(inID, message);
        // Сообщение слушателям о входящих
        notifyInboundQueueChanged();
        // Возвращает клиенту ID
        return inID;
    }
    //Очередь исходящих
    public static Integer addOutbound(Integer inID, String message) {
        outboundQueue.put(inID, message);
        System.out.println("addOutbound() id = " + inID);
        notifyOutboundQueueChanged();
        return inID;
    }

    //Берём сообщение из массива входящих для обработчика и удаляем
    public static Map.Entry<Integer, String> getInbound()
    {
        // entrySet() - берём и ключ и значение
        Map.Entry<Integer, String> entry = inboundQueue.entrySet().iterator().next();
        System.out.println("getInbound() id = " + entry.getKey());
        inboundQueue.remove(entry.getKey());
        return entry;
    }

    //Берём сообщение из массива исходящих  и удаляем
    public static String getOutbound(Integer inID) {
        // возвращает null если нет элемента с таким ключем
        return outboundQueue.remove(inID);
    }

    private static void notifyInboundQueueChanged()
    {
        for (QueueListener listener : listeners)
        {
            listener.onInboundQueueChanged();
        }
    }

    private static void notifyOutboundQueueChanged()
    {
        for (QueueListener listener : listeners){
            listener.onOutboundQueueChanged();
        }
    }

    static void addListener(QueueListener listener){
        listeners.add(listener);
        listeners.trimToSize();
    }
}
