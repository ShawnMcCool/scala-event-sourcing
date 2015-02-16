package Accounts.Commands

import Accounts._
import DomainEvents._
import EventStore._
import org.scalamock.scalatest.MockFactory
import org.scalatest._

class MemberChangedTheirEmailSpec extends FlatSpec with Matchers with MockFactory {
  "The member's email" should "be changed" in {
    val id = Member.ID.generate

    val eventStore = new EventStore
    eventStore.store(id, MemberHasRegistered(id, Email("old@email.com")))

    // act
    new ChangeMembersEmailHandler(eventStore).execute(
      ChangeMembersEmail(id, Email("new@email.com"))
    )

    // assert
    eventStore.forAggregate(id) should be(Seq(
      MemberHasRegistered(id, Email("old@email.com")),
      MemberChangedTheirEmail(id, Email("new@email.com"))
    ))

    val member = Member(eventStore.forAggregate(id))
    member.email should be(Email("new@email.com"))
  }
}
