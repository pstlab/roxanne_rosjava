package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutionNode;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.PlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformCommand;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.ex.PlatformException;
import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ROSJava connector proxy
 *
 */
public class ROSJavaPlatformProxy extends PlatformProxy
{

    private static final String DISPATCHING_TOPIC = "/roxanne/acting/dispatching";
    private static final String FEEDBACK_TOPIC = "/roxanne/acting/feedback";
    private static final String OBSERVATION_TOPIC = "/roxanne/acting/observation";

    private ConnectedNode node;

    private Subscriber<roxanne_rosjava_msgs.TokenExecutionFeedback> fSubscriber;
    private Subscriber<roxanne_rosjava_msgs.Observation> oSubscriber;



    /**
     *
     * @param node
     */
    public ROSJavaPlatformProxy(ConnectedNode node) {
        super();
        // set connected node
        this.node = node;
    }

    /**
     *
     * @param cfgFile
     * @throws PlatformException
     */
    @Override
    public void initialize(String cfgFile)
            throws PlatformException
    {
        // subscribe to feedback topic
        this.fSubscriber = this.node.newSubscriber(
                FEEDBACK_TOPIC,
                roxanne_rosjava_msgs.TokenExecutionFeedback._TYPE);

        // set execution feedback listener
        fSubscriber.addMessageListener(new MessageListener<roxanne_rosjava_msgs.TokenExecutionFeedback>() {

            @Override
            public void onNewMessage(roxanne_rosjava_msgs.TokenExecutionFeedback message) {

                /*
                 * TODO
                 */
            }
        });

        // subscribe to observation topic
        this.oSubscriber =  this.node.newSubscriber(
                OBSERVATION_TOPIC,
                roxanne_rosjava_msgs.Observation._TYPE);
        // set observation listener
        oSubscriber.addMessageListener(new MessageListener<roxanne_rosjava_msgs.Observation>() {

            @Override
            public void onNewMessage(roxanne_rosjava_msgs.Observation observation) {

                /*
                 * TODO
                 */

            }
        });
    }

    /**
     *
     * @param node
     * @return
     */
    @Override
    public PlatformCommand executeNode(ExecutionNode node)
    {


        // create a message publisher
        Publisher<roxanne_rosjava_msgs.TokenExecution> publisher =
                this.node.newPublisher(
                        DISPATCHING_TOPIC,
                        roxanne_rosjava_msgs.TokenExecution._TYPE);

        // create message too dispatch
        roxanne_rosjava_msgs.TokenExecution msg = publisher.newMessage();
        // set data eg. msg.setData("x");
        // publish dispatched token
        publisher.publish(msg);

        return null;

    }

    /**
     *
     * @param node
     * @return
     */
    @Override
    public PlatformCommand startNode(ExecutionNode node)
    {
        // get command id
        int id = CMD_COUNTER.getAndIncrement();
        //  create platform command
        PlatformCommand cmd = new PlatformCommand("CMD_" + id, node);

        // create a message publisher
        Publisher<roxanne_rosjava_msgs.TokenExecution> publisher =
                this.node.newPublisher(
                        DISPATCHING_TOPIC,
                        roxanne_rosjava_msgs.TokenExecution._TYPE);

        // create message to dispatch
        roxanne_rosjava_msgs.TokenExecution msg = publisher.newMessage();
        // set id
        msg.setTokenId(id);
        // set component
        msg.setComponent(node.getComponent());
        // get ground signature
        String gSignature = node.getPredicate().getGroundSignature();
        // get pieces
        String[] splits = gSignature.split("-");
        // set predicate
        msg.setPredicate(splits[0]);
        // check parameters
        if (splits.length > 1) {
           // set  parameters
           msg.setParameters(Arrays.asList(Arrays.copyOfRange(splits, 1, splits.length)));
        }

        // set plan start  time
        msg.setPlanTime(node.getStart()[0]);
        // set start command type
        msg.setCommandType(1);

        // publish dispatched token
        publisher.publish(msg);

        // add command to dispatched index
        this.dispatchedIndex.put(cmd.getId(), cmd);
        // get dispatched command
        return cmd;
    }

    /**
     *
     * @param node
     */
    @Override
    public PlatformCommand stopNode(ExecutionNode node) {

        // get command id
        int id = CMD_COUNTER.getAndIncrement();
        //  create platform command
        PlatformCommand cmd = new PlatformCommand("CMD_" + id, node);

        // create a message publisher
        Publisher<roxanne_rosjava_msgs.TokenExecution> publisher =
                this.node.newPublisher(
                        DISPATCHING_TOPIC,
                        roxanne_rosjava_msgs.TokenExecution._TYPE);

        // create message to dispatch
        roxanne_rosjava_msgs.TokenExecution msg = publisher.newMessage();
        // set id
        msg.setTokenId(id);
        // set component
        msg.setComponent(node.getComponent());
        // get ground signature
        String gSignature = node.getPredicate().getGroundSignature();
        // get pieces
        String[] splits = gSignature.split("-");
        // set predicate
        msg.setPredicate(splits[0]);
        // check parameters
        if (splits.length > 1) {
            // set  parameters
            msg.setParameters(Arrays.asList(Arrays.copyOfRange(splits, 1, splits.length)));
        }

        // set plan start  time
        msg.setPlanTime(node.getStart()[0]);
        // set stop command type
        msg.setCommandType(0);

        // publish dispatched token
        publisher.publish(msg);

        // add command to dispatched index
        this.dispatchedIndex.put(cmd.getId(), cmd);
        // get dispatched command
        return cmd;
    }

    /**
     * The default behavior is to dispatch any node associated to the tokens
     * of primitive state variables
     *
     *
     * @param node
     * @return
     */
    @Override
    public boolean isPlatformCommand(ExecutionNode node) {
       // true as this method is called only for tokens of primitive state variables
       return true;
    }


}


