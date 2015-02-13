package DomainEvents

import Accounts._

sealed trait DomainEvent {}

case class AccountWasRegistered(id: Account.ID, name: String) extends DomainEvent

case class MemberWasAddedToAccount(memberId: Member.ID, accountId: Account.ID) extends DomainEvent
