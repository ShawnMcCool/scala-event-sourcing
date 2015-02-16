package Accounts

import DomainEvents._
import EventStore._

object Account extends Aggregate[Account] {
  def register(id: ID, name: String): Seq[DomainEvent] = Seq(AccountWasRegistered(id, name))

  def applyEvents(e: DomainEvent, account: Option[Account]): Account = e match {
    case event: AccountWasRegistered    => Account(event.id, event.name, Set())
    case event: MemberWasAddedToAccount => account.get.copy(memberIds = account.get.memberIds + event.memberId)
    case _                              => throw new UnmatchedDomainEvent(e)
  }

  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }
  case class ID(id: String) extends AggregateIdentity
}

case class Account(id: Account.ID, name: String, memberIds: Set[Member.ID]) {
  def addMember(m: Member): Seq[DomainEvent] = Seq(new MemberWasAddedToAccount(m.id, this.id))
}
