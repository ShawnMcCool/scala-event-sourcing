package Accounts

import DomainEvents._
import EventStore._

object Member extends Aggregate[Member] {
  def register(id: Member.ID, email: Email): DomainEvent = MemberHasRegistered(id, email)

  def applyEvent(e: DomainEvent, member: Option[Member]): Member = e match {
    case MemberHasRegistered(id, email)     => Member(Member.ID(id), Email(email))
    case MemberChangedTheirEmail(id, email) => member.get.copy(id = Member.ID(id), email = Email(email))
    case _                                  => throw new UnmatchedDomainEvent(e)
  }

  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }
  case class ID(id: String) extends AggregateIdentity
}

case class Member(id: Member.ID, email: Email) {
  def changeEmail(email: Email): MemberChangedTheirEmail = MemberChangedTheirEmail(id, email)
}
