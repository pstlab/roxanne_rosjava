package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;


import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformCommand;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import java.util.Arrays;

/**
 *
 */
public class RoxanneTokenPublisher
{
    private RosJavaPlatformProxy proxy;                                     // set platform proxy
    private Publisher<roxanne_rosjava_msgs.TokenExecution> publisher;       // topic publisher

    /**
     *
     * @param proxy
     */
    protected RoxanneTokenPublisher(RosJavaPlatformProxy proxy) {
        this.proxy = proxy;
    }


    /**
     *
     * @return
     */
    public String getMessageType() {
        return roxanne_rosjava_msgs.TokenExecution._TYPE;
    }


    /**
     *
     * @param topicName
     * @param node
     */
    public void createPublisher(String topicName, ConnectedNode node) {
        // create message publisher
        this.publisher = node.newPublisher(
                topicName,
                this.getMessageType()
        );
    }

    /**
     *
     * @param connNode
     * @param cmd
     */
    public void doPublish(ConnectedNode connNode, PlatformCommand cmd)
    {
        // create message token
        roxanne_rosjava_msgs.Token tk = this.connNode.getTopicMessageFactory().
                newFromType(roxanne_rosjava_msgs.Token._TYPE);

        // sett id
        tk.setId(cmd.getNode().getId());
        // set component
        tk.setComponent(cmd.getNode().getComponent());
        // get ground signature
        String gSignature = cmd.getNode().getPredicate().getGroundSignature();
        // get pieces
        String[] splits = gSignature.split("-");
        // set predicate
        tk.setPredicate(splits[0]);
        // check parameters
        if (splits.length > 1) {
            // set  parameters
            tk.setParameters(Arrays.asList(Arrays.copyOfRange(splits, 1, splits.length)));
        }

        // set start  time
        tk.setStart(cmd.getNode().getStart());
        // set end time
        tk.setEnd(cmd.getNode().getEnd());
        // set duration
        tk.setDuration(cmd.getNode().getDuration());

        // create message to dispatch
        roxanne_rosjava_msgs.TokenExecution msg = this.publisher.newMessage();
        // set id
        msg.setTokenId(cmd.getId());
        // set token message
        msg.setToken(tk);
        // set start command type
        msg.setCommandType(cmd.getCommandType());

        // actually dispatch token
        this.publisher.publish(msg);
    }
}
