package Accounts

object Member {
  case class ID(id: String)
  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }
}
case class Member(id: Member.ID, name: String = "")
