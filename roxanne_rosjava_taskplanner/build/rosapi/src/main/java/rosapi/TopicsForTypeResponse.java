package rosapi;

public interface TopicsForTypeResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/TopicsForTypeResponse";
  static final java.lang.String _DEFINITION = "string[] topics";
  java.util.List<java.lang.String> getTopics();
  void setTopics(java.util.List<java.lang.String> value);
}
