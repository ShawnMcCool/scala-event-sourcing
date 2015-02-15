package EventStore

import DomainEvents._

import scala.collection.mutable

class EventStore {
  var events = mutable.Map[AggregateIdentity, Seq[DomainEvent]]()
  def store(id: AggregateIdentity, events: Seq[DomainEvent]) =
    this.events += (id -> events)
  def forAggregate(id: AggregateIdentity): Seq[DomainEvent] =
    events(id)
}
