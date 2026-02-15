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

  given testRepo: UserRepository with
    private var users = List.empty[User]
    override def save(user: User): Unit = users = users :+ user
    override def loadAll(): List[User] = users
    override def findByName(name: String): Option[User] = 
      users.find(_.username.asString == name)

  test("signUp dovrebbe creare e salvare un utente") {
    val name = "UserTest"
    val user = UserRegistration.signUp(name)
    
    user.username.asString shouldBe name
    testRepo.loadAll() should contain (user)
  }

  test("findByName dovrebbe trovare un utente esistente") {
    val name = "SearchMe"
    UserRegistration.signUp(name)
    
    val found = testRepo.findByName(name)
    found.isDefined shouldBe true
    found.get.username.asString shouldBe name
  }

  test("findByName non dovrebbe trovare un utente che non esiste") {
    val found = testRepo.findByName("Ghost")
    found shouldBe None
  }

  test("loadAll dovrebbe restituire tutti gli utenti registrati") {
    val initialCount = testRepo.loadAll().size
    UserRegistration.signUp("User1")
    UserRegistration.signUp("User2")
    
    testRepo.loadAll().size shouldBe (initialCount + 2)
  }