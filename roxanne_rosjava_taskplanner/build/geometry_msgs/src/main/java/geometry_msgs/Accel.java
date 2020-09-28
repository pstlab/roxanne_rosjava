package geometry_msgs;

public interface Accel extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "geometry_msgs/Accel";
  static final java.lang.String _DEFINITION = "# This expresses acceleration in free space broken into its linear and angular parts.\nVector3  linear\nVector3  angular\n";
  geometry_msgs.Vector3 getLinear();
  void setLinear(geometry_msgs.Vector3 value);
  geometry_msgs.Vector3 getAngular();
  void setAngular(geometry_msgs.Vector3 value);
}
