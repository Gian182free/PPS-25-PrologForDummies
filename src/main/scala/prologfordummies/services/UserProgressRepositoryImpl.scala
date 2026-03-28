package prologfordummies.services

import prologfordummies.model.{User, UserProgress, LevelRecord, Level}
import java.nio.file.{Files, Paths, Path}
import upickle.default.*
import prologfordummies.model.UserProgress.given
import prologfordummies.model.LevelRecord.given
import prologfordummies.model.codec.UserProgressCodec.given

/** Implementazione concreta di UserProgress che utilizza un file JSON per la
*   persistenza dei dati dei livelli.
*/
class UserProgressRepositoryImpl(storagePath: Path)
    extends UserProgressRepository {

  private def ensureDirectoryExists(): Unit =
    if (!Files.exists(storagePath.getParent))
      Files.createDirectories(storagePath.getParent)

  private def loadAll(): List[UserProgress] =
    Files.exists(storagePath) match
      case false =>
        List.empty
      case true =>
        Files.readString(storagePath) match
          case json if json.isBlank =>
            List.empty
          case content =>
            try read[List[UserProgress]](content)
            catch
              case e: Exception =>
                println(s"DEBUG: Errore parsing JSON: ${e.getMessage}")
                List.empty

  override def findByUserId(userId: User.Id): Option[UserProgress] =
    loadAll().find(_.userId.toString == userId.toString)

  override def findByUserIdAndLevelId(
      userId: User.Id,
      levelId: Level.Id
  ): Option[LevelRecord] =
    findByUserId(userId).flatMap(_.history.find(_.levelId == levelId))

  override def saveProgress(progress: UserProgress): Unit = {
    val allUsersProgress = loadAll()

    val updatedAllProgress =
      if (allUsersProgress.exists(_.userId == progress.userId)) {
        allUsersProgress.map {
          case p if p.userId == progress.userId => progress
          case p                                => p
        }
      } else {

        allUsersProgress :+ progress
      }

    ensureDirectoryExists()
    Files.writeString(storagePath, write(updatedAllProgress, indent = 4))
  }
}

object UserProgressRepositoryImpl {
  private val userHome = System.getProperty("user.home")
  private val file = "users_progress.json"
  private val defaultPath = Paths.get(userHome, ".prologfordummies", file)

  given fileRepository: UserProgressRepository = new UserProgressRepositoryImpl(
    defaultPath
  )
}
