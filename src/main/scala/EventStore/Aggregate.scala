package EventStore

import DomainEvents._

trait Aggregate[T] {
  def applyEvents(e: DomainEvent, a: Option[T]): T

  def apply(events: Seq[DomainEvent]): T = {
    val agg: Option[T] = None

    events.foldLeft(agg) {
      (aggregate, event) => {
        Some(this.applyEvents(event, aggregate))
      }
    } match {
      case Some(m) => m
      case None    => throw new CouldNotPlayBackAggregate
    }
  }
}

trait AggregateIdentity {
  val id: String
  override def toString = id
}

class CouldNotPlayBackAggregate extends Exception