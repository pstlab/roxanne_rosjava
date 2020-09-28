package control_msgs;

public interface PointHead extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "control_msgs/PointHead";
  static final java.lang.String _DEFINITION = "geometry_msgs/PointStamped target\ngeometry_msgs/Vector3 pointing_axis\nstring pointing_frame\nduration min_duration\nfloat64 max_velocity\n---\n---\nfloat64 pointing_angle_error\n";
}
