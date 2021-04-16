package com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.ex.MessageUnmarshalingException;
import it.cnr.istc.pst.platinum.control.lang.PlatformObservation;
import org.ros.internal.message.Message;
import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

/**
 *
 * @param <T>
 * @param <D>
 */
public abstract class RosJavaObservationListener<T extends Message, D extends Object> implements MessageListener<T> {

    protected RosJavaPlatformProxy proxy;           // platform proxy
    protected Subscriber<T> subscriber;             // topic subscriber

    /**
     *
     * @param proxy
     */
    protected RosJavaObservationListener(RosJavaPlatformProxy proxy) {
        this.proxy = proxy;
    }

    /**
     *
     * @param topicName
     * @param node
     * @return
     */
    public final Subscriber<T> createSubscriber(String topicName, ConnectedNode node) {

        // subscribe to topic
        this.subscriber = node.newSubscriber(
                topicName,
                this.getMessageType());

        // add message listener
        this.subscriber.addMessageListener(this);
        // get subscriber
        return this.subscriber;
    }

    /**
     *
     * @return
     */
    public abstract String getMessageType();

    /**
     *
     * @param message
     */
    @Override
    public void onNewMessage(T message) {

        try {

            // get feedback from message
            PlatformObservation observation = this.unmarshal(message);
            // notify feedback or observation to platform subscribers
            this.proxy.notify(observation);
        }
        catch (MessageUnmarshalingException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     *
     * @param msg
     * @return
     * @throws MessageUnmarshalingException
     */
    public abstract PlatformObservation<D> unmarshal(T msg)
            throws MessageUnmarshalingException;

}
