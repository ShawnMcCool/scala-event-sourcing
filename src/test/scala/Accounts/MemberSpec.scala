package Accounts

import DomainEvents._
import org.scalatest._

class MemberSpec extends FlatSpec with Matchers {
  "A generated ID" should "be unique" in {
    Member.ID.generate should not be(Member.ID.generate)
  }

  "Member registration" should "raise MemberHasRegistered" in {
    val id = Member.ID.generate

    Member.register(id, Email("test@tester.com")) == MemberHasRegistered(id, Email("test@tester.com")) should be(true)
  }

  "A registered member" should "have an id and email" in {
    val id = Member.ID.generate

    val events: Seq[DomainEvent] = Seq(MemberHasRegistered(id, Email("test@tester.com")))

    val member = Member(events)
    member.id should be(id)
    member.email should be(Email("test@tester.com"))
  }
}