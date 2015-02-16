package EventStore

import DomainEvents._

trait Aggregate[T] {
  def applyEvent(e: DomainEvent, a: Option[T]): T

  def apply(events: Seq[DomainEvent]): T = {
    events.foldLeft(Option.empty[T]) {
      (aggregate, event) => {
        Some(this.applyEvent(event, aggregate))
      }
    } getOrElse {
      throw new CouldNotPlayBackAggregate
    }
  }
}

trait AggregateIdentity {
  val id: String

  override def toString = id
}

class CouldNotPlayBackAggregate extends Error