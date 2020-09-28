package rosapi;

public interface ServiceRequestDetailsResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/ServiceRequestDetailsResponse";
  static final java.lang.String _DEFINITION = "TypeDef[] typedefs";
  java.util.List<rosapi.TypeDef> getTypedefs();
  void setTypedefs(java.util.List<rosapi.TypeDef> value);
}
