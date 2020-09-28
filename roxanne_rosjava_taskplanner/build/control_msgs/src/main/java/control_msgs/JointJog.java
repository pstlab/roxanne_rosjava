package control_msgs;

public interface JointJog extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "control_msgs/JointJog";
  static final java.lang.String _DEFINITION = "# Used in time-stamping the message.\nHeader header\n\n# Name list of the joints. You don\'t need to specify all joints of the\n# robot. Joint names are case-sensitive.\nstring[] joint_names\n\n# A position command to the joints listed in joint_names.\n# The order must be identical.\n# Units are meters or radians.\n# If displacements and velocities are filled, a profiled motion is requested.\nfloat64[] displacements # or position_deltas\n\n# A velocity command to the joints listed in joint_names.\n# The order must be identical.\n# Units are m/s or rad/s.\n# If displacements and velocities are filled, a profiled motion is requested.\nfloat64[] velocities\n\nfloat64 duration\n";
  std_msgs.Header getHeader();
  void setHeader(std_msgs.Header value);
  java.util.List<java.lang.String> getJointNames();
  void setJointNames(java.util.List<java.lang.String> value);
  double[] getDisplacements();
  void setDisplacements(double[] value);
  double[] getVelocities();
  void setVelocities(double[] value);
  double getDuration();
  void setDuration(double value);
}
