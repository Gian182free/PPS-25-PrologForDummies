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
    override def update(userEdited: User): Unit = 
      users = users.map(u => if u.id == userEdited.id then userEdited else u)
    override def delete(id: User.Id): Unit = 
      users = users.filterNot(_.id == id)

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

  test("delete dovrebbe rimuovere l'utente specificato") {
    val user = UserRegistration.signUp("ToDelete")
    testRepo.delete(user.id)
    testRepo.findByName("ToDelete") shouldBe None
  }

  test("update dovrebbe modificare i dati di un utente già esistente") {
    val user = UserRegistration.signUp("oldUser")
    val updatedUser = user.copy(username = User.Name("newUser"))
    
    testRepo.update(updatedUser)
    
    testRepo.findByName("oldUser") shouldBe None
    testRepo.findByName("newUser").isDefined shouldBe true
  }

  test("signup dovrebbe lanciare un'eccezione se il nome è vuoto") {
    an [IllegalArgumentException] should be thrownBy UserRegistration.signUp("")
  }

  test("signup dovrebbe lanciare un'eccezione se il nome è già utilizzato") {
    val name = "doppione"
    UserRegistration.signUp(name)
    
    an [IllegalArgumentException] should be thrownBy UserRegistration.signUp(name)
  }