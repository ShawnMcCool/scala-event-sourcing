package Accounts.Commands

import DomainEvents._
import EventStore._
import Accounts._
import org.scalatest._
import org.scalamock.scalatest.MockFactory

class RegisterMemberSpec extends FlatSpec with Matchers with MockFactory {
  "The handler" should "register a member" in {
    val memberId = Member.ID.generate
    val events: Seq[DomainEvent] = List(MemberHasRegistered(memberId, Email("test@test.com")))

    val eventStore = mock[EventStore]
    (eventStore.store _).expects(memberId, events)

    new RegisterMemberHandler(eventStore).execute(
      RegisterMember(memberId, Email("test@test.com"))
    )
  }
}
