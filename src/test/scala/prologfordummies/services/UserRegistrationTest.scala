package prologfordummies.services

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import prologfordummies.model.User
import java.time.LocalDateTime


class UserRegistrationTest extends AnyFunSuite with Matchers:

  val fixedTime = LocalDateTime.of(2026, 1, 1, 12, 0)

  given testCreator: UserCreator with
    override def create(name: String): User =
      User(
        id = User.Id.random, 
        username = User.Name(name), 
        registrationDate = fixedTime
      )

  test("UserRegistration.signUp dovrebbe creare un utente con il nome corretto") {
    val name = "PrologMaster"
    val user = UserRegistration.signUp(name)
    user.username.asString shouldBe(name)
  }

  test("UserRegistration.signUp dovrebbe usare la data del mock") {
    val user = UserRegistration.signUp("AnyName")
    user.registrationDate shouldBe fixedTime
  }