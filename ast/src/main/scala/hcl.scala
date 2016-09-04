package hcl


sealed trait HCL

case class HString(value: String) extends HCL
case class HBool(value: Boolean) extends HCL
case class HNumber(value: HCLNumber) extends HCL
case class HArray(value: HCLArray) extends HCL
case class HObject(value: HCLObject) extends HCL

case class HCLObject(map: Map[Field,HCL] = Map.empty)

sealed trait HCLNumber
case class HCLLong(value: Long) extends HCLNumber
case class HCLDecimal(value: BigDecimal) extends HCLNumber


