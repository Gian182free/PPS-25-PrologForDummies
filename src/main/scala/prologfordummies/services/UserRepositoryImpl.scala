package prologfordummies.services

import prologfordummies.model.User
import java.nio.file.{Files, Paths, Path}
import java.util.UUID
import upickle.default.*
import prologfordummies.model.UserCodec.given

/** Implementazione concreta di UserRepository che utilizza un file JSON per la
  * persistenza dei dati utente.
  */
class UserRepositoryImpl(storagePath: Path) extends UserRepository:
  private val dataFolder = storagePath.getParent

  private def ensureDirectoryExists(): Unit =
    if (!Files.exists(storagePath)) Files.createDirectories(storagePath.getParent)

  override def save(user: User): Unit =
    ensureDirectoryExists()
    val allUsers = loadAll() :+ user
    writeToPath(allUsers)

  override def loadAll(): List[User] =
    Files.exists(storagePath) match
      case false => List.empty
      case true  =>
        Files.readString(storagePath) match
          case file if file.isBlank => List.empty
          case content              =>
            try read[List[User]](content)
            catch case _ => List.empty

  override def findByName(name: String): Option[User] =
    loadAll().find(_.username.asString.equals(name.trim))

  override def update(updatedUser: User): Unit =
    val newUsers = loadAll().map {
      case user if user.id == updatedUser.id => updatedUser
      case user                              => user
    }
    writeToPath(newUsers)

  override def delete(deletedUser: User): Unit =
    val newUsers = loadAll().filter { case user =>
      user.id != deletedUser.id
    }
    writeToPath(newUsers)

  private def writeToPath(users: List[User]): Unit =
    ensureDirectoryExists()
    Files.writeString(storagePath, write(users, indent = 4))

object UserRepositoryImpl {
  private val userHome = System.getProperty("user.home")
  private val file = "users.json"
  private val defaultPath =
    Paths.get(userHome, ".prologfordummies", file)

  given fileRepository: UserRepository = new UserRepositoryImpl(defaultPath)
}
