package prologfordummies.services

import java.nio.file.{Files, Paths, Path}
import java.util.UUID
import upickle.default.*
import prologfordummies.model.codec.LevelCodec.given
import prologfordummies.model.Level

/** Implementazione concreta di LevelRepository che utilizza un file JSON per la
*   persistenza dei dati dei livelli.
*/
class LevelRepositoryImpl(storagePath: Path) extends LevelRepository:
  private val dataFolder = storagePath.getParent

  private def ensureDirectoryExists(): Unit =
    if (!Files.exists(storagePath)) Files.createDirectories(storagePath.getParent)

  override def loadAll(): List[Level] =
    Files.exists(storagePath) match
      case false => List.empty
      case true  =>
        Files.readString(storagePath) match
          case file if file.isBlank => List.empty
          case content              =>
            try read[List[Level]](content)
            catch case _ => List.empty

  override def findById(id: Level.Id): Option[Level] =
    loadAll().find(_.id.equals(id))

  private def writeToPath(levels: List[Level]): Unit =
    ensureDirectoryExists()
    Files.writeString(storagePath, write(levels, indent = 4))

object LevelRepositoryImpl {
  private val userHome = System.getProperty("user.home")
  private val file = "levels.json"
  private val defaultPath =
    Paths.get("src", "main", "resources", file)

  given fileRepository: LevelRepository = new LevelRepositoryImpl(defaultPath)
}
