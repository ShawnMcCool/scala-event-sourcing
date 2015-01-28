package Accounts

class Email(email: String) {
  def isValid(email: String): Boolean = """\b[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*\b""".r.unapplySeq(email).isDefined

  val address = email
  require(isValid(email))

  override def toString(): String = address

  def ==(that: Email): Boolean = this.address == that.address
}
