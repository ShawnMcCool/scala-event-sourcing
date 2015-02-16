package EventStore

import DomainEvents._

trait Aggregate[T] {
  def applyEvents(e: DomainEvent, a: Option[T]): T

  def apply(events: Seq[DomainEvent]): T = {
    val m: Option[T] = None

    events.foldLeft(m) {
      (aggregate, event) => {
        Some(this.applyEvents(event, aggregate))
      }
    } match {
      case Some(m) => m
      case None    => throw new Exception
    }
  }
}

trait AggregateIdentity