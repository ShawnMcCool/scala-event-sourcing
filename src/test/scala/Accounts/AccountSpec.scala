package Accounts

import DomainEvents._
import org.scalatest._

class AccountSpec extends FlatSpec with Matchers {
  def getMember(): Member = {
    Member(Member.ID.generate, Email("test@test.com"))
  }

  "A generated ID" should "be unique" in {
    Account.ID.generate should not be(Account.ID.generate)
  }

  "Account registration" should "raise AccountWasRegistered" in {
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

    val account = Account(events)
    account.id should be(id)
    account.name should be(name)
  }

  "Adding a new member" should "raise MemberWasAddedToAccount" in {
    val member = getMember()
    val accountId = Account.ID.generate
    val account = Account(Seq(AccountWasRegistered(accountId, "")))

    val events = account.addMember(member)

    events.contains(
      MemberWasAddedToAccount(member.id, accountId)
    ) should be(true)
  }

  "An account" should "contain an added member" in {
    val member = getMember()
    val accountId = Account.ID.generate

    val events: Seq[DomainEvent] = Seq(MemberWasAddedToAccount(member.id, accountId))

    val account = Account(
      Seq(AccountWasRegistered(Account.ID.generate, "BurgerCo")) ++ events
    )

    account.memberIds.size should be(1)
    account.memberIds.contains(member.id) should be(true)
  }

  "An account" should "measure equality by ID" in {
    val account1 = Account(Seq(AccountWasRegistered(Account.ID("1"), "")))
    val account2 = Account(Seq(AccountWasRegistered(Account.ID("1"), "")))
    account1 should be(account2)
  }
}
