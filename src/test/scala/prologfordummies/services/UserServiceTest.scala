package prologfordummies.services

import org.scalatest.funsuite.AnyFunSuite
import prologfordummies.model.User
import java.time.LocalDateTime

class UserServiceTest extends AnyFunSuite {

  /** 
   * Implementazione di un repository in memoria RAM, usando una List
  **/
  class fakeUserRepository extends UserRepository {
    var users: List[User] = List.empty
    
    override def save(user: User): Unit = users = users :+ user
    override def loadAll(): List[User] = users
    override def findByName(name: String): Option[User] = 
      users.find(_.username.asString.equalsIgnoreCase(name.trim))    
    override def update(user: User): Unit = 
      users = users.map(u => if u.id == user.id then user else u)   
    override def delete(user: User): Unit = 
      users = users.filterNot(_.id == user.id)
  }

  def createService = 
    val fakeRepo = new fakeUserRepository()
    val service = UserServiceImpl.liveService(using fakeRepo)
    (service, fakeRepo)

  test("signUp dovrebbe fallire se il nome è vuoto") {
    val (service, repo) = createService
    val result = service.signUp("   ")
    
    assert(result.isLeft)
    assert(repo.users.isEmpty)
  }

  test("signUp dovrebbe creare un utente se il nome non è già utilizzato") {
    val (service, repo) = createService
    val result = service.signUp("TestUser")
    
    assert(result.isRight)
    assert(repo.users.exists(_.username.asString == "TestUser"))
  }

  test("signUp dovrebbe fallire se il nome esiste già") {
    val (service, repo) = createService
    service.signUp("user")
    val result = service.signUp("User")
    
    assert(result.isLeft)
    assert(repo.users.size == 1)
  }

  test("updateUser dovrebbe fallire se il nuovo nome è già usato") {
    val (service, repo) = createService
    val user1 = User(User.Id.random, User.Name("User"), LocalDateTime.now())
    val user2 = User(User.Id.random, User.Name("UserTest"), LocalDateTime.now())
    repo.save(user1)
    repo.save(user2)

    val updatedUser2 = user2.copy(username = User.Name("User"))
    val result = service.updateUser(updatedUser2)

    assert(result.isLeft)
  }

  test("updateUser dovrebbe aggiornare l'utente se il nome non è già utilizzato") {
    val (service, repo) = createService
    val user1 = User(User.Id.random, User.Name("User"), LocalDateTime.now())
    val user2 = User(User.Id.random, User.Name("UserTest"), LocalDateTime.now())
    repo.save(user1)
    repo.save(user2)

    val updatedUser2 = user2.copy(username = User.Name("UpdatedUser"))
    val result = service.updateUser(updatedUser2)

    assert(result.isRight)
  }

  test("deleteUser dovrebbe eliminare l'utente") {
    val (service, repo) = createService
    val user = User(User.Id.random, User.Name("UserDeleted"), LocalDateTime.now())
    repo.save(user)
    
    val result = service.deleteUser(user)
    
    assert(result.isRight)
    assert(repo.users.isEmpty)
  }
}