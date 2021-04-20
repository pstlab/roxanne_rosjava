package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.acting.ActingAgentStatus;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.acting.GoalOrientedActingAgent;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.acting.ex.ActingAgentInitializationException;
import org.apache.commons.logging.Log;
import org.ros.exception.ServiceException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.service.ServiceResponseBuilder;
import roxanne_rosjava_msgs.ActingConfigurationServiceRequest;
import roxanne_rosjava_msgs.ActingConfigurationServiceResponse;

/**
 *
 */
public class ActingNode extends AbstractNodeMain
{
    private GoalOrientedActingAgent agent;

    private Log log;
    private ConnectedNode node;

    private String nodeName;
    private String propertyFilePath;

    /**
     *
     */
    public ActingNode() {
        this.nodeName = "/roxanne/acting";
    }

    /**
     *
     * @return
     */
    @Override
    public GraphName getDefaultNodeName() {
        // set node name
        return GraphName.of(this.nodeName);
    }

    /**
     *
     * @param connectedNode
     */
    @Override
    public void onStart(ConnectedNode connectedNode)
    {
        // set node
        this.node = connectedNode;
        // set logger
        this.log = connectedNode.getLog();
        // create service handler
        connectedNode.newServiceServer("/roxanne/acting/configuration",
                roxanne_rosjava_msgs.ActingConfigurationService._TYPE,
                new ServiceResponseBuilder<roxanne_rosjava_msgs.ActingConfigurationServiceRequest,
                        roxanne_rosjava_msgs.ActingConfigurationServiceResponse>() {

                    /**
                     *
                     * @param request
                     * @param response
                     * @throws ServiceException
                     */
                    @Override
                    public void build(ActingConfigurationServiceRequest request, ActingConfigurationServiceResponse response)
                            throws ServiceException {

                        // check request parameters
                        String path = request.getConfigFilePath();
                        if (path == null || path.equals("")) {
                            // set result code
                            response.setCode(0);
                            // missing parameter data
                            response.setMessage("Specify a full path to a valid configuration file for a ROXANNE acting node!");

                        } else {

                            // check agent
                            ActingAgentStatus status = agent == null ? null : agent.getStatus();
                            // check status
                            if (status == null || status.equals(ActingAgentStatus.OFFLINE) ||
                                    status.equals(ActingAgentStatus.RUNNING))  {

                                try {

                                    // initialize agent
                                    doInitializeAgent(node, path);
                                    // set result code
                                    response.setCode(1);
                                    // set message
                                    response.setMessage("Acting node successfully initialized");
                                }
                                catch (ActingAgentInitializationException ex) {
                                    // set response
                                    response.setCode(0);
                                    response.setMessage("Acting agent initialization error:\n" +
                                            "- Message= " + ex.getMessage() + "\n");
                                }
                            }
                            else {

                                // not ready for initialization
                                response.setCode(0);
                                response.setMessage("Agent in \"" + status.name() + "\" state currently and not ready for initialization.");

                            }
                        }
                    }
                });
    }

    /**
     *
     * @param node
     * @param path
     * @throws ActingAgentInitializationException
     */
    protected void doInitializeAgent(ConnectedNode node, String path)
            throws ActingAgentInitializationException
    {
        try
        {
            // creating acting agent
            this.log.info("Setting up goal-oriented agent using config file \"" + path + "\"...");
            // create the acting agent
            this.agent = new GoalOrientedActingAgent(path, node);
            // start the agent
            this.log.info("Starting agent...");
            // start   acting agent
            this.agent.start();
            // set acting agent
            this.agent.initialize();
            // ready to receive messages
            this.log.info("... agent ready to receive goals!");
            // set current property file path
            this.propertyFilePath = path;
        }
        catch (InterruptedException ex) {
            // initialization error
            throw new ActingAgentInitializationException(ex.getMessage());
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
