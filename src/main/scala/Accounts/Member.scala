package Accounts

object Member {
  def apply(email: Email): Member = Member(Member.ID.generate, email)
  def register(email: Email): Member = Member(Member.ID.generate, email)

  case class ID(id: String) {
    def ==(that: ID): Boolean = this.id == that.id
  }
  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }
}
case class Member(id: Member.ID, email: Email) {
  def equals(that: Member): Boolean = this.id == that.id
}
