package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaGoalListener;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaPlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.ex.MessageUnmarshalingException;
import it.cnr.istc.pst.platinum.control.lang.AgentTaskDescription;
import it.cnr.istc.pst.platinum.control.lang.TokenDescription;
import roxanne_rosjava_msgs.ActingGoal;
import roxanne_rosjava_msgs.Token;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class RoxanneGoalListener extends RosJavaGoalListener<roxanne_rosjava_msgs.ActingGoal> {

    private static final AtomicLong taskCounter = new AtomicLong(0);

    /**
     *
     * @param proxy
     */
    protected RoxanneGoalListener(RosJavaPlatformProxy proxy) {
        super(proxy);
    }

    /**
     *
     * @return
     */
    @Override
    public String getMessageType() {
        return roxanne_rosjava_msgs.ActingGoal._TYPE;
    }

    /**
     *
     * @param msg
     * @return
     * @throws MessageUnmarshalingException
     */
    @Override
    public AgentTaskDescription unmarshal(ActingGoal msg)
            throws MessageUnmarshalingException {

        // get goal tokens
        List<Token> goals = msg.getGoals();
        // get facts
        List<roxanne_rosjava_msgs.Token> facts = msg.getFacts();

        // check message data
        if (goals == null || goals.isEmpty()) {
            // mandatory parameter missing
            throw new MessageUnmarshalingException("Received an invalid task request: \"" + msg + "\"\n" +
                    "No goal has been specified");
        }


        // received input goal
        log.info("Received a task to plan for: \"" + msg + "\"\n");
        // prepare a task description
        AgentTaskDescription task = new AgentTaskDescription(taskCounter.getAndIncrement());

        // check if facts exist
        if (facts != null && !facts.isEmpty()) {
            // set facts
            for (roxanne_rosjava_msgs.Token tk : facts)
            {
                // get component
                String component = tk.getComponent();
                // get predicate
                String predicate = tk.getPredicate();
                // get parameters
                String[] params = tk.getParameters() != null ? tk.getParameters().toArray(new String[tk.getParameters().size()]) : null;
                // get start
                long[] start = (((long[]) tk.getStart()) != null && ((long[]) tk.getStart()).length > 0) ? (long[]) tk.getStart() : null;
                // get end
                long[] end = (((long[]) tk.getEnd()) != null && ((long[]) tk.getEnd()).length > 0) ? (long[]) tk.getEnd() : null;
                // get duration
                long[] duration = (((long[]) tk.getDuration()) != null && ((long[]) tk.getDuration()).length > 0) ? (long[]) tk.getDuration() : null;

                log.info("Adding fact:\n" +
                        "- id: " + tk.getId() + "\n" +
                        "- component: " + component + "\n" +
                        "- predicate: " + predicate + "\n" +
                        "- start: [" + start[0] + "," + start[1] + "]\n" +
                        "- end: [" + end[0] + "," + end[1] +"]\n" +
                        "- duration: [" + duration[0] + "," + duration[1] + "]\n");

                // add fact description from received request
                task.addFactDescription(new TokenDescription(
                        component,
                        predicate,
                        params,
                        start,
                        end,
                        duration));
            }

            // set goals
            for (roxanne_rosjava_msgs.Token tk : goals)
            {
                // get component
                String component = tk.getComponent();
                // get predicate
                String predicate = tk.getPredicate();
                // get parameters
                String[] params = tk.getParameters() != null ? tk.getParameters().toArray(new String[tk.getParameters().size()]) : null;
                // get start
                long[] start = (((long[]) tk.getStart()) != null && ((long[]) tk.getStart()).length > 0) ? (long[]) tk.getStart() : null;
                // get end
                long[] end = (((long[]) tk.getEnd()) != null && ((long[]) tk.getEnd()).length > 0) ? (long[]) tk.getEnd() : null;
                // get duration
                long[] duration = (((long[]) tk.getDuration()) != null && ((long[]) tk.getDuration()).length > 0) ? (long[]) tk.getDuration() : null;

                log.info("Acting goal:\n" +
                        "- id: " + tk.getId() + "\n" +
                        "- component: " + component + "\n" +
                        "- predicate: " + predicate + "\n" +
                        "- start: [" + start[0] + "," + start[1] + "]\n" +
                        "- end: [" + end[0] + "," + end[1] +"]\n" +
                        "- duration: [" + duration[0] + "," + duration[1] + "]\n");

                // add goal description from received request
                task.addGoalDescription(new TokenDescription(
                        component,
                        predicate,
                        params,
                        start,
                        end,
                        duration));
            }

        }

        // get task request description
        return task;
    }
}
