package prologfordummies.controller

import prologfordummies.model.Question
import prologfordummies.view.{LevelsPage, QuizPage, OpenQuizPage}
import scalafx.animation.PauseTransition
import scalafx.util.Duration
import prologfordummies.model.Level
import prologfordummies.view.UIComponents.showCustomConfirm
import prologfordummies.model.QuestionType

object QuizController {

  def submitAnswer(q: Question, ans: String, level: Level, index: Int): Boolean = {
    val isCorrect = q.isCorrect(ans)

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

  private def goToNext(level: Level, currentIndex: Int): Unit =
    nextQuestion(level, currentIndex) match
      case Some(i) => loadQuestionPage(level, i)
      case None => backToLevels()

  def loadQuestionPage(level: Level, index: Int): Unit =
    val q = level.questions(index)

    q.qType match
      case QuestionType.MultipleChoice =>
        prologfordummies.Main.setPage(QuizPage.asParent(level, index))
      case QuestionType.OpenQuestion =>
        prologfordummies.Main.setPage(OpenQuizPage.asParent(level, index))

}