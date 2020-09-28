package rosapi;

public interface TypeDef extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/TypeDef";
  static final java.lang.String _DEFINITION = "string type\nstring[] fieldnames\nstring[] fieldtypes\nint32[] fieldarraylen\nstring[] examples\nstring[] constnames\nstring[] constvalues\n";
  java.lang.String getType();
  void setType(java.lang.String value);
  java.util.List<java.lang.String> getFieldnames();
  void setFieldnames(java.util.List<java.lang.String> value);
  java.util.List<java.lang.String> getFieldtypes();
  void setFieldtypes(java.util.List<java.lang.String> value);
  int[] getFieldarraylen();
  void setFieldarraylen(int[] value);
  java.util.List<java.lang.String> getExamples();
  void setExamples(java.util.List<java.lang.String> value);
  java.util.List<java.lang.String> getConstnames();
  void setConstnames(java.util.List<java.lang.String> value);
  java.util.List<java.lang.String> getConstvalues();
  void setConstvalues(java.util.List<java.lang.String> value);
}
