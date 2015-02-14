package Accounts

import DomainEvents._
import org.scalatest._

class MemberSpec extends FlatSpec with Matchers {
  "A generated ID" should "be unique" in {
    Member.ID.generate should not be(Member.ID.generate)
  }

  "Member registration" should "raise a MemberHasRegistered event" in {
    val id = Member.ID.generate
    val events: Seq[DomainEvent] = Member.register(id, Email("test@tester.com"))

    events.contains(
      MemberHasRegistered(id, Email("test@tester.com"))
    ) should be(true)
  }

  "A registered member" should "have an id and email" in {
    val id = Member.ID.generate

    val events: Seq[DomainEvent] = List(MemberHasRegistered(id, Email("test@tester.com")))

    val member = Member(events)
    member.id should be(id)
    member.email should be(Email("test@tester.com"))
  }
}