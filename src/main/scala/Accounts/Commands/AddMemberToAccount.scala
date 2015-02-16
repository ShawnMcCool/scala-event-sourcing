package Accounts.Commands

import Accounts._
import EventStore._

object AddMemberToAccount {
  def apply(memberId: String, accountId: String): AddMemberToAccount =
    AddMemberToAccount(Member.ID(memberId), Account.ID(accountId))
}
case class AddMemberToAccount(memberId: Member.ID, accountId: Account.ID)

class AddMemberToAccountHandler(eventStore: EventStore) {
  def execute(c: AddMemberToAccount) = {
    val account = Account(eventStore.forAggregate(c.accountId))
    val member = Member(eventStore.forAggregate(c.memberId))
    val event = account.addMember(member)
    eventStore.store(c.accountId, event)
  }
}