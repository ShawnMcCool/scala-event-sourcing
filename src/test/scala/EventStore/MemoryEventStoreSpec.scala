package EventStore

import DomainEvents._
import org.scalatest._
import org.scalamock.scalatest.MockFactory

case class TestID(id: String) extends AggregateIdentity

class MemoryEventStoreSpec extends FlatSpec with Matchers with MockFactory {
  "The event store" should "store one event" in {
    val id = TestID("1")
    val event = MemberHasRegistered("1", "test@test.com")

    val eventStore = new MemoryEventStore
    eventStore.store(id, event)

    eventStore.forAggregate(id) should be(Seq(event))
  }

  "The event store" should "store multiple events" in {
    val id = TestID("1")
    val events: Seq[DomainEvent] = Seq(
      MemberHasRegistered("1", "test@test.com"),
      MemberHasRegistered("1", "test@test.com")
    )

    val eventStore = new MemoryEventStore
    eventStore.store(id, events)

    eventStore.forAggregate(id) should be(events)
  }

  "The event store" should "continue to store events" in {
    val id = TestID("1")

    val eventStore = new MemoryEventStore
    eventStore.store(id, MemberHasRegistered("1", "test@test.com"))
    eventStore.store(id, MemberHasRegistered("1", "test@test.com"))

    eventStore.forAggregate(id) should be(Seq(
      MemberHasRegistered("1", "test@test.com"),
      MemberHasRegistered("1", "test@test.com")
    ))
  }
}