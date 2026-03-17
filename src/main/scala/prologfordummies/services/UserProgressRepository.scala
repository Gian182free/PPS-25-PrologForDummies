package prologfordummies.services

import prologfordummies.model.UserProgress
import prologfordummies.model.LevelRecord
import prologfordummies.model.Level
import prologfordummies.model.User

/** Interfaccia di UserProgress Repository per la gestione dei dati dei
  * progressi degli utenti
  */
trait UserProgressRepository:
  def findByUserId(userId: User.Id): Option[UserProgress]
  def saveProgress(progress: UserProgress): Unit
  def findByUserIdAndLevelId(
      userId: User.Id,
      levelId: Level.Id
  ): Option[LevelRecord]
