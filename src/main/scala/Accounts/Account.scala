package Accounts

import DomainEvents._
import EventStore._

object Account extends Aggregate[Account] {
  def register(id: ID, name: String): DomainEvent = AccountWasRegistered(id, name)

  def applyEvent(e: DomainEvent, account: Option[Account]): Account = e match {
    case AccountWasRegistered(id, name) => Account(Account.ID(id), name, Set())
    case MemberWasAddedToAccount(id, _) => account.get.copy(memberIds = account.get.memberIds + Member.ID(id))
    case _                              => throw new UnmatchedDomainEvent(e)
  }

  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }
  case class ID(id: String) extends AggregateIdentity
}

case class Account(id: Account.ID, name: String, memberIds: Set[Member.ID]) {
  def addMember(m: Member): DomainEvent = MemberWasAddedToAccount(m.id, this.id)
}
