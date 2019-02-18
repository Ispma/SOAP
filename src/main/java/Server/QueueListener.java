package Server;

public interface QueueListener
{
        default void onInboundQueueChanged(){}
        default void onOutboundQueueChanged(){}
}
