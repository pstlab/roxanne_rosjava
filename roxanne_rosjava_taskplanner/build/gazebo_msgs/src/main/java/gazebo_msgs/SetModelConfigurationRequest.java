package gazebo_msgs;

public interface SetModelConfigurationRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "gazebo_msgs/SetModelConfigurationRequest";
  static final java.lang.String _DEFINITION = "# Set joint positions for a model\nstring model_name           # model to set state\nstring urdf_param_name      # UNUSED\n\nstring[] joint_names        # list of joints to set positions.  if joint is not listed here, preserve current position.\nfloat64[] joint_positions   # set to this position.\n";
  java.lang.String getModelName();
  void setModelName(java.lang.String value);
  java.lang.String getUrdfParamName();
  void setUrdfParamName(java.lang.String value);
  java.util.List<java.lang.String> getJointNames();
  void setJointNames(java.util.List<java.lang.String> value);
  double[] getJointPositions();
  void setJointPositions(double[] value);
}
