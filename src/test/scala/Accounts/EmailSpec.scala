package Accounts

import org.scalatest._

class EmailSpec extends FlatSpec with Matchers {
  "An email" should "represent an valid address" in {
    new Email("aoe@aoe.com")

    a[IllegalArgumentException] should be thrownBy {
      new Email("")
    }
    a[IllegalArgumentException] should be thrownBy {
      new Email("aoe")
    }
    a[IllegalArgumentException] should be thrownBy {
      new Email("@.com")
    }
  }

  "An email" should "display the address as a string" in {
    val email = new Email("test@email.com")
    email.toString() should be("test@email.com")
  }

  "An email" should "compare equality by the address value" in {
    val email = new Email("test@email.com")

    email == new Email("test@email.com") should be(true)
    email == new Email("different@email.com") should be(false)
  }
}