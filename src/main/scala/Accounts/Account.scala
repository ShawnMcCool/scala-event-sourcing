package Accounts

trait DomainEvent {}

case class AccountWasRegistered(id: AccountId, name: String) extends DomainEvent
case class MemberWasAddedToAccount(memberId: MemberId, accountId: AccountId) extends DomainEvent

case class AccountId(id: String)

object AccountId {
  def generate = AccountId(java.util.UUID.randomUUID.toString)
}

object Account {
  def register(id: AccountId, name: String): Seq[DomainEvent] = Seq(AccountWasRegistered(id, name))

  def fromStream(id: AccountId, events: Seq[DomainEvent]): Account = {
    events.foldLeft(Account(id)) { (account, event) => { account.apply(event) }}
  }
}

case class Account(id: AccountId, name: String = "", memberIds: Set[MemberId] = Set()) {
  def add(m: Member): Seq[DomainEvent] = Seq(new MemberWasAddedToAccount(m.id, this.id))

  def apply(e: DomainEvent) = e match {
    case event: AccountWasRegistered => applyAccountWasRegistered(event)
    case event: MemberWasAddedToAccount => applyMemberWasAddedToAccount(event)
  }

  private def applyAccountWasRegistered(e: AccountWasRegistered) =
    copy(name = e.name)

  private def applyMemberWasAddedToAccount(e: MemberWasAddedToAccount) =
    copy(memberIds = memberIds + e.memberId)
}