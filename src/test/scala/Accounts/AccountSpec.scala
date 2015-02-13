package Accounts

import DomainEvents._
import org.scalatest._

class AccountSpec extends FlatSpec with Matchers {
  "A generated ID" should "be unique" in {
    Account.ID.generate should not be(Account.ID.generate)
  }

  "Account registration" should "trigger a AccountWasRegistered event" in {
    val id = Account.ID.generate
    val name = "BurgerCo"

    val events = Account.register(id, name)

    events.contains(
      AccountWasRegistered(id, name)
    ) should be(true)
  }

  "A registered account" should "have a name and id" in {
    val id = Account.ID.generate
    val name = "BurgerCo"

    val events: Seq[DomainEvent] = List(AccountWasRegistered(id, name))

    val account = Account(id, events)
    account.id should be(id)
    account.name should be(name)
  }

  "Adding a new member" should "throw a MemberWasAddedToAccount event" in {
    // arrange
    val member = Member(Member.ID.generate)
    val accountId = Account.ID.generate

    // act
    val events = Account(accountId).add(member)

    // assert - events
    events.contains(
      MemberWasAddedToAccount(member.id, accountId)
    ) should be(true)
  }

  "An account" should "contain an added member" in {
    val member = Member(Member.ID.generate)
    val accountId = Account.ID.generate

    val events: Seq[DomainEvent] = List(MemberWasAddedToAccount(member.id, accountId))

    // assert - state
    val account = Account(accountId, events)
    account.memberIds.size should be(1)
    account.memberIds.contains(member.id) should be(true)
  }

  "An account" should "measure equality by ID" in {
    Account(Account.ID("1")) should be(Account(Account.ID("1")))
  }
}
