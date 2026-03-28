package prologfordummies.controller

import prologfordummies.model.Question
import prologfordummies.view.{LevelsPage, QuizPage, OpenQuizPage}
import scalafx.animation.PauseTransition
import scalafx.util.Duration
import prologfordummies.model.Level
import prologfordummies.view.UIComponents.showCustomConfirm
import prologfordummies.model.QuestionType
import prologfordummies.model.LevelSession
import scalafx.application.Platform
import prologfordummies.model.UserSession
import prologfordummies.model.LevelRecord
import prologfordummies.services.UserProgressRepositoryImpl
import prologfordummies.model.UserProgress

/** Gestisce l'interazione Utente-Quiz: risposta, avanzamento e salvataggio dei progressi. */
object QuizController {

  /** Valida la risposta e passa automaticamente alla domanda successiva. 
   * @param q domanda corrente,
   * @param ans risposta fornita dall'utente,
   * @param level livello corrente,
   * @param index indice della domanda corrente nel livello.
   * @return Boolean 
   */
  def submitAnswer(q: Question, ans: String, level: Level, index: Int): Boolean = {
    val isCorrect = q.isCorrect(ans)

    // Registro il tentativo nella sessione di gioco
    LevelSession.addAttempt(isCorrect)


    val pause = new PauseTransition {
      duration = Duration(800)
      onFinished = _ => goToNext(level, index)
    }
    pause.play()

    isCorrect
  }

  private def backToLevels(): Unit = {
    prologfordummies.Main.setPage(LevelsPage.asParent)
  }

  def confirmBackToLevels(): Unit = {
    showCustomConfirm(
      head = "Conferma uscita",
      message = "Sei sicuro di voler uscire dal quiz e tornare ai livelli?",
      confirmButtonMsg = "Esci",
      declinedButtonMsg = "Annulla",
      onConfirm = () => backToLevels()
    )
  }

  private def nextQuestion(level: Level, currentIndex: Int): Option[Int] =
    val nextIndex = currentIndex + 1
    level.questions.lift(nextIndex).map(_ => nextIndex)

  /** Salva i risultati finali del livello e prepara il riepilogo. 
   * @param level livello corrente.
   * @return (stats, minutesStr, percentage)
   */
  private def saveProgress(level: Level): ((Int, Int), String, String) = {
    val stats = LevelSession.currentStats.getOrElse((0, 0))
    val elapsed = LevelSession.elapsedMinutes

    val minutesStr = if elapsed == 1 then "1 Minuto" else s"$elapsed Minuti"
    val percentage = if stats._1 > 0 then (stats._2.toDouble / stats._1 * 100).formatted("%.0f") else "0"

    UserSession.currentSessionUser.foreach { user =>
    val repository = UserProgressRepositoryImpl.fileRepository

    // Recupero il progress se esiste
    val currentProgress = repository.findByUserId(user.id)
      .getOrElse(UserProgress(user.id, List.empty))

    val newRecord = LevelRecord(
      levelId = level.id,
      completedAt = java.time.LocalDateTime.now(),
      quizAttempts = stats._1,
      quizCorrects = stats._2,
      timeSpentMinutes = elapsed
    )

    val updatedProgress = currentProgress.copy(
      history = currentProgress.history :+ newRecord
    )

    repository.saveProgress(updatedProgress)
  }
    (stats, minutesStr, percentage)
  }

  private def showEndedModal(level: Level): Unit = {

    val (stats, minutes, percentage) = saveProgress(level)

    val finalData = LevelSession.endLevel()

    Platform.runLater(() => {
      showCustomConfirm(
        head = "Quiz completato!",
        message = s"Ottimo lavoro! Ecco i tuoi risultati:\n\n" +
                  s"• Tempo: $minutes \n" +
                  s"• Risposte: ${stats._2} su ${stats._1}\n" +
                  s"• Percentuale di risposte corrette: ${percentage}% ",
        confirmButtonMsg = "Ok ma rosso",
        declinedButtonMsg = "Ok",
        onConfirm = () => backToLevels()
      )
    })
  }

  private def goToNext(level: Level, currentIndex: Int): Unit =
    nextQuestion(level, currentIndex) match
      case Some(i) => loadQuestionPage(level, i)
      case None => showEndedModal(level)

  /** Carica la vista corretta per il tipo di domanda. */
  def loadQuestionPage(level: Level, index: Int): Unit =
    val q = level.questions(index)

    // Se è la prima domanda del livello, avviamo la sessione di gioco
    if (index == 0) {
      LevelSession.startLevel(level.id)
    }

    q.qType match
      case QuestionType.MultipleChoice =>
        prologfordummies.Main.setPage(QuizPage.asParent(level, index))
      case QuestionType.OpenQuestion =>
        prologfordummies.Main.setPage(OpenQuizPage.asParent(level, index))

}