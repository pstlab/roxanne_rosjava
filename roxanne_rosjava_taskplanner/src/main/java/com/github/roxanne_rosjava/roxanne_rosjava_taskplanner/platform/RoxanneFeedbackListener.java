package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;


import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaTopicListener;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaPlatformProxy;

/**
 *
 */
public class RoxanneFeedbackListener extends RosJavaTopicListener<roxanne_rosjava_msgs.TokenExecutionFeedback>
{
    /**
     *
     * @param proxy
     */
    protected RoxanneFeedbackListener(RosJavaPlatformProxy proxy) {
        super(proxy);
    }

    /**
     *
     * @return
     */
    @Override
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

}
