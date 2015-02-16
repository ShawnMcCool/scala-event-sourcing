package Accounts

import DomainEvents._
import EventStore._

object Member extends Aggregate[Member] {
  def register(id: Member.ID, email: Email): DomainEvent = MemberHasRegistered(id, email)

  def applyEvents(e: DomainEvent, member: Option[Member]): Member = e match {
    case event: MemberHasRegistered     => Member(Member.ID(event.id), Email(event.email))
    case event: MemberChangedTheirEmail => member.get.copy(id = Member.ID(event.id), email = Email(event.email))
    case _                              => throw new UnmatchedDomainEvent(e)
  }

  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }
  case class ID(id: String) extends AggregateIdentity {
    override def toString: String = id
  }
}

case class Member(id: Member.ID, email: Email) {
  def changeEmail(email: Email): DomainEvent = MemberChangedTheirEmail(id, email)
}
