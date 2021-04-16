package com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform;

import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

/**
 *
 * @param <T>
 */
public abstract class RosJavaTopicListener<T> implements MessageListener<T> {

    protected RosJavaPlatformProxy proxy;           // platform proxy
    protected Subscriber<T> subscriber;             // topic subscriber

    /**
     *
     * @param proxy
     */
    protected RosJavaTopicListener(RosJavaPlatformProxy proxy) {
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
    public abstract void onNewMessage(T message) ;
}
