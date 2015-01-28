package Accounts

import org.scalatest._

class AccountSpec extends FlatSpec with Matchers {
  "An account" should "hold a list of members" in {
    val account = new Account
    val member = new Member
    account.add(member)
    account.members.length should be(1)
    account.add(new Member)
    account.members.length should be(2)
    account.members(0) should be(member)
  }

  "An account" should "not hold a member more than once" in {
    val account = new Account
    val member = new Member
    account.add(member)
    a[IllegalArgumentException] should be thrownBy {
      account.add(member)
    }
  }
}
