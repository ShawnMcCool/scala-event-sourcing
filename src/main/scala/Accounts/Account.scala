package Accounts

import DomainEvents._
import EventStore._

object Account extends Aggregate[Account] {
  def register(id: ID, name: String): DomainEvent = AccountWasRegistered(id, name)

  def applyEvents(e: DomainEvent, account: Option[Account]): Account = e match {
    case event: AccountWasRegistered    => Account(Account.ID(event.id), event.name, Set())
    case event: MemberWasAddedToAccount => account.get.copy(memberIds = account.get.memberIds + Member.ID(event.memberId))
    case _                              => throw new UnmatchedDomainEvent(e)
  }

  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }
  case class ID(id: String) extends AggregateIdentity {
    override def toString = id
  }
}

case class Account(id: Account.ID, name: String, memberIds: Set[Member.ID]) {
  def addMember(m: Member): DomainEvent = MemberWasAddedToAccount(m.id, this.id)
}
