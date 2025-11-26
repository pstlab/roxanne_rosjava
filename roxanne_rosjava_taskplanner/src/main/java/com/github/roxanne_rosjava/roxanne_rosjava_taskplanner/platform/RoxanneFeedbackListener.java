package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;


import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaFeedbackListener;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaPlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.ex.MessageUnmarshalingException;
import it.cnr.istc.pst.platinum.control.lang.PlatformCommand;
import it.cnr.istc.pst.platinum.control.lang.PlatformFeedback;
import it.cnr.istc.pst.platinum.control.lang.PlatformFeedbackType;
import roxanne_rosjava_msgs.TokenExecutionFeedback;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class RoxanneFeedbackListener extends RosJavaFeedbackListener<TokenExecutionFeedback> {

    private static AtomicLong feedbackIdCounter = new AtomicLong(0);

    private static final PlatformFeedbackType[] RESULT = new PlatformFeedbackType[] {

            PlatformFeedbackType.SUCCESS,           // index/code 0 - successful execution

            PlatformFeedbackType.FAILURE            // index/code 1 - execution failure
    };

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
     * @return
     * @throws MessageUnmarshalingException
     */
    @Override
    public PlatformFeedback unmarshal(roxanne_rosjava_msgs.TokenExecutionFeedback message)
            throws MessageUnmarshalingException {

        // retrieve issued command from feedback
        PlatformCommand cmd = this.proxy.getDispatchedCommand(message.getTokenId());
        // check command
        if (cmd == null) {

            // no command found
            throw new MessageUnmarshalingException("Received feedback about a non-dispatched command:\n" +
                    "\t- CommandId: " + message.getTokenId() + "\n" +
                    "\t- Feedback: " + message + "\n");
        }

        // get platform feedback
        PlatformFeedback feedback = new PlatformFeedback(
                feedbackIdCounter.getAndIncrement(),
                cmd,
                RESULT[message.getCode()]);


        this.log.info("[RoxanneFeedbackListener] Received feedback about dispatched command:\n" +
                "- Command ID: " + cmd.getId() + "\n" +
                "- Task name: " + cmd.getName() + "\n" +
                "- Task parameters: " + Arrays.asList(cmd.getParamValues()) + "\n" +
                "- Feedback: " + feedback + "\n");

        // get the feedback
        return feedback;
    }

}
