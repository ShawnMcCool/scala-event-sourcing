package Accounts

import org.scalatest._

class EmailSpec extends FlatSpec with Matchers {
  "An email" should "represent an valid address" in {
    Email("aoe@aoe.com")

    a[IllegalArgumentException] should be thrownBy {
      Email("")
    }
    a[IllegalArgumentException] should be thrownBy {
      Email("aoe")
    }
    a[IllegalArgumentException] should be thrownBy {
      Email("@.com")
    }
  }

  "An email" should "display the address as a string" in {
    val email = Email("test@email.com")
    email.toString() should be("test@email.com")
  }

  "An email" should "compare equality by the address value" in {
    val email = Email("test@email.com")

    email == Email("test@email.com") should be(true)
    email == Email("different@email.com") should be(false)
  }
}