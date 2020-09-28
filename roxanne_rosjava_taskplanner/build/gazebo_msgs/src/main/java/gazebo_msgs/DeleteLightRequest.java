package gazebo_msgs;

public interface DeleteLightRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "gazebo_msgs/DeleteLightRequest";
  static final java.lang.String _DEFINITION = "string light_name                 # name of the light to be deleted\n";
  java.lang.String getLightName();
  void setLightName(java.lang.String value);
}
