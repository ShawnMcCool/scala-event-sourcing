package Accounts

import DomainEvents._

object Member {
  def apply(): Member = Member(null, null)

  def apply(email: Email): Member = Member(Member.ID.generate, email)

  def apply(events: Seq[DomainEvent]): Member = {
    events.foldLeft(Member(null, null)) {
      (member, event) => {
        member.apply(event)
      }
    }
  }

  def register(id: Member.ID, email: Email): Seq[DomainEvent] = List(MemberHasRegistered(id, email))

  case class ID(id: String) {
    def ==(that: ID): Boolean = this.id == that.id
  }

  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }

}

case class Member(id: Member.ID, email: Email) {
  def equals(that: Member): Boolean = this.id == that.id

  def apply(e: DomainEvent): Member = {
    def applyMemberHasRegistered(e: MemberHasRegistered) =
      copy(id = e.id, email = e.email)

    e match {
      case event: MemberHasRegistered => applyMemberHasRegistered(event)
      case _                          => throw new UnmatchedDomainEvent(e)
    }
  }
}
