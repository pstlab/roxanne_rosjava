package rosapi;

public interface PublishersResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/PublishersResponse";
  static final java.lang.String _DEFINITION = "string[] publishers";
  java.util.List<java.lang.String> getPublishers();
  void setPublishers(java.util.List<java.lang.String> value);
}
