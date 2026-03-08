package prologfordummies.controller

import prologfordummies.view.LevelsPage

object QuizController {

  def submitAnswer(optionIndex: Int): Unit = {
    println(s"Risposta selezionata: Opzione $optionIndex")
  }

  def backToLevels(): Unit = {
    prologfordummies.Main.setPage(LevelsPage.asParent)
  }

}