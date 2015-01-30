package Accounts

import org.scalatest._

class AccountSpec extends FlatSpec with Matchers {
  "An account" should "hold a list of members" in {
    val member = new Member
    val account = Account("My Account").add(member)
    account.members.size should be(1)
    account.members.contains(member) should be(true)
  }
}
