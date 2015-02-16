package Accounts.Commands

import Accounts._
import EventStore._

object RegisterMember {
  def apply(id: String, email: String): RegisterMember =
    RegisterMember(Member.ID(id), Email(email))
}
case class RegisterMember(id: Member.ID, email: Email)

class RegisterMemberHandler(eventStore: EventStore) {
  def execute(c: RegisterMember) =
    eventStore.store(c.id, Seq(Member.register(c.id, c.email)))
}