package Accounts

import org.scalatest._

class AccountSpec extends FlatSpec with Matchers {
  "An account" should "accept new members" in {
    // arrange
    val member = Member(Member.ID.generate)
    val accountId = Account.ID.generate

    // act
    val eventStream = Account(accountId).add(member)

    // assert
    val account = Account.fromStream(accountId, eventStream)
    account.memberIds.size should be(1)
    account.memberIds.contains(member.id) should be(true)
  }
}
