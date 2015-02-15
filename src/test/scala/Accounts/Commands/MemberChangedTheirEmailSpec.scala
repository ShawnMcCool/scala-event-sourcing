package Accounts.Commands

import Accounts._
import DomainEvents._
import EventStore._
import org.scalamock.scalatest.MockFactory
import org.scalatest._

class MemberChangedTheirEmailSpec extends FlatSpec with Matchers with MockFactory {
  "The member's email" should "be changed" in {
    // arrange
    val id = Member.ID.generate

    val eventStore = mock[EventStore]
    val setupEvents: Seq[DomainEvent] = Seq(MemberHasRegistered(id, Email("old@email.com")))
    val resultingEvents: Seq[DomainEvent] = Seq(MemberChangedTheirEmail(id, Email("new@email.com")))
    (eventStore.forAggregate _).expects(id).returns(setupEvents)
    (eventStore.store _).expects(id, resultingEvents)

    // act
    new ChangeMembersEmailHandler(eventStore).execute(
      ChangeMembersEmail(id, Email("new@email.com"))
    )

    // assert
    Member(setupEvents ++ resultingEvents).email.get should be(Email("new@email.com"))
  }
}
