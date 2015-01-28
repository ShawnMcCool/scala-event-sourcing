package Accounts

class Account {
  var members: List[Member] = List();

  def add(m: Member) = {
    require (!members.contains(m))
    members = members :+ m
  }
}
