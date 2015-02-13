package Accounts

import org.scalatest._

class MemberSpec extends FlatSpec with Matchers {
  "A generated ID" should "be unique" in {
    Member.ID.generate should not be(Member.ID.generate)
  }

  "Member registration" should "raise a MemberHasRegistered event" in {
    val member = Member.register(Email("test@tester.com"))
  }
//  "A member" should "measure equality by ID" in {
//    val member1 = Member(Seq(AccountWasRegistered(Account.ID("1"), "")))
//    val member2 = Member(Seq(AccountWasRegistered(Account.ID("1"), "")))
//    account1 should be(account2)
//  }
}