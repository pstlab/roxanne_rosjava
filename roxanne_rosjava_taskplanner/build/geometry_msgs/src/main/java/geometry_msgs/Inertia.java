package geometry_msgs;

public interface Inertia extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "geometry_msgs/Inertia";
  static final java.lang.String _DEFINITION = "# Mass [kg]\nfloat64 m\n\n# Center of mass [m]\ngeometry_msgs/Vector3 com\n\n# Inertia Tensor [kg-m^2]\n#     | ixx ixy ixz |\n# I = | ixy iyy iyz |\n#     | ixz iyz izz |\nfloat64 ixx\nfloat64 ixy\nfloat64 ixz\nfloat64 iyy\nfloat64 iyz\nfloat64 izz\n";
  double getM();
  void setM(double value);
  geometry_msgs.Vector3 getCom();
  void setCom(geometry_msgs.Vector3 value);
  double getIxx();
  void setIxx(double value);
  double getIxy();
  void setIxy(double value);
  double getIxz();
  void setIxz(double value);
  double getIyy();
  void setIyy(double value);
  double getIyz();
  void setIyz(double value);
  double getIzz();
  void setIzz(double value);
}
