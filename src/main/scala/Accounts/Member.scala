package Accounts

import DomainEvents._
import EventStore._

object Member extends Aggregate[Member] {
  def register(id: Member.ID, email: Email): Seq[DomainEvent] = Seq(MemberHasRegistered(id, email))

  // Apply Domain Events
  def applyEvents(e: DomainEvent, member: Option[Member]): Member = e match {
    case event: MemberHasRegistered     => Member(event.id, event.email)
    case event: MemberChangedTheirEmail => member.get.copy(id = event.id, email = event.email)
    case _                              => throw new UnmatchedDomainEvent(e)
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
