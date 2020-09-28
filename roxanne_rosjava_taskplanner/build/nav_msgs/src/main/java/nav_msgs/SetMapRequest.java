package nav_msgs;

public interface SetMapRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "nav_msgs/SetMapRequest";
  static final java.lang.String _DEFINITION = "# Set a new map together with an initial pose\nnav_msgs/OccupancyGrid map\ngeometry_msgs/PoseWithCovarianceStamped initial_pose\n";
  nav_msgs.OccupancyGrid getMap();
  void setMap(nav_msgs.OccupancyGrid value);
  geometry_msgs.PoseWithCovarianceStamped getInitialPose();
  void setInitialPose(geometry_msgs.PoseWithCovarianceStamped value);
}
