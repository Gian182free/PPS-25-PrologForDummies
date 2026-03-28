package prologfordummies.model

import java.time.LocalDateTime
import java.time.Duration

/** Stati possibili della sessione di livello */
sealed trait LevelSessionState

case object Idle extends LevelSessionState

/** Rappresenta lo stato di una sessione di livello attualmente in corso.
  * @param levelId
  *   Identificativo del livello che l'utente sta svolgendo
  * @param startTime
  *   Timestamp di inizio livello
  * @param quizAttempts
  *   Conteggio totale dei quiz effettuati
  * @param quizCorrects
  *   Conteggio delle risposte fornite correttamente
  */
case class ActiveLevel(
    levelId: Level.Id,
    startTime: LocalDateTime,
    quizAttempts: Int,
    quizCorrects: Int
) extends LevelSessionState

object LevelSession:

  private var state: LevelSessionState = Idle

  // Inizio livello
  def startLevel(levelId: Level.Id): Unit =
    state = ActiveLevel(levelId, LocalDateTime.now(), 0, 0)

  // Registro una risposta
  def addAttempt(isCorrect: Boolean): Unit =
    state match
      case currentState: ActiveLevel =>
        state = currentState.copy(
          quizAttempts = currentState.quizAttempts + 1,
          quizCorrects =
            currentState.quizCorrects + (if isCorrect then 1 else 0)
        )
      case Idle => ()

  // Calcolo il tempo trascorso
  def elapsedMinutes: Long =
    state match
      case currentState: ActiveLevel =>
        Duration
          .between(currentState.startTime, LocalDateTime.now())
          .toMinutes
          .max(1)
      case Idle => 0

  /** Restituisce le statistiche correnti della sessione di gioco.
    *
    * @return
    *   un [[scala.Option]] contenente una pair (Int, Int) dove:
    *   - il primo valore è il numero di risposte date
    *     ([[ActiveLevel.quizAttempts]])
    *   - il secondo valore è il numero di risposte corrette
    *     ([[ActiveLevel.quizCorrects]]) Restituisce [[scala.None]] se non vi è
    *     alcuna sessione attiva (stato [[Idle]]).
    */
  def currentStats: Option[(Int, Int)] =
    state match
      case currentState: ActiveLevel =>
        Some((currentState.quizAttempts, currentState.quizCorrects))
      case Idle => None

  // Alla conclusione del livello restituisco i dati finali
  def endLevel(): Option[ActiveLevel] =
    val finalState = state match
      case currentState: ActiveLevel => Some(currentState)
      case Idle                      => None
    state = Idle
    finalState
