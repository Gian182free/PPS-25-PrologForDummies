package prologfordummies.controller

import prologfordummies.model.{Level, UserSession}
import prologfordummies.services.{LevelRepositoryImpl, UserProgressRepositoryImpl}

import java.util.UUID
import prologfordummies.Main
object LevelsController {

  private given repo: prologfordummies.services.LevelRepository =
    LevelRepositoryImpl.fileRepository
    
  private given progressRepo: prologfordummies.services.UserProgressRepository =
    UserProgressRepositoryImpl.fileRepository

  def loadLevel(level: Level): Unit = {
    QuizController.loadQuestionPage(level, 0)
  }

  def handleLevelSelected(
      idLevel: UUID,
      onSuccess: Level => Unit,
      onError: String => Unit
  ): Unit = {
    repo.loadAll().find(_.id == idLevel) match {
      case Some(level) => onSuccess(level)
      case None        => onError(s"Livello con ID $idLevel non trovato")
    }
  }

  def handleBackToMenu(): Unit = {
    Main.setPage(prologfordummies.view.MenuPage.asParent)
  }

  def loadLevels(): List[Level] = {
    repo.loadAll()
  }
  
  def isLevelCompleted(level: Level): Boolean =
    UserSession.currentSessionUser.flatMap { user =>
      progressRepo.findByUserId(user.id)
    }.exists(_.history.exists(_.levelId == level.id))

  def countCorrectAnswers(level: Level): Int =
    UserSession.currentSessionUser.flatMap { user =>
      progressRepo.findByUserId(user.id)
    }.map { _.history
      .filter(_.levelId == level.id)
      .map(_.quizCorrects)
      .maxOption.getOrElse(0)
    }.getOrElse(0)
}
