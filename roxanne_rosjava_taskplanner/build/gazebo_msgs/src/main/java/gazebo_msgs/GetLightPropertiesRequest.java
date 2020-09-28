package gazebo_msgs;

public interface GetLightPropertiesRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "gazebo_msgs/GetLightPropertiesRequest";
  static final java.lang.String _DEFINITION = "string light_name                    # name of Gazebo Light\n";
  java.lang.String getLightName();
  void setLightName(java.lang.String value);
}
