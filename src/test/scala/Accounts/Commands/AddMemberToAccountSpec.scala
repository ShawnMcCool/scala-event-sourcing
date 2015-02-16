package Accounts.Commands

import Accounts._
import DomainEvents._
import EventStore._
import org.scalamock.scalatest.MockFactory
import org.scalatest._

class AddMemberToAccountSpec extends FlatSpec with Matchers with MockFactory {
  "The member" should "be added to the account" in {
    val memberId = Member.ID.generate
    val accountId = Account.ID.generate

    val eventStore = new MemoryEventStore
    eventStore.store(memberId, MemberHasRegistered(memberId, Email("old@email.com")))
    eventStore.store(accountId, AccountWasRegistered(accountId, "Test Account"))

    // act
    new AddMemberToAccountHandler(eventStore).execute(
      AddMemberToAccount(memberId, accountId)
    )

    // assert
    eventStore.forAggregate(accountId) should be(Seq(
      AccountWasRegistered(accountId, "Test Account"),
      MemberWasAddedToAccount(memberId, accountId)
    ))

    val account = Account(eventStore.forAggregate(accountId))
    account.memberIds.contains(memberId) should be(true)
  }
}
