package Accounts

import DomainEvents._
import EventStore._

object Member {
  def apply(): Member = Member(Member.ID.empty, None)
  def apply(email: Email): Member = Member(Member.ID.empty, Some(email))

  def apply(events: Seq[DomainEvent]): Member = {
    events.foldLeft(Member()) {
      (member, event) => {
        member.apply(event)
      }
    }
  }

  def register(id: Member.ID, email: Email): Seq[DomainEvent] = Seq(MemberHasRegistered(id, email))

  case class ID(id: String) extends AggregateIdentity {
    def equals(that: ID): Boolean = this.id == that.id
  }

  object ID {
    def empty = ID("")
    def generate = ID(java.util.UUID.randomUUID.toString)
  }

}

case class Member(id: Member.ID, email: Option[Email]) {
  def changeEmail(email: Email): Seq[DomainEvent] =
    Seq(MemberChangedTheirEmail(id, email))

  def apply(e: DomainEvent): Member = {
    def applyMemberHasRegistered(e: MemberHasRegistered) =
      copy(id = e.id, email = Some(e.email))
    def applyMemberChangedTheirEmail(e: MemberChangedTheirEmail) =
      copy(id = e.id, email = Some(e.email))

    e match {
      case event: MemberHasRegistered     => applyMemberHasRegistered(event)
      case event: MemberChangedTheirEmail => applyMemberChangedTheirEmail(event)
      case _                              => throw new UnmatchedDomainEvent(e)
    }
  }
}
