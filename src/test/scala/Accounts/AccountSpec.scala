package Accounts

import DomainEvents._
import org.scalatest._

class AccountSpec extends FlatSpec with Matchers {
  def getMember(): Member = {
    Member(Seq(MemberHasRegistered(Member.ID.generate, Email("test@user.com"))))
  }
  def registerAccount(): Seq[DomainEvent] = {
    Seq(AccountWasRegistered(Account.ID.generate, "Test Account"))
  }

  "A generated ID" should "be unique" in {
    Account.ID.generate should not be (Account.ID.generate)
  }

  "Account registration" should "raise AccountWasRegistered" in {
    val id = Account.ID.generate
    val name = "BurgerCo"

    Account.register(id, name) == AccountWasRegistered(id, name) should be(true)
  }

  "A registered account" should "have a name and id" in {
    val id = Account.ID.generate
    val name = "BurgerCo"

    val account = Account(Seq(AccountWasRegistered(id, name)))
    account.id should be(id)
    account.name should be(name)
  }

  "A member" should "not be added more than once" in {
    val member = getMember()

    val registration = registerAccount()
    val memberAddition = Account(registration).addMember(member)

    val account = Account(registration :+ memberAddition)

    a[MemberCannotBeAddedMoreThanOnce] should be thrownBy {
      account.addMember(member)
    }
  }

  "Adding a new member" should "raise MemberWasAddedToAccount" in {
    val member = getMember()
    val accountId = Account.ID.generate
    val account = Account(Seq(AccountWasRegistered(accountId, "")))

    account.addMember(member) == MemberWasAddedToAccount(member.id, accountId) should be(true)
  }

  "An account" should "contain an added member" in {
    val member = getMember()
    val accountId = Account.ID.generate

    val account = Account(Seq(
      AccountWasRegistered(Account.ID.generate, "BurgerCo"),
      MemberWasAddedToAccount(member.id, accountId)
    ))

    account.memberIds.size should be(1)
    account.memberIds.contains(member.id) should be(true)
  }
}
