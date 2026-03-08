package prologfordummies.controller

import prologfordummies.model.Level
import prologfordummies.services.{LevelRepositoryImpl}
import java.util.UUID
import prologfordummies.Main

object LevelsController {

  private given repo: prologfordummies.services.LevelRepository = LevelRepositoryImpl.fileRepository

  def handleLevelSelected(idLevel: UUID, onSuccess: Level => Unit, onError: String => Unit): Unit = {
    repo.loadAll().find(_.id == idLevel) match {
      case Some(level) => onSuccess(level)
      case None => onError(s"Livello con ID $idLevel non trovato")
    }
    // TODO: implementare la sessione livello
  }

  def handleBackToMenu(): Unit = {
    Main.setPage(prologfordummies.view.MenuPage.asParent)
  }

  def loadLevels(): List[Level] = {
    repo.loadAll()
  }
}