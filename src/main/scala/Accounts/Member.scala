package Accounts

case class MemberId(id: String)
object MemberId {
  def generate = MemberId(java.util.UUID.randomUUID.toString)
}

case class Member(id: MemberId, name: String = "")
