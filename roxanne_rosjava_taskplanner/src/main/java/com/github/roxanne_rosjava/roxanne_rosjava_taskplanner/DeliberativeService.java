package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.Planner;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.PlannerBuilder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.PlanDataBaseBuilder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.PlanDataBase;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.Token;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.ex.NoSolutionFoundException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.ex.ProblemInitializationException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.plan.SolutionPlan;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.plan.Timeline;
import com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.control.ROSJavaPlanner;
import org.apache.commons.logging.Log;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceResponseBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class DeliberativeService extends AbstractNodeMain
{
    Log log;                // logger

    /**
     *
     * @return
     */
    @Override
    public GraphName getDefaultNodeName() {
        // set node name
        return GraphName.of("/roxanne/deliberative");
    }

    /**
     *
     * @param connectedNode
     */
    @Override
    public void onStart(final ConnectedNode connectedNode)
    {
        // set logger
        this.log = connectedNode.getLog();
        // start waiting for requests
        connectedNode.newServiceServer("roxanne/service/deliberative",
                roxanne_rosjava_msgs.DeliberativeService._TYPE,
                new ServiceResponseBuilder<roxanne_rosjava_msgs.DeliberativeServiceRequest, roxanne_rosjava_msgs.DeliberativeServiceResponse>() {

                    /**
                     *
                     * @param req
                     * @param res
                     */
                    @Override
                    public void build(roxanne_rosjava_msgs.DeliberativeServiceRequest req, roxanne_rosjava_msgs.DeliberativeServiceResponse res) {

                        // get request data
                        String ddl = req.getDdl();
                        String pdl = req.getPdl();
                        log.info("Received deliberative service request for:\n- ddl " + ddl + "\n- pdl " + pdl + "\n");

                        try
                        {
                            // build the plan database
                            PlanDataBase pdb = PlanDataBaseBuilder.createAndSet(ddl, pdl);
                            // set a planning instance of the plan database
                            Planner planner = PlannerBuilder.createAndSet(ROSJavaPlanner.class, pdb);

                            // start planning
                            SolutionPlan plan = planner.plan();
                            // display plan
                            planner.display();
                            // solution found
                            log.info("----------------------------------\nSolution found after " + plan.getSolvingTime() + " msecs\n"
                                    + "Solution plan:\n" + plan + "\n----------------------------------\n");

                            // set response
                            res.setResult(new Integer(1).byteValue());
                            // prepare the list of timelines
                            List<roxanne_rosjava_msgs.Timeline> tls = new ArrayList<>();
                            // set content
                            int tlId = 0;
                            for (Timeline tl : plan.getTimelines())
                            {
                                // prepare a list of tokens
                                List<roxanne_rosjava_msgs.Token> tList = new ArrayList<>();
                                for (Token tk : tl.getTokens())
                                {

                                    // create token message object
                                    roxanne_rosjava_msgs.Token t = connectedNode.getServiceResponseMessageFactory().
                                            newFromType(roxanne_rosjava_msgs.Token._TYPE);

                                    // set data
                                    t.setId(tk.getId());
                                    t.setComponent(tk.getComponent().getName());

                                    // get predicate
                                    String[] splits = tk.getPredicate().getGroundSignature().split("-");
                                    // set predicate
                                    t.setPredicate(splits[0]);
                                    // check parameters
                                    if (splits.length > 1) {
                                        t.setParameters(Arrays.asList(Arrays.copyOfRange(splits, 1, splits.length)));
                                    }

                                    // set start time bound
                                    t.setStart(new long[] {
                                            tk.getInterval().getStartTime().getLowerBound(),
                                            tk.getInterval().getStartTime().getUpperBound()
                                    });

                                    // set end time bound
                                    t.setEnd(new long[] {
                                            tk.getInterval().getEndTime().getLowerBound(),
                                            tk.getInterval().getEndTime().getUpperBound()
                                    });

                                    // set duration bounds
                                    t.setDuration(new long[] {
                                            tk.getInterval().getDurationLowerBound(),
                                            tk.getInterval().getDurationUpperBound()
                                    });

                                    // add token to list
                                    tList.add(t);

                                }

                                // create timeline
                                roxanne_rosjava_msgs.Timeline msgTl = connectedNode.getServiceResponseMessageFactory().
                                        newFromType(roxanne_rosjava_msgs.Timeline._TYPE);

                                // set id
                                msgTl.setId(tlId);
                                // set component
                                msgTl.setComponent(tl.getComponent().getName());
                                // set tokens
                                msgTl.setTokens(tList);
                                // add timeline to the list
                                tls.add(msgTl);

                                tlId++;
                            }


                            // set timelines to response message
                            res.setTimelines(tls);

                        }
                        catch (NoSolutionFoundException ex) {
                            // no solution found
                            log.info(ex.getMessage());

                            // set response
                            res.setResult(new Integer(1).byteValue());
                        }
                        catch (Exception ex) {
                            // error
                            log.warn(ex.getMessage());
                            // set response
                            res.setResult(new Integer(2).byteValue());
                        }
                    }
                });
    }
}
