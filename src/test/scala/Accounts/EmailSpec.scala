package Accounts

import org.scalatest._

class EmailSpec extends FlatSpec with Matchers {
  "An email" should "represent a valid address" in {
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

  "An email" should "be printed as its address" in {
    Email("test@email.com").toString() should be("test@email.com")
  }

  "An email" should "be equal to the same address" in {
    Email("test@email.com") == Email("test@email.com") should be(true)
    Email("test@email.com") == Email("different@email.com") should be(false)
  }
}