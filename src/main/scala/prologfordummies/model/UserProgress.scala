package prologfordummies.model

import java.time.LocalDateTime

/** Rappresenta lo storico del completamento di un livello e i relativi KPI.
  *
  * @param levelId
  *   Identificativo univoco del livello
  * @param completedAt
  *   Data e ora del completamento
  * @param quizAttempts
  *   Numero totale di quiz svolti
  * @param quizCorrects
  *   Numero di quiz risolti correttamente
  * @param timeSpentMinutes
  *   Tempo totale (in minuti) dedicato al livello
  */
case class LevelRecord(
    levelId: Level.Id,
    completedAt: LocalDateTime,
    quizAttempts: Int,
    quizCorrects: Int,
    timeSpentMinutes: Long
)

/** Aggregatore dei progressi di un utente, fornisce KPI globali basati sullo storico dei livelli.
  *
  * @param userId  L'identificativo univoco dell'utente a cui appartengono i progressi
  * @param history Lista di tutti [[LevelRecord]] completati
  */
case class UserProgress(
    userId: User.Id,
    history: List[LevelRecord]
) {

  // * *** Metodi di utility *** */

  // Somma totale dei tentativi
  def totalAttempts: Int = history.map(_.quizAttempts).sum

  // Somma totale delle risposte corrette
  def totalCorrects: Int = history.map(_.quizCorrects).sum

  // Percentuale globale di successo
  def globalAccuracy: Double =
    if (totalAttempts > 0) (totalCorrects.toDouble / totalAttempts) * 100
    else 0.0

  // Ore totali di utilizzo (approssimate)
  def totalHours: Double = history.map(_.timeSpentMinutes).sum / 60.0

  // Conteggio livelli completati
  def uniqueLevelsCompleted: Int = history.map(_.levelId).distinct.size

}
