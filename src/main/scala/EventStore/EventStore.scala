package EventStore

import DomainEvents._

import scala.collection.mutable

trait EventStore {
  def store(id: AggregateIdentity, e: DomainEvent): Unit
  def store(id: AggregateIdentity, es: Seq[DomainEvent]): Unit
  def forAggregate(id: AggregateIdentity): Seq[DomainEvent]
}

class MemoryEventStore extends EventStore {
  val events = mutable.Map[String, Seq[DomainEvent]]()
  def store(id: AggregateIdentity, e: DomainEvent): Unit = store(id, Seq(e))
  def store(id: AggregateIdentity, es: Seq[DomainEvent]): Unit =
    if (events.contains(id.toString)) events(id.toString) = events(id.toString) ++ es
    else events(id.toString) = es
  def forAggregate(id: AggregateIdentity): Seq[DomainEvent] = events(id.toString)
}
