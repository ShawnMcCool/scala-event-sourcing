package Accounts

import DomainEvents._

object Account {

  object ID {
    def generate = ID(java.util.UUID.randomUUID.toString)
  }

  case class ID(id: String)

  def register(id: ID, name: String): Seq[DomainEvent] = Seq(AccountWasRegistered(id, name))

  def fromStream(id: ID, events: Seq[DomainEvent]): Account = {
    events.foldLeft(Account(id)) { (account, event) => {
      account.apply(event)
    }
    }
  }
}

case class Account(id: Account.ID, name: String = "", memberIds: Set[Member.ID] = Set()) {
  def add(m: Member): Seq[DomainEvent] = Seq(new MemberWasAddedToAccount(m.id, this.id))

  def equals(that: Account): Boolean = this.id.equals(that.id)

  // domain event application
  def apply(e: DomainEvent): Account = {
    def applyAccountWasRegistered(e: AccountWasRegistered) =
      copy(name = e.name)
    def applyMemberWasAddedToAccount(e: MemberWasAddedToAccount) =
      copy(memberIds = memberIds + e.memberId)

    e match {
      case event: AccountWasRegistered    => applyAccountWasRegistered(event)
      case event: MemberWasAddedToAccount => applyMemberWasAddedToAccount(event)
    }
  }
}
