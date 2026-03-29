package prologfordummies.services

import prologfordummies.model.Level
import java.nio.file.{Files, Path, Paths}
import scala.io.Source
import upickle.default.*
import prologfordummies.model.codec.LevelCodec.given

/** Implementazione concreta di LevelRepository che utilizza un file JSON per la
*   persistenza dei dati dei livelli.
*/
class LevelRepositoryImpl(storagePath: Path) extends LevelRepository:
  private val dataFolder = storagePath.getParent

  private def ensureDirectoryExists(): Unit =
    if (!Files.exists(storagePath)) Files.createDirectories(storagePath.getParent)

  override def loadAll(): List[Level] =
    Files.exists(storagePath) match
      case false => loadFromResource().getOrElse(List.empty)
      case true  =>
        Files.readString(storagePath) match
          case file if file.isBlank => List.empty
          case content              =>
            try read[List[Level]](content)
            catch case _ => List.empty

  /** Prova a leggere levels.json dalle risorse dell'applicazione.
   *
   * @return
   * - Some(List.empty) se il file esiste ma è vuoto
   * - Some(levels) se il JSON viene letto e parsato correttamente
   * - None se la risorsa non è disponibile o si verifica un errore in lettura
   */
  private def loadFromResource(): Option[List[Level]] =

  try
    Option(getClass.getResourceAsStream("/levels.json")).flatMap { stream =>
      try
        val content = Source.fromInputStream(stream).mkString
        if content.isBlank then Some(List.empty)
        else
          try Some(read[List[Level]](content))
          catch case _ => Some(List.empty)
      finally stream.close()
    }
  catch
    case _ => None

  override def findById(id: Level.Id): Option[Level] =
    loadAll().find(_.id.equals(id))

  private def writeToPath(levels: List[Level]): Unit =
    ensureDirectoryExists()
    Files.writeString(storagePath, write(levels, indent = 4))

object LevelRepositoryImpl:
  private val userHome = System.getProperty("user.home")
  private val file = "levels.json"
  private val defaultPath = 
    Paths.get(userHome, ".prologfordummies", file)

  given fileRepository: LevelRepository = new LevelRepositoryImpl(defaultPath)
