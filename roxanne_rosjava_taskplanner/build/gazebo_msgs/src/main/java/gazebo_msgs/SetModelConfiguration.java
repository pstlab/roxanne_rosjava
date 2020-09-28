package gazebo_msgs;

public interface SetModelConfiguration extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "gazebo_msgs/SetModelConfiguration";
  static final java.lang.String _DEFINITION = "# Set joint positions for a model\nstring model_name           # model to set state\nstring urdf_param_name      # UNUSED\n\nstring[] joint_names        # list of joints to set positions.  if joint is not listed here, preserve current position.\nfloat64[] joint_positions   # set to this position.\n---\nbool success                # return true if setting state successful\nstring status_message       # comments if available\n";
}
