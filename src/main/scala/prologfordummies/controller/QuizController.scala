package prologfordummies.controller

import prologfordummies.model.Question
import prologfordummies.view.{LevelsPage, QuizPage}
import scalafx.scene.control.Button
import scalafx.animation.PauseTransition
import scalafx.util.Duration
import prologfordummies.model.Level

object QuizController {

  def submitAnswer(button: Button, q: Question, ans: String, level: Level, index: Int): Unit = {
    val isCorrect = q.isCorrect(ans)

    button.style =
      s"""
         -fx-background-color: ${if isCorrect then "#4CAF51" else "#F44337"};
         -fx-text-fill: white;
         -fx-font-weight: bold;
         -fx-cursor: hand;
      """

    val pause = new PauseTransition {
      duration = Duration(800)
      onFinished = _ => goToNext(level, index)
    }
    pause.play()
  }

  def backToLevels(): Unit = {
    prologfordummies.Main.setPage(LevelsPage.asParent)
  }

  def nextQuestion(level: Level, currentIndex: Int): Option[Int] =
    level.questions.lift(currentIndex + 1).map(_ => currentIndex + 1)

  def goToNext(level: Level, currentIndex: Int): Unit =
    nextQuestion(level, currentIndex) match
      case Some(i) => prologfordummies.Main.setPage(QuizPage.asParent(level, i))
      case None => backToLevels()

}