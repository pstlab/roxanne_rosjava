package rosapi;

public interface ServiceResponseDetailsResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/ServiceResponseDetailsResponse";
  static final java.lang.String _DEFINITION = "TypeDef[] typedefs";
  java.util.List<rosapi.TypeDef> getTypedefs();
  void setTypedefs(java.util.List<rosapi.TypeDef> value);
}
