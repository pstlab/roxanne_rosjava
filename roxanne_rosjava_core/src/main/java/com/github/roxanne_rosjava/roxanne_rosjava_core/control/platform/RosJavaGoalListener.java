package com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.ex.MessageUnmarshalingException;
import it.cnr.istc.pst.platinum.control.lang.AgentTaskDescription;
import it.cnr.istc.pst.platinum.control.lang.PlatformFeedback;
import org.apache.commons.logging.Log;
import org.ros.internal.message.Message;
import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

/**
 *
 * @param <T>
 */
public abstract class RosJavaGoalListener<T extends Message> implements MessageListener<T> {

    protected Log log;
    protected RosJavaPlatformProxy proxy;           // platform proxy
    protected Subscriber<T> subscriber;             // topic subscriber

    /**
     *
     * @param proxy
     */
    protected RosJavaGoalListener(RosJavaPlatformProxy proxy) {
        // set proxy
        this.proxy = proxy;
        // set logger
        this.log = this.proxy.getLogger();
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
            AgentTaskDescription task = this.unmarshal(message);
            // notify feedback or observation to platform subscribers
            this.proxy.notify(task);
        }
        catch (MessageUnmarshalingException ex) {
            // error message
            System.err.println(ex.getMessage());
        }
    }

    /**
     *
     * @param msg
     * @return
     * @throws MessageUnmarshalingException
     */
    public abstract AgentTaskDescription unmarshal(T msg)
            throws MessageUnmarshalingException;

}
