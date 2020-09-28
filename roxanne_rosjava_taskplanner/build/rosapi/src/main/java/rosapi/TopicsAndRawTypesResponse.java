package rosapi;

public interface TopicsAndRawTypesResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/TopicsAndRawTypesResponse";
  static final java.lang.String _DEFINITION = "string[] topics\nstring[] types\nstring[] typedefs_full_text";
  java.util.List<java.lang.String> getTopics();
  void setTopics(java.util.List<java.lang.String> value);
  java.util.List<java.lang.String> getTypes();
  void setTypes(java.util.List<java.lang.String> value);
  java.util.List<java.lang.String> getTypedefsFullText();
  void setTypedefsFullText(java.util.List<java.lang.String> value);
}
