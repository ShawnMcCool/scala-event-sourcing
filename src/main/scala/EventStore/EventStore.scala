package EventStore

import DomainEvents._

class EventStore {
  var events: Seq[DomainEvent] = List()
  def store(events: Seq[DomainEvent]) = {
    this.events :+ events
  }
}
