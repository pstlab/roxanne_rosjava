package gazebo_msgs;

public interface GetModelStateResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "gazebo_msgs/GetModelStateResponse";
  static final java.lang.String _DEFINITION = "Header header                        # Standard metadata for higher-level stamped data types.\n                                     # * header.seq holds the number of requests since the plugin started\n                                     # * header.stamp timestamp related to the pose\n                                     # * header.frame_id not used but currently filled with the relative_entity_name\ngeometry_msgs/Pose pose              # pose of model in relative entity frame\ngeometry_msgs/Twist twist            # twist of model in relative entity frame\nbool success                         # return true if get successful\nstring status_message                # comments if available";
  std_msgs.Header getHeader();
  void setHeader(std_msgs.Header value);
  geometry_msgs.Pose getPose();
  void setPose(geometry_msgs.Pose value);
  geometry_msgs.Twist getTwist();
  void setTwist(geometry_msgs.Twist value);
  boolean getSuccess();
  void setSuccess(boolean value);
  java.lang.String getStatusMessage();
  void setStatusMessage(java.lang.String value);
}
