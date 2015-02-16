package Accounts

import DomainEvents._
import EventStore._

object Member extends Aggregate[Member] {
  def register(id: Member.ID, email: Email): DomainEvent = MemberHasRegistered(id, email)

  def applyEvents(e: DomainEvent, member: Option[Member]): Member = e match {
    case event: MemberHasRegistered     => Member(event.id, event.email)
    case event: MemberChangedTheirEmail => member.get.copy(id = event.id, email = event.email)
    case _                              => throw new UnmatchedDomainEvent(e)
  }

  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }
  case class ID(id: String) extends AggregateIdentity
}

case class Member(id: Member.ID, email: Email) {
  def changeEmail(email: Email): DomainEvent = MemberChangedTheirEmail(id, email)
}
