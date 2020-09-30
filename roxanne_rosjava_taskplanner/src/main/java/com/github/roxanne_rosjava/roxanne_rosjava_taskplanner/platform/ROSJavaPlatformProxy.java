package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutionNode;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.PlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformCommand;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.ex.PlatformException;

import java.util.HashMap;
import java.util.Map;

/**
 * ROSJava connector proxy
 *
 */
public class ROSJavaPlatformProxy extends PlatformProxy
{
    private static final String dispatchingTopic = "/roxanne/acting/dispatching";
    private static final String feedbackTopic = "/roxanne/acting/feedback";
    private static final String observationTopic = "roxanne/acting/observation";

    private Map<String, PlatformCommand> dispatchedIndex;   // index of dispatched commands by ID

    /**
     *
     */
    public ROSJavaPlatformProxy() {
        super();
        // set dispatched index
        this.dispatchedIndex = new HashMap<>();
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

    }

    /**
     *
     * @param node
     * @return
     */
    @Override
    public PlatformCommand executeNode(ExecutionNode node) {

        /*
         * TODO
         */

        return null;

    }

    /**
     *
     * @param node
     * @return
     */
    @Override
    public PlatformCommand startNode(ExecutionNode node) {

        /*
         * TODO
         */


        return null;
    }

    /**
     *
     * @param node
     */
    @Override
    public void stopNode(ExecutionNode node) {

        /*
         * TODO
         */
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
