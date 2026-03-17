package prologfordummies.controller

import prologfordummies.model.Question
import prologfordummies.view.{LevelsPage, QuizPage, OpenQuizPage}
import scalafx.scene.control.Button
import scalafx.animation.PauseTransition
import scalafx.util.Duration
import prologfordummies.model.Level
import prologfordummies.view.UIComponents.showCustomConfirm
import prologfordummies.model.QuestionType
import prologfordummies.model.LevelSession
import scalafx.application.Platform
object QuizController {

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

  private def showEndedModal(): Unit = {

    val stats = LevelSession.currentStats.getOrElse((0, 0))
    val minutes = LevelSession.elapsedMinutes == 1 match
      case true => "1 Minuto"
      case false => LevelSession.elapsedMinutes.toString + " Minuti"

    val percentage = if stats._1 > 0 then (stats._2.toDouble / stats._1 * 100).formatted("%.0f") else "0"

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
      case None => showEndedModal()

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