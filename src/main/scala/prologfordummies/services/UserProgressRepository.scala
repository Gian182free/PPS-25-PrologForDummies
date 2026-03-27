package prologfordummies.services

import prologfordummies.model.UserProgress
import prologfordummies.model.LevelRecord
import prologfordummies.model.Level
import prologfordummies.model.User

/** Interfaccia di UserProgress Repository per la gestione dei dati dei
  * progressi degli utenti
  */
trait UserProgressRepository:

  /** Recupera i progressi per un determinato utente
    * @param userId
    *   L'id dell'utente
    * @return
    *   Un [[Option]] contenente [[UserProgress]] se l'utente ha una cronologia,
    *   [[None]] altrimenti
    */
  def findByUserId(userId: User.Id): Option[UserProgress]

  /** Salva o aggiorna lo stato dei progressi di un utente
    * @param progress
    *   L'oggetto [[UserProgress]] aggiornato da persistere
    */
  def saveProgress(progress: UserProgress): Unit

  /** Ricerca il record specifico di un singolo livello per un determinato
    * utente
    * @param userId
    *   L'identificativo dell'utente
    * @param levelId
    *   L'identificativo del livello cercato
    * @return
    *   Un [[Option]] contenente il [[LevelRecord]] se il livello è stato
    *   affrontato, [[None]] altrimenti
    */
  def findByUserIdAndLevelId(
      userId: User.Id,
      levelId: Level.Id
  ): Option[LevelRecord]
