package DomainEvents

import Accounts._

sealed trait DomainEvent {}

object AccountWasRegistered {
  def apply(id: Account.ID, name: String): AccountWasRegistered =
    AccountWasRegistered(id.toString, name)
}
case class AccountWasRegistered(id: String, name: String) extends DomainEvent

object MemberWasAddedToAccount {
  def apply(memberId: Member.ID, accountId: Account.ID): MemberWasAddedToAccount =
    MemberWasAddedToAccount(memberId.toString, accountId.toString)
}
case class MemberWasAddedToAccount(memberId: String, accountId: String) extends DomainEvent

object MemberHasRegistered {
  def apply(id: Member.ID, email: Email): MemberHasRegistered =
    MemberHasRegistered(id.toString, email.toString)
}
case class MemberHasRegistered(id: String, email: String) extends DomainEvent

object MemberChangedTheirEmail {
  def apply(id: Member.ID, email: Email): MemberChangedTheirEmail =
    MemberChangedTheirEmail(id.toString, email.toString)
}
case class MemberChangedTheirEmail(id: String, email: String) extends DomainEvent

class UnmatchedDomainEvent(event: DomainEvent) extends Exception