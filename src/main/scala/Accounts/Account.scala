package Accounts

case class Account(name: String, members: Set[Member] = Set()) {
  def add(m: Member): Account = copy(members = members + m)
}