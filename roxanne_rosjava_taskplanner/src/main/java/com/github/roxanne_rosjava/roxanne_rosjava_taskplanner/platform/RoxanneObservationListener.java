package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform;


import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaObservationListener;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaPlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.ex.MessageUnmarshalingException;
import it.cnr.istc.pst.platinum.control.lang.PlatformObservation;
import org.json.JSONObject;
import roxanne_rosjava_msgs.Observation;

/**
 *
 */
public class RoxanneObservationListener extends RosJavaObservationListener<Observation, JSONObject>
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
     * @param msg
     * @return
     * @throws MessageUnmarshalingException
     */
    @Override
    public PlatformObservation<JSONObject> unmarshal(Observation msg)
            throws MessageUnmarshalingException {

        /**
         * TODO
         */

        return null;
    }
}
