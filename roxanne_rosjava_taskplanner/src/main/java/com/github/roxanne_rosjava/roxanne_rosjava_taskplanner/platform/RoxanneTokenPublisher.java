package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaPlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaCommandPublisher;
import it.cnr.istc.pst.platinum.control.lang.PlatformCommand;
import org.ros.node.ConnectedNode;
import roxanne_rosjava_msgs.TokenExecution;

import java.util.Arrays;

/**
 *
 */
public class RoxanneTokenPublisher extends RosJavaCommandPublisher<TokenExecution>
{


    /**
     *
     * @param proxy
     */
    protected RoxanneTokenPublisher(RosJavaPlatformProxy proxy) {
        super(proxy);
    }


    /**
     *
     * @return
     */
    @Override
    public String getMessageType() {
        return roxanne_rosjava_msgs.TokenExecution._TYPE;
    }

    /**
     *
     * @param connNode
     * @param cmd
     */
    @Override
    public roxanne_rosjava_msgs.TokenExecution marshal(ConnectedNode connNode, PlatformCommand cmd)
    {
        // create message token
        roxanne_rosjava_msgs.Token tk = connNode.getTopicMessageFactory().
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
        // get message to dispatch
        return msg;
    }
}
