package prologfordummies.controller

import prologfordummies.model.Question
import prologfordummies.view.LevelsPage
import scalafx.scene.control.Button

object QuizController {

  def submitAnswer(button: Button, q: Question, ans: String): Unit = {
    val isCorrect = q.isCorrect(ans)

    button.style =
      s"""
         -fx-background-color: ${if isCorrect then "#4CAF51" else "#F44337"};
         -fx-text-fill: white;
         -fx-font-weight: bold;
         -fx-cursor: hand;
      """
  }

  def backToLevels(): Unit = {
    prologfordummies.Main.setPage(LevelsPage.asParent)
  }

}