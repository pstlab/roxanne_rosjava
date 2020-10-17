package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;


import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

/**
 *
 */
public class RoxanneFeedbackListener implements MessageListener<roxanne_rosjava_msgs.TokenExecutionFeedback>
{
    private RosJavaPlatformProxy proxy;                                                 // platform proxy
    private Subscriber<roxanne_rosjava_msgs.TokenExecutionFeedback> subscriber;         // topic subscriber

    /**
     *
     * @param proxy
     */
    protected RoxanneFeedbackListener(RosJavaPlatformProxy proxy) {
        this.proxy = proxy;
    }

    /**
     *
     * @return
     */
    public String getMessageType() {
        return roxanne_rosjava_msgs.TokenExecutionFeedback._TYPE;
    }



    /**
     *
     * @param message
     */
    @Override
    public void onNewMessage(roxanne_rosjava_msgs.TokenExecutionFeedback message) {

        /*
         * TODO
         */

    }



    /**
     *
     * @param topicName
     * @param node
     * @return
     */
    public Subscriber<roxanne_rosjava_msgs.TokenExecutionFeedback> createSubscriber(String topicName, ConnectedNode node) {

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
