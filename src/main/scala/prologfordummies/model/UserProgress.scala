package prologfordummies.model

import java.time.LocalDateTime

case class LevelRecord(
    levelId: Level.Id,
    completedAt: LocalDateTime,
    quizAttempts: Int,
    quizCorrects: Int,
    timeSpentMinutes: Long
)

case class UserProgress(
    userId: User.Id,
    history: List[LevelRecord]
) {

  //* *** Metodi di utility *** */ 

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
