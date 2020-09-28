package rosjava_test_msgs;

public interface CompositeA extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosjava_test_msgs/CompositeA";
  static final java.lang.String _DEFINITION = "# This represents an orientation in free space in quaternion form.\n\nfloat64 x\nfloat64 y\nfloat64 z\nfloat64 w\n";
  double getX();
  void setX(double value);
  double getY();
  void setY(double value);
  double getZ();
  void setZ(double value);
  double getW();
  void setW(double value);
}
