package Commands

import Accounts._
import DomainEvents._
import EventStore._

object RegisterMember {
  def apply(id: String, email: String): RegisterMember =
    RegisterMember(Member.ID(id), Email(email))
}
case class RegisterMember(id: Member.ID, email: Email)

class RegisterMemberHandler(eventStore: EventStore) {
  def execute(c: RegisterMember) = {
    val events: Seq[DomainEvent] = Member.register(c.id, c.email)
    eventStore.store(events)
  }
}