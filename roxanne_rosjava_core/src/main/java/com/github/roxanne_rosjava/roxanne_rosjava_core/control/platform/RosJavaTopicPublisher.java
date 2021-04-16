package com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform;

import it.cnr.istc.pst.platinum.control.lang.PlatformCommand;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

/**
 *
 * @param <T>
 */
public abstract class RosJavaTopicPublisher<T> {

    protected RosJavaPlatformProxy proxy;          // set platform proxy
    protected Publisher<T> publisher;              // topic publisher

    /**
     *
     * @param proxy
     */
    protected RosJavaTopicPublisher(RosJavaPlatformProxy proxy) {
        this.proxy = proxy;
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
    public abstract void publish(ConnectedNode node, PlatformCommand cmd);

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
