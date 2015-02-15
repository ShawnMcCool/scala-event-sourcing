package Accounts.Commands

import Accounts._
import EventStore._

object ChangeMembersEmail {
  def apply(id: String, email: String): ChangeMembersEmail =
    ChangeMembersEmail(Member.ID(id), Email(email))
}
case class ChangeMembersEmail(id: Member.ID, email: Email)

class ChangeMembersEmailHandler(events: EventStore) {
  def execute(c: ChangeMembersEmail) = {
    val member = Member(events.forAggregate(c.id))
    events.store(member.id,
      member.changeEmail(c.email)
    )
  }
  
}