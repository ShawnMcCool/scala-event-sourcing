package Accounts

import DomainEvents._
import EventStore._

object Member extends Aggregate[Member] {
  def register(id: Member.ID, email: Email): Seq[DomainEvent] = Seq(MemberHasRegistered(id, email))

  // Apply Domain Events
  def applyEvents(e: DomainEvent, member: Option[Member]): Member = {
    def applyMemberHasRegistered(e: MemberHasRegistered) =
      Member(e.id, e.email)
    def applyMemberChangedTheirEmail(e: MemberChangedTheirEmail) =
      member.get.copy(id = e.id, email = e.email)

    e match {
      case event: MemberHasRegistered     => applyMemberHasRegistered(event)
      case event: MemberChangedTheirEmail => applyMemberChangedTheirEmail(event)
      case _                              => throw new UnmatchedDomainEvent(e)
    }
  }

  // Member ID
  object ID {
    def empty = ID("")
    def generate = ID(java.util.UUID.randomUUID.toString)
  }
  case class ID(id: String) extends AggregateIdentity {
    def equals(that: ID): Boolean = this.id == that.id
  }

}

case class Member(id: Member.ID, email: Email) {
  def changeEmail(email: Email): Seq[DomainEvent] =
    Seq(MemberChangedTheirEmail(id, email))
}
