package com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.ex.MessageMarshalingException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.ex.CommandPublisherException;
import it.cnr.istc.pst.platinum.control.lang.PlatformCommand;
import org.apache.commons.logging.Log;
import org.ros.internal.message.Message;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

/**
 *
 * @param <T>
 */
public abstract class RosJavaCommandPublisher<T extends Message> {

    protected Log logger;
    protected RosJavaPlatformProxy proxy;          // set platform proxy
    protected Publisher<T> publisher;              // topic publisher

    /**
     *
     * @param proxy
     */
    protected RosJavaCommandPublisher(RosJavaPlatformProxy proxy) {
        this.proxy = proxy;
        this.logger = this.proxy.getLogger();
    }

    /**
     *
     * @return
     */
    public abstract String getMessageType();

    /**
     *
     * @param node
     * @param cmd
     */
    public void publish(ConnectedNode node, PlatformCommand cmd)
            throws CommandPublisherException
    {
        try
        {
            // marshal platform command
            T msg = this.marshal(node, cmd);
            // publish message on topic
            this.publisher.publish(msg);
        }
        catch (MessageMarshalingException ex) {
            throw new CommandPublisherException(ex.getMessage());
        }
    }

    /**
     *
     * @param node
     * @param cmd
     * @return
     * @throws MessageMarshalingException
     */
    public abstract T marshal(ConnectedNode node, PlatformCommand cmd)
            throws MessageMarshalingException;

    /**
     *
     * @param topicName
     * @param node
     */
    public final void createPublisher(String topicName, ConnectedNode node) {
        // create message publisher
        this.publisher = node.newPublisher(
                topicName,
                this.getMessageType()
        );
    }
}
