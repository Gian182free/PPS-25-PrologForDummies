package prologfordummies.model

import org.scalatest.funsuite.AnyFunSuite

class QuizTest extends AnyFunSuite{
  test("Quiz deve salvare i valori correttamente") {
    val quiz = Quiz(
        id = 1,
        question = "Quanto fa 2 + 2?",
        correctAnswer = "4",
        answers = List("2", "3", "4")
    )

    assert(quiz.id == 1)
    assert(quiz.question == "Quanto fa 2 + 2?")
    assert(quiz.correctAnswer == "4")
    assert(quiz.answers == List("2", "3", "4"))
  }

  test("isCorrect dovrebbe restituire vero per la risposta corretta") {
    val quiz = Quiz(1, "Quanto fa 2 + 2?", "4", List("2", "3", "4"))
    assert(quiz.isCorrect("4"))
  }
  test("isCorrect dovrebbe restituire falso per la risposta sbagliata") {
    val quiz = Quiz(1, "Qual è la capitale della Francia?", "Parigi", List("Roma", "Berlino", "Parigi"))
    assert(!quiz.isCorrect("Rome"))
  }
  
  test("La lista delle risposte deve contenere la risposta corretta") {
    val quiz = Quiz(1, "Qual è la capitale d'Italia?", "Roma", List("Roma", "Milano", "Napoli"))
    assert(quiz.answers.contains(quiz.correctAnswer))
  }

  
}
