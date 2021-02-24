package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;

import it.cnr.istc.pst.platinum.ai.executive.pdb.ExecutionNode;
import it.cnr.istc.pst.platinum.control.lang.PlatformCommand;
import it.cnr.istc.pst.platinum.control.lang.ex.PlatformException;
import it.cnr.istc.pst.platinum.control.platform.PlatformProxy;
import org.ros.node.ConnectedNode;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Roxanne ROSJava connector proxy
 *
 */
public class RosJavaPlatformProxy extends PlatformProxy
{
    private ConnectedNode connNode;

    private Map<String, String> command2dispatchTopic;	            // map platform commands to ROS dispatch topics
    private Map<String, RoxanneTokenPublisher> topic2publisher;	    // map ROS topic to publisher
    private Set<String> subscribedTopics;                           // subscribed topics


    /**
     *
     * @param node
     */
    protected RosJavaPlatformProxy(ConnectedNode node) {
        super();
        // set connected node
        this.connNode = node;
        // initialize data structures
        this.command2dispatchTopic = new HashMap<>();
        this.topic2publisher = new HashMap<>();
        this.subscribedTopics = new HashSet<>();

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
        // set observation counter
        obsIdCounter.set(0);
        cmdIdCounter.set(0);

        // clear data structures
        this.command2dispatchTopic.clear();
        this.topic2publisher.clear();
        this.dispatchedIndex.clear();
        this.subscribedTopics.clear();

        try
        {
            // parse platform configuration from file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // parse XML document
            Document document = builder.parse(new FileInputStream(cfgFile));
            // prepare XPath expression
            XPathFactory xpf = XPathFactory.newInstance();
            XPath xp = xpf.newXPath();

            // get environment topics
            XPathExpression expression = xp.compile("//environment-topic");
            NodeList elements = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < elements.getLength(); i++)
            {
                // get node element
                Node topic = elements.item(i);
                // get topic name
                Attr topicName = (Attr) topic.getAttributes().getNamedItem("name");
                // get delegate class
                Attr delegateClass = (Attr) topic.getAttributes().getNamedItem("delegate");

                // subscribe to topic if necessary
                if (!this.subscribedTopics.contains(topicName.getValue().trim().toLowerCase()))
                {
                    // index topic
                    this.subscribedTopics.add(topicName.getValue().trim().toLowerCase());
                    System.out.println("... subscribing to topic " + topicName.getValue().trim().toLowerCase() + " ...");

                    // create observation listener
                    Class<? extends RoxanneObservationListener> clazz = (Class<? extends RoxanneObservationListener>)
                            Class.forName(delegateClass.getValue().trim());
                    Constructor<? extends RoxanneObservationListener> c =
                            clazz.getDeclaredConstructor(RosJavaPlatformProxy.class);
                    // set visibility of constructor
                    c.setAccessible(true);
                    // create instance
                    RoxanneObservationListener listener = c.newInstance(this);
                    // create subscriber
                    listener.createSubscriber(topicName.getValue().trim(), this.connNode);
                }
            }


            // get platform commands
            expression = xp.compile("//command");
            elements = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < elements.getLength(); i++)
            {
                // get command element
                Node cmd = elements.item(i);

                // index command name
                Attr cmdName = (Attr) cmd.getAttributes().getNamedItem("name");
                Attr compName = (Attr) cmd.getAttributes().getNamedItem("component");
                System.out.println("... parsing cmd: " + compName.getValue() + "." + cmdName.getValue() + " <<");

                // get command dispatch topic info
                expression = xp.compile("//command[@name= '" + cmdName.getValue() +  "' and @component= '" + compName.getValue() + "']/dispatch-topic");
                NodeList list = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
                // get dispatch topic
                Node dispatchTopic = list.item(0);
                // get dispatch topic name
                Attr dispatchTopicName = (Attr) dispatchTopic.getAttributes().getNamedItem("name");
                // get publisher class
                Attr publisherClass = (Attr) dispatchTopic.getAttributes().getNamedItem("publisher");

                // index dispatching topic by platform command
                this.command2dispatchTopic.put(
                        compName.getValue().trim().toLowerCase() + "." + cmdName.getValue().trim().toLowerCase(),
                        dispatchTopicName.getValue().trim().toLowerCase());

                // create publisher if necessary
                if (!this.topic2publisher.containsKey(dispatchTopicName.getValue().trim().toLowerCase()))
                {
                    // create publisher instance
                    Class<? extends RoxanneTokenPublisher> clazz = (Class<? extends RoxanneTokenPublisher>)
                            Class.forName(publisherClass.getValue().trim());
                    Constructor<? extends RoxanneTokenPublisher> c =
                            clazz.getDeclaredConstructor(RosJavaPlatformProxy.class);
                    // set constructor visibility
                    c.setAccessible(true);
                    // create instance
                    RoxanneTokenPublisher publisher = c.newInstance(this);
                    // create publisher
                    publisher.createPublisher(dispatchTopicName.getValue().trim(), this.connNode);

                    // index publisher by topic
                    this.topic2publisher.put(dispatchTopicName.getValue().trim().toLowerCase(), publisher);
                    System.out.println("... creating publisher to topic " + dispatchTopicName.getValue().trim().toLowerCase() + " ...");
                }


                // get command feedback topic info
                expression = xp.compile("//command[@name= '" + cmdName.getValue() +  "' and @component= '" + compName.getValue() + "']/feedback-topic");
                list = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
                // get feedback topic
                Node feedbackTopic = list.item(0);
                // get feedback topic name
                Attr feedbackTopicName = (Attr) feedbackTopic.getAttributes().getNamedItem("name");
                // get delegate class
                Attr delegateClass = (Attr) feedbackTopic.getAttributes().getNamedItem("delegate");

                // subscribe to topic if necessary
                if (!this.subscribedTopics.contains(feedbackTopicName.getValue().trim().toLowerCase()))
                {
                    // index topic
                    this.subscribedTopics.add(feedbackTopicName.getValue().trim().toLowerCase());
                    System.out.println("... subscribing to topic " + feedbackTopicName.getValue().trim().toLowerCase() + " ...");


                    // create feedback listener
                    Class<? extends RoxanneFeedbackListener> clazz = (Class<? extends RoxanneFeedbackListener>)
                            Class.forName(delegateClass.getValue().trim());
                    Constructor<? extends RoxanneFeedbackListener> c =
                            clazz.getDeclaredConstructor(RosJavaPlatformProxy.class);
                    // set constructor visible
                    c.setAccessible(true);
                    // create instance
                    RoxanneFeedbackListener listener = c.newInstance(this);
                    // create subscriber
                    listener.createSubscriber(feedbackTopicName.getValue().trim(), this.connNode);
                }
            }
        }
        catch (Exception ex) {
            throw new PlatformException(ex.getMessage());
        }
    }

    /**
     *
     * @param node
     * @return
     */
    @Override
    public PlatformCommand executeNode(ExecutionNode node)
    {
        // get command id
        int id = cmdIdCounter.getAndIncrement();
        //  create platform command
        PlatformCommand cmd = new PlatformCommand(id, node, 1);

        // extract command information
        String cmdName = PlatformProxy.extractCommandName(node);
        String compName = node.getComponent();

        // get dispatcher topic
        String topic = this.command2dispatchTopic.get(
                compName.trim().toLowerCase() + "." + cmdName.trim().toLowerCase());

        // check default dispatching topic of the component
        if (topic == null) {
            // get default dispatching topic of the component (if any)
            topic = this.command2dispatchTopic.get(
                    compName.trim().toLowerCase() + ".*");
        }

        // check default dispatching topic
        if (topic == null) {
            // get default dispatching topic
            topic = this.command2dispatchTopic.get("*.*");
        }

        // get message publisher
        RoxanneTokenPublisher publisher = this.topic2publisher.get(topic);
        // publish message
        publisher.doPublish(this.connNode, cmd);

        // add command to dispatched index
        this.dispatchedIndex.put(cmd.getId(), cmd);
        // get dispatched command
        return cmd;

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
        int id = cmdIdCounter.getAndIncrement();
        //  create platform command
        PlatformCommand cmd = new PlatformCommand(id, node, 1);

        // extract command information
        String cmdName = PlatformProxy.extractCommandName(node);
        String compName = node.getComponent();

        // get dispatcher topic
        String topic = this.command2dispatchTopic.get(
                compName.trim().toLowerCase() + "." + cmdName.trim().toLowerCase());

        // check default dispatching topic of the component
        if (topic == null) {
            // get default dispatching topic of the component (if any)
            topic = this.command2dispatchTopic.get(
                    compName.trim().toLowerCase() + ".*");
        }

        // check default dispatching topic
        if (topic == null) {
            // get default dispatching topic
            topic = this.command2dispatchTopic.get("*.*");
        }


        // get message publisher
        RoxanneTokenPublisher publisher = this.topic2publisher.get(topic);
        // publish message
        publisher.doPublish(this.connNode, cmd);

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
    public void stopNode(ExecutionNode node) {

        // get command id
        int id = cmdIdCounter.getAndIncrement();
        //  create platform command
        PlatformCommand cmd = new PlatformCommand(id, node, 0);

        // extract command information
        String cmdName = PlatformProxy.extractCommandName(node);
        String compName = node.getComponent();

        // get dispatcher topic
        String topic = this.command2dispatchTopic.get(
                compName.trim().toLowerCase() + "." + cmdName.trim().toLowerCase());

        // check default dispatching topic of the component
        if (topic == null) {
            // get default dispatching topic of the component (if any)
            topic = this.command2dispatchTopic.get(
                    compName.trim().toLowerCase() + ".*");
        }

        // check default dispatching topic
        if (topic == null) {
            // get default dispatching topic
            topic = this.command2dispatchTopic.get("*.*");
        }


        // get message publisher
        RoxanneTokenPublisher publisher = this.topic2publisher.get(topic);
        // publish message
        publisher.doPublish(this.connNode, cmd);

        // add command to dispatched index
        this.dispatchedIndex.put(cmd.getId(), cmd);
    }

    /**
     * The default behavior is to dispatch any node associated to the tokens
     * of primitive state variables.
     *
     * Check the platform configuration file with the dispatching and feedback specifications
     *
     * @param node
     * @return
     */
    @Override
    public boolean isPlatformCommand(ExecutionNode node) {
        // check token name
        String cmdName = PlatformProxy.extractCommandName(node);
        // check component name
        String compName = node.getComponent();

        // check if there is a specific dispatching topic for this node
        boolean toDispatch = this.command2dispatchTopic.containsKey(
                compName.trim().toLowerCase() + "." + cmdName.trim().toLowerCase());

        // check if there is a default dispatching command for the component
        if (!toDispatch) {
            // check component default dispatching command
            toDispatch = this.command2dispatchTopic.containsKey(compName.trim().toLowerCase() + ".*");
        }

        // check if there is a default dispatching command
        if (!toDispatch) {
            // check default
            toDispatch = this.command2dispatchTopic.containsKey("*.*");
        }

        // get flag
        return toDispatch;
    }


}


