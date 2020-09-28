package gazebo_msgs;

public interface GetLightPropertiesResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "gazebo_msgs/GetLightPropertiesResponse";
  static final java.lang.String _DEFINITION = "std_msgs/ColorRGBA diffuse           # diffuse color as red, green, blue, alpha\nfloat64 attenuation_constant\nfloat64 attenuation_linear\nfloat64 attenuation_quadratic\nbool success                         # return true if get successful\nstring status_message                # comments if available";
  std_msgs.ColorRGBA getDiffuse();
  void setDiffuse(std_msgs.ColorRGBA value);
  double getAttenuationConstant();
  void setAttenuationConstant(double value);
  double getAttenuationLinear();
  void setAttenuationLinear(double value);
  double getAttenuationQuadratic();
  void setAttenuationQuadratic(double value);
  boolean getSuccess();
  void setSuccess(boolean value);
  java.lang.String getStatusMessage();
  void setStatusMessage(java.lang.String value);
}
