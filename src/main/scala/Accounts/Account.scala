package Accounts

import DomainEvents._
import EventStore._

object Account extends Aggregate[Account] {
  def apply(): Account = Account(Account.ID.generate, "", Set())

  def register(id: ID, name: String): Seq[DomainEvent] = Seq(AccountWasRegistered(id, name))

  def applyEvents(e: DomainEvent, account: Option[Account]): Account = {
    def applyAccountWasRegistered(e: AccountWasRegistered) =
      Account(e.id, e.name, Set())
    def applyMemberWasAddedToAccount(e: MemberWasAddedToAccount) =
      account.get.copy(memberIds = account.get.memberIds + e.memberId)

    e match {
      case event: AccountWasRegistered    => applyAccountWasRegistered(event)
      case event: MemberWasAddedToAccount => applyMemberWasAddedToAccount(event)
      case _                              => throw new UnmatchedDomainEvent(e)
    }
  }

  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }

  case class ID(id: String) extends AggregateIdentity {
    def equals(that: ID): Boolean = this.id == that.id
  }

}

case class Account(id: Account.ID, name: String, memberIds: Set[Member.ID]) {
  def addMember(m: Member): Seq[DomainEvent] = Seq(new MemberWasAddedToAccount(m.id, this.id))

  def equals(that: Account): Boolean = this.id == that.id
}
