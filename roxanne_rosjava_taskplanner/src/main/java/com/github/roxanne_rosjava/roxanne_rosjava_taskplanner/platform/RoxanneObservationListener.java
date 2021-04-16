package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;


import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaPlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaTopicListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

/**
 *
 */
public class RoxanneObservationListener extends RosJavaTopicListener<roxanne_rosjava_msgs.Observation>
{
    /**
     *
     * @param proxy
     */
    protected RoxanneObservationListener(RosJavaPlatformProxy proxy) {
        super(proxy);
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

}
