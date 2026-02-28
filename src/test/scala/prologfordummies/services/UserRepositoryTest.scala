package prologfordummies.services

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfter
import prologfordummies.model.User
import java.nio.file.{Files, Paths}
import java.time.LocalDateTime

class UserRepositoryImplTest extends AnyFunSuite with BeforeAndAfter {

  // File apposito per i test per non sporcare i dati reali
  private val testPath = Paths.get(
    System.getProperty("user.home"),
    ".prologfordummies",
    "users_test.json"
  )
  val repo = UserRepositoryImpl(testPath)

  // Pulizia del file di test prima di ogni test
  before {
    if (Files.exists(testPath)) Files.delete(testPath)
  }

  test("Salvataggio e caricamento utente") {
    val user = User(User.Id.random, User.Name("TestUser"), LocalDateTime.now())
    repo.save(user)

    val loaded = repo.loadAll()
    assert(loaded.size == 1)
    assert(loaded.head.username.asString == "TestUser")
  }

  test("Aggiornamento utente esistente") {
    val user = User(User.Id.random, User.Name("User"), LocalDateTime.now())
    repo.save(user)

    val updated = user.copy(username = User.Name("UserUpdated"))
    repo.update(updated)

    assert(repo.findByName("User").isEmpty)
    assert(repo.findByName("UserUpdated").isDefined)
  }

  test("Ricerca per nome") {
    val user = User(User.Id.random, User.Name("User"), LocalDateTime.now())
    repo.save(user)

    assert(repo.findByName("user").isEmpty)
    assert(repo.findByName("User").isDefined)
  }

  test("Eliminazione utente") {
    val user = User(User.Id.random, User.Name("User"), LocalDateTime.now())
    repo.save(user)
    repo.delete(user)

    assert(repo.loadAll().isEmpty)
  }

}
