package rosjava_test_msgs;

public interface Composite extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosjava_test_msgs/Composite";
  static final java.lang.String _DEFINITION = "# composite message. required for testing import calculation in generators\nCompositeA a\nCompositeB b\n";
  rosjava_test_msgs.CompositeA getA();
  void setA(rosjava_test_msgs.CompositeA value);
  rosjava_test_msgs.CompositeB getB();
  void setB(rosjava_test_msgs.CompositeB value);
}
