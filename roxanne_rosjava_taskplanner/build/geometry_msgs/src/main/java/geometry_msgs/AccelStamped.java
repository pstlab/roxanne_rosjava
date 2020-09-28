package geometry_msgs;

public interface AccelStamped extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "geometry_msgs/AccelStamped";
  static final java.lang.String _DEFINITION = "# An accel with reference coordinate frame and timestamp\nHeader header\nAccel accel\n";
  std_msgs.Header getHeader();
  void setHeader(std_msgs.Header value);
  geometry_msgs.Accel getAccel();
  void setAccel(geometry_msgs.Accel value);
}
