package nav_msgs;

public interface SetMap extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "nav_msgs/SetMap";
  static final java.lang.String _DEFINITION = "# Set a new map together with an initial pose\nnav_msgs/OccupancyGrid map\ngeometry_msgs/PoseWithCovarianceStamped initial_pose\n---\nbool success\n\n";
}
