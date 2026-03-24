package prologfordummies.controller

import prologfordummies.model.UserSession
import prologfordummies.services.UserProgressRepositoryImpl

object StatsController {

  private given progressRepo: prologfordummies.services.UserProgressRepository =
    UserProgressRepositoryImpl.fileRepository

  private def currentUserProgress =
    UserSession.currentSessionUser.flatMap { user =>
      progressRepo.findByUserId(user.id)
    }

  def completedLevelsCount: Int =
    currentUserProgress.map(_.uniqueLevelsCompleted).getOrElse(0)

  def totalLevelsCount: Int =
    LevelsController.loadLevels().size
  
  def totalAttempts: Int =
    currentUserProgress.map(_.totalAttempts).getOrElse(0)

  def totalCorrectsPercentage: Double =
    currentUserProgress.map(_.globalAccuracy).getOrElse(0.0)

  def totalHoursUsed: Double =
    currentUserProgress.map(_.totalHours).getOrElse(0.0)
}
