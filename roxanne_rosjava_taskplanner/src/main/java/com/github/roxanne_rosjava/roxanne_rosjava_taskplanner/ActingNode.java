package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.lang.AgentTaskDescription;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.lang.Goal;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.PlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.control.acting.GoalOrientedActingAgent;
import com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform.ROSJavaPlatformProxy;
import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.topic.Subscriber;
import java.util.List;

/**
 *
 */
public class ActingNode extends AbstractNodeMain
{
    private static final String HOME = System.getenv("ROXANNE_HOME") != null ? System.getenv("ROXANNE_HOME") + "/" : "";
    private static final String PROPERTY_FILE =  HOME + "etc/agent.properties";

    private GoalOrientedActingAgent agent;          // timeline-based acting agent

    private Log log;

    /**
     *
     * @return
     */
    @Override
    public GraphName getDefaultNodeName() {
        // set node name
        return GraphName.of("/roxanne/acting");
    }

    /**
     *
     * @param connectedNode
     */
    @Override
    public void onStart(ConnectedNode connectedNode)
    {
        // set logger
        this.log = connectedNode.getLog();
        try
        {
	        // creating platform
	        this.log.info("Setting up platform...");
            // create ROSJava platform proxy
            PlatformProxy proxy = new ROSJavaPlatformProxy(connectedNode);
	        // creating acting agent
	        this.log.info("Setting up goal-oriented agent...");
            // create the acting agent
            this.agent = new GoalOrientedActingAgent(PROPERTY_FILE, proxy);
            // start the agent
            this.log.info("Starting agent...");
            // set acting agent
            this.agent.initialize();


            // create a subscriber to the goal input topic
            Subscriber<roxanne_rosjava_msgs.ActingGoal> subscriber = connectedNode.newSubscriber(
                    "roxanne/acting/goal",
                    roxanne_rosjava_msgs.ActingGoal._TYPE);

            // synchronous goal management
            subscriber.addMessageListener(new MessageListener<roxanne_rosjava_msgs.ActingGoal>()
            {
                @Override
                public void onNewMessage(roxanne_rosjava_msgs.ActingGoal message)
                {
                    // received input goal
                    //log.info("I have received a goal to plan for: \"" + message.getData() + "\"");
                    // create task descriptor
                    AgentTaskDescription goal = new AgentTaskDescription();

                    try
                    {
                        // notify goal to the agent
                        agent.buffer(goal);
                        // wait a response - blocking call
                        List<Goal> results = agent.getResults();
                        // print goal information
                        for (Goal result : results) {
                            // print some statistics
                            log.info("Completed goal " + result + ":\n" +
                                    "\t- Planing: " + result.getPlanningAttempts() + " attempts for a total time of " + (result.getTotalContingencyHandlingTime() / 1000) + " seconds\n" +
                                    "\t- Execution: " + result.getExecutionAttempts() + " + attempts for a total time of " + (result.getTotalExecutionTime() / 1000) + " seconds\n" +
                                    "\t- Contingency handling: " + result.getContingencyHandlingAttempts() + " attempts for a total tiem of " + (result.getTotalContingencyHandlingTime() / 1000) + " seconds");
                        }
                    }
                    catch (InterruptedException ex) {
                        // error
                        log.error("Interrupted acting process:\n" + ex.getMessage() + "\n");
                    }
                }
            });
        }
        catch (Exception ex) {
            // error
            this.log.error("Error while starting acting agent\n" + ex.getMessage() + "\n");
        }
    }

    /**
     *
     * @param node
     */
    @Override
    public void onShutdownComplete(Node node) {
        // stop acting agent if necessary
        if (this.agent != null)
        {
            try
            {
                // stop acting agent
                this.agent.stop();
                // clear internal data structures
                this.agent.clear();
            }
            catch (Exception ex) {
                this.log.error("Error while stopping acting agent:\n" + ex.getMessage() + "\n");
            }
            finally {
                // clear variable
                this.agent = null;
            }
        }
    }
}
