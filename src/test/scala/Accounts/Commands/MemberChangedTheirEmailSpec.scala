package Accounts.Commands

import Accounts._
import DomainEvents._
import EventStore._
import org.scalamock.scalatest.MockFactory
import org.scalatest._

class MemberChangedTheirEmailSpec extends FlatSpec with Matchers with MockFactory {
  "The member's email" should "be changed" in {
    val id = Member.ID.generate

    val eventStore = mock[EventStore]
    val setupEvent: DomainEvent = MemberHasRegistered(id, Email("old@email.com"))
    val resultingEvent: DomainEvent = MemberChangedTheirEmail(id, Email("new@email.com"))

    (eventStore.forAggregate _).expects(id).returns(Seq(setupEvent))
    (eventStore.store _).expects(id, Seq(resultingEvent))

    // act
    new ChangeMembersEmailHandler(eventStore).execute(
      ChangeMembersEmail(id, Email("new@email.com"))
    )

    // assert
    val member = Member(Seq(setupEvent, resultingEvent))
    member.email should be(Email("new@email.com"))
  }
}
