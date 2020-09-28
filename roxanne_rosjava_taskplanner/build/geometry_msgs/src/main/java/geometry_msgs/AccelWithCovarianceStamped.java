package geometry_msgs;

public interface AccelWithCovarianceStamped extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "geometry_msgs/AccelWithCovarianceStamped";
  static final java.lang.String _DEFINITION = "# This represents an estimated accel with reference coordinate frame and timestamp.\nHeader header\nAccelWithCovariance accel\n";
  std_msgs.Header getHeader();
  void setHeader(std_msgs.Header value);
  geometry_msgs.AccelWithCovariance getAccel();
  void setAccel(geometry_msgs.AccelWithCovariance value);
}
