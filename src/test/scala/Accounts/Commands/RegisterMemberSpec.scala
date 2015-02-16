package Accounts.Commands

import EventStore._
import Accounts._
import org.scalatest._
import org.scalamock.scalatest.MockFactory

class RegisterMemberSpec extends FlatSpec with Matchers with MockFactory {
  "The handler" should "register a member" in {
    val id = Member.ID.generate
    val eventStore = new MemoryEventStore

    new RegisterMemberHandler(eventStore).execute(
      RegisterMember(id, Email("test@test.com"))
    )

    val member = Member(eventStore.forAggregate(id))
    member.id should be(id)
    member.email should be(Email("test@test.com"))
  }
}
