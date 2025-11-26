package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaPlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaCommandPublisher;
import it.cnr.istc.pst.platinum.ai.executive.pdb.ExecutionNode;
import it.cnr.istc.pst.platinum.control.lang.PlatformCommand;
import org.ros.node.ConnectedNode;
import roxanne_rosjava_msgs.TokenExecution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        // get the dispatched token of the plan
        ExecutionNode execNode = cmd.getNode();
        List<ExecutionNode> nList = new ArrayList<>();
        // get next token on the timeline
        ExecutionNode nToken = execNode.getNext();
        while (nToken != null && nList.size() < 3) {
            // add next token
            nList.add(nToken);
            // go ahead with the timeline
            nToken = nToken.getNext();
        }


        // create the token message associated with the node to be executed
        roxanne_rosjava_msgs.Token tk = this.createToken(connNode, execNode);

        // create message to dispatch
        roxanne_rosjava_msgs.TokenExecution msg = this.publisher.newMessage();
        // set id
        msg.setTokenId(cmd.getId());
        // set token message
        msg.setToken(tk);
        // set start command type
        msg.setCommandType(cmd.getCommandType());

        // prepare information about next tokens to execute in the future
        List<roxanne_rosjava_msgs.Token> tList = new ArrayList<>(nList.size());
        for (ExecutionNode node : nList) {
            // create token message
            tList.add(this.createToken(connNode, node));
        }

        // set future tokens
        msg.setNext(tList);

        // get message to dispatch
        return msg;
    }

    /**
     *
     * @param connNode
     * @param node
     * @return
     */
    private roxanne_rosjava_msgs.Token createToken(ConnectedNode connNode, ExecutionNode node) {

        // create message token
        roxanne_rosjava_msgs.Token tk = connNode.getTopicMessageFactory().
                newFromType(roxanne_rosjava_msgs.Token._TYPE);

        // sett id
        tk.setId(node.getId());
        // set component
        tk.setComponent(node.getComponent());
        // get ground signature
        String gSignature = node.getPredicate().getGroundSignature();
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
        tk.setStart(node.getStart());
        // set end time
        tk.setEnd(node.getEnd());
        // set duration
        tk.setDuration(node.getDuration());

        // get the created token message
        return tk;
    }
}
