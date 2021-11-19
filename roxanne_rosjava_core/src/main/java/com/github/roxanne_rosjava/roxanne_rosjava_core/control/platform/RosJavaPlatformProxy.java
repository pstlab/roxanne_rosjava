package com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.ex.CommandPublisherException;
import it.cnr.istc.pst.platinum.ai.executive.pdb.ExecutionNode;
import it.cnr.istc.pst.platinum.control.lang.PlatformCommand;
import it.cnr.istc.pst.platinum.control.lang.ex.PlatformException;
import it.cnr.istc.pst.platinum.control.platform.PlatformProxy;
import org.apache.commons.logging.Log;
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

    private Map<String, String> command2dispatchTopic;	              // map platform commands to ROS dispatch topics
    private Map<String, RosJavaCommandPublisher> topic2publisher;	  // map ROS topic to publisher
    private Set<String> subscribedTopics;                             // subscribed topics
    private Map<String, Set<String>> component2excluded;             // excluded commands by component

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
        this.component2excluded = new HashMap<>();
    }

    /**
     *
     * @param cfgFile
     * @throws PlatformException
     */
    @Override
    public void initialize(String cfgFile)
            throws PlatformException {

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
                    Class<? extends RosJavaObservationListener> clazz = (Class<? extends RosJavaObservationListener>)
                            Class.forName(delegateClass.getValue().trim());
                    Constructor<? extends RosJavaObservationListener> c =
                            clazz.getDeclaredConstructor(RosJavaPlatformProxy.class);
                    // set visibility of constructor
                    c.setAccessible(true);
                    // create instance
                    RosJavaObservationListener listener = c.newInstance(this);
                    // create subscriber
                    listener.createSubscriber(topicName.getValue().trim(), this.connNode);
                }
            }

            // get (input) goal topic(s)
            expression = xp.compile("//goal-topic");
            elements = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
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
                    Class<? extends RosJavaGoalListener> clazz = (Class<? extends RosJavaGoalListener>)
                            Class.forName(delegateClass.getValue().trim());
                    Constructor<? extends RosJavaGoalListener> c =
                            clazz.getDeclaredConstructor(RosJavaPlatformProxy.class);
                    // set visibility of constructor
                    c.setAccessible(true);
                    // create instance
                    RosJavaGoalListener listener = c.newInstance(this);
                    // create subscriber
                    listener.createSubscriber(topicName.getValue().trim(), this.connNode);
                }
            }


            // get platform commands
            expression = xp.compile("//command");
            elements = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < elements.getLength(); i++) {

                // get command element
                Node cmd = elements.item(i);

                // index command name
                Attr cmdName = (Attr) cmd.getAttributes().getNamedItem("name");
                Attr compName = (Attr) cmd.getAttributes().getNamedItem("component");
                Attr exclude = (Attr) cmd.getAttributes().getNamedItem("exclude");
                System.out.println("... parsing cmd: " + compName.getValue() + "." + cmdName.getValue() + " <<");

                // check if attribute "exclude" has been set
                if (exclude != null) {

                    // set exclude
                    if (!this.component2excluded.containsKey(compName.getValue().trim().toLowerCase())) {
                        this.component2excluded.put(compName.getValue().trim().toLowerCase(), new HashSet<String>());
                    }

                    // check excluded values - expected a list separated by space
                    String[] values = exclude.getValue().split(" ");
                    // add values
                    for (String val : values) {
                        // add the value to exclude
                        this.component2excluded.get(compName.getValue().trim().toLowerCase())
                                .add(val.trim().toLowerCase());
                    }
                }

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
                if (!this.topic2publisher.containsKey(dispatchTopicName.getValue().trim().toLowerCase())) {
                    
                    // create publisher instance
                    Class<? extends RosJavaCommandPublisher> clazz = (Class<? extends RosJavaCommandPublisher>)
                            Class.forName(publisherClass.getValue().trim());
                    Constructor<? extends RosJavaCommandPublisher> c =
                            clazz.getDeclaredConstructor(RosJavaPlatformProxy.class);
                    // set constructor visibility
                    c.setAccessible(true);
                    // create instance
                    RosJavaCommandPublisher publisher = c.newInstance(this);
                    // create publisher
                    publisher.createPublisher(dispatchTopicName.getValue().trim(), this.connNode);

                    // index publisher by topic
                    this.topic2publisher.put(dispatchTopicName.getValue().trim().toLowerCase(), publisher);
                    System.out.println("... creating publisher to topic " + dispatchTopicName.getValue().trim().toLowerCase() + " ...");
                }

                // get command feedback topic info
                expression = xp.compile("//command[@name= '" + cmdName.getValue() +  "' and @component= '" + compName.getValue() + "']/feedback-topic");
                list = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
                // check feedback tag
                if (list != null && list.item(0) != null) {

                    // get feedback topic
                    Node feedbackTopic = list.item(0);
                    // get feedback topic name
                    Attr feedbackTopicName = (Attr) feedbackTopic.getAttributes().getNamedItem("name");
                    // get delegate class
                    Attr delegateClass = (Attr) feedbackTopic.getAttributes().getNamedItem("delegate");

                    // subscribe to topic if necessary
                    if (!this.subscribedTopics.contains(feedbackTopicName.getValue().trim().toLowerCase())) {
                        // index topic
                        this.subscribedTopics.add(feedbackTopicName.getValue().trim().toLowerCase());
                        System.out.println("... subscribing to topic " + feedbackTopicName.getValue().trim().toLowerCase() + " ...");

                        // create feedback listener
                        Class<? extends RosJavaFeedbackListener> clazz = (Class<? extends RosJavaFeedbackListener>)
                                Class.forName(delegateClass.getValue().trim());
                        Constructor<? extends RosJavaFeedbackListener> c =
                                clazz.getDeclaredConstructor(RosJavaPlatformProxy.class);
                        // set constructor visible
                        c.setAccessible(true);
                        // create instance
                        RosJavaFeedbackListener listener = c.newInstance(this);
                        // create subscriber
                        listener.createSubscriber(feedbackTopicName.getValue().trim(), this.connNode);
                    }
                }
            }

        } catch (Exception ex) {
            throw new PlatformException(ex.getMessage());
        }
    }

    /**
     *
     * @return
     */
    protected Log getLogger() {
        // get logger
        return this.connNode.getLog();
    }

    /**
     *
     * @param node
     * @return
     */
    @Override
    public PlatformCommand executeNode(ExecutionNode node)
            throws PlatformException {

        // get command id
        int id = cmdIdCounter.getAndIncrement();
        // extract command information
        String cmdName = this.extractCommandName(node);
        String compName = node.getComponent();
        // extract command parameters
        String[] cmdParams = this.extractCommandParameters(node);

        //  create platform command
        PlatformCommand cmd = new PlatformCommand(
                id,
                cmdName,
                cmdParams,
                node,
                1);

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

        try {

            // get message publisher
            RosJavaCommandPublisher publisher = this.topic2publisher.get(topic);
            // publish message
            publisher.publish(this.connNode, cmd);
            // add command to dispatched index
            this.dispatchedIndex.put(cmd.getId(), cmd);
        }
        catch (CommandPublisherException ex) {
            // throw platform exception
            throw new PlatformException(ex.getMessage());
        }

        // get dispatched command
        return cmd;

    }

    /**
     *
     * @param node
     * @return
     * @throws PlatformException
     */
    @Override
    public PlatformCommand startNode(ExecutionNode node)
            throws PlatformException {

        // get command id
        int id = cmdIdCounter.getAndIncrement();
        // extract command information
        String cmdName = this.extractCommandName(node);
        String compName = node.getComponent();
        // extract command parameters
        String[] cmdParams = this.extractCommandParameters(node);

        //  create platform command
        PlatformCommand cmd = new PlatformCommand(
                id,
                cmdName,
                cmdParams,
                node,
                1);

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


        try {

            // get message publisher
            RosJavaCommandPublisher publisher = this.topic2publisher.get(topic);
            // publish message
            publisher.publish(this.connNode, cmd);
            // add command to dispatched index
            this.dispatchedIndex.put(cmd.getId(), cmd);
        }
        catch (CommandPublisherException ex) {
            throw new PlatformException(ex.getMessage());
        }

        // get dispatched command
        return cmd;
    }

    /**
     *
     * @param node
     */
    @Override
    public void stopNode(ExecutionNode node)
            throws PlatformException {

        // get command id
        int id = cmdIdCounter.getAndIncrement();
        // extract command information
        String cmdName = this.extractCommandName(node);
        String compName = node.getComponent();
        // extract command parameters
        String[] cmdParams = this.extractCommandParameters(node);

        //  create platform command
        PlatformCommand cmd = new PlatformCommand(
                id,
                cmdName,
                cmdParams,
                node,
                0);

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

        try {

            // get message publisher
            RosJavaCommandPublisher publisher = this.topic2publisher.get(topic);
            // publish message
            publisher.publish(this.connNode, cmd);
            // add command to dispatched index
            this.dispatchedIndex.put(cmd.getId(), cmd);
        }
        catch (CommandPublisherException ex) {
            throw new PlatformException(ex.getMessage());
        }
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
        String cmdName = this.extractCommandName(node);
        // check component name
        String compName = node.getComponent();

        // to dispatch flag
        boolean excluded = true;
        // check first if the value is excluded from dispatch
        if (this.component2excluded.containsKey(compName.trim().toLowerCase()) ||
                this.component2excluded.containsKey("*")) {

            // check value
            excluded = this.component2excluded.containsKey(compName.trim().toLowerCase()) ?
                    // check component specification
                    this.component2excluded.get(compName.trim().toLowerCase()).contains(cmdName.trim().toLowerCase()) :
                    // check global specification
                    this.component2excluded.get("*").contains(cmdName.trim().toLowerCase());
        }

        // set dispatch flag
        boolean toDispatch = false;
        // check excluded flag
        if (!excluded) {

            // check if there is a specific dispatching topic for this node
            toDispatch = this.command2dispatchTopic.containsKey(
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
        }

        // get flag
        return toDispatch;
    }


}


