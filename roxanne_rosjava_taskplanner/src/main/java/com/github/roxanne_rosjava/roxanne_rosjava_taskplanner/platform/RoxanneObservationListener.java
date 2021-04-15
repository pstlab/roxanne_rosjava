package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;


import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

/**
 *
 */
public class RoxanneObservationListener implements MessageListener<roxanne_rosjava_msgs.Observation>
{
    private RosJavaPlatformProxy proxy;                                     // set platform proxy
    private Subscriber<roxanne_rosjava_msgs.Observation> subscriber;        // topic subscriber

    /**
     *
     * @param proxy
     */
    protected RoxanneObservationListener(RosJavaPlatformProxy proxy) {
        this.proxy = proxy;
    }


    /**
     *
     * @return
     */
    public String getMessageType() {
        return roxanne_rosjava_msgs.Observation._TYPE;
    }



    /**
     *
     * @param message
     */
    @Override
    public void onNewMessage(roxanne_rosjava_msgs.Observation message) {

        /*
         * TODO
         */
    }

    /**
     *
     * @param node
     * @return
     */
    public Subscriber<roxanne_rosjava_msgs.Observation> createSubscriber(String topicName, ConnectedNode node) {

        // subscribe to topic
        this.subscriber = node.newSubscriber(
                topicName,
                this.getMessageType());

        // add message listener
        this.subscriber.addMessageListener(this);
        // get subscriber
        return this.subscriber;
    }

}
