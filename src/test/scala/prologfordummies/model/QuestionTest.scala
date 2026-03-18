package prologfordummies.model

import org.scalatest.funsuite.AnyFunSuite

class QuestionTest extends AnyFunSuite {

  test("Question deve salvare i valori correttamente") {
    val question = Question(
      id = 1,
      question = "Quanto fa 2 + 2?",
      correctAnswer = "4",
      answers = List("2", "3", "4"),
      qType = QuestionType.MultipleChoice
    )

    assert(question.id == 1)
    assert(question.question == "Quanto fa 2 + 2?")
    assert(question.correctAnswer == "4")
    assert(question.answers == List("2", "3", "4"))
  }

  test("isCorrect dovrebbe restituire vero per la risposta corretta (MultipleChoice)") {
    val question = Question(
      id = 1,
      question = "Quanto fa 2 + 2?",
      correctAnswer = "4",
      answers = List("2", "3", "4"),
      qType = QuestionType.MultipleChoice
    )

    assert(question.isCorrect("4"))
  }

  test("isCorrect dovrebbe restituire falso per la risposta sbagliata (MultipleChoice)") {
    val question = Question(
      id = 1,
      question = "Qual è la capitale della Francia?",
      correctAnswer = "Parigi",
      answers = List("Roma", "Berlino", "Parigi"),
      qType = QuestionType.MultipleChoice
    )

    assert(!question.isCorrect("Roma"))
  }

  test("La lista delle risposte deve contenere la risposta corretta (MultipleChoice)") {
    val question = Question(
      id = 1,
      question = "Qual è la capitale d'Italia?",
      correctAnswer = "Roma",
      answers = List("Roma", "Milano", "Napoli"),
      qType = QuestionType.MultipleChoice
    )

    assert(question.answers.contains(question.correctAnswer))
  }

  test("OpenQuestion: isCorrect deve restituire falso con risposta sbagliata") {
    val question = Question(
      id = 11,
      question = "Scrivi un fatto Prolog che indica Roma come capitale d’Italia.",
      correctAnswer = "capitale(roma, italia).",
      answers = Nil,
      qType = QuestionType.OpenQuestion
    )

    assert(!question.isCorrect("capitale(roma, francia)."))
  }

  test("OpenQuestion: isCorrect deve restituire vero con risposta corretta") {
    val question = Question(
      id = 11,
      question = "Scrivi un fatto Prolog che indica Roma come capitale d’Italia.",
      correctAnswer = "capitale(roma, italia).",
      answers = Nil,
      qType = QuestionType.OpenQuestion
    )

    assert(question.isCorrect("capitale(roma, italia)."))
  }

  test("OpenQuestion: answers deve essere vuoto") {
    val question = Question(
      id = 12,
      question = "Che fatto Prolog rappresenta che mario è padre di luigi?",
      correctAnswer = "padre(mario, luigi).",
      answers = Nil,
      qType = QuestionType.OpenQuestion
    )

    assert(question.answers.isEmpty)
  }

}