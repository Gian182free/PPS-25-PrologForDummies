package prologfordummies.model

import org.scalatest.funsuite.AnyFunSuite
import prologfordummies.model.QuestionType
import prologfordummies.model.Question
import prologfordummies.services.prolog.PrologGrader

class QuestionOpenPrologTest extends AnyFunSuite {

  // Test per la valutazione di domande aperte con validazione Prolog

  test("Valutazione logica Prolog - caso positivo") {
    val staticTheory = "parent(abramo, isacco). parent(isacco, giacobbe)."
    val userCode = "nonno(X, Y) :- parent(X, Z), parent(Z, Y)."
    val goal = "nonno(abramo, giacobbe)."

    val q = Question(
      id = 1,
      question = "Definisci la regola nonno",
      correctAnswer = "",
      answers = List.empty,
      qType = QuestionType.OpenQuestion,
      validationQuery = Some(goal)
    )

    assert(q.isCorrect(userCode, staticTheory))
  }

  test("Valutazione logica Prolog - caso negativo") {
    val staticTheory = "parent(abramo, isacco). parent(isacco, giacobbe)."
    val userCode = "nonno(X, Y) :- parent(Y, Z), parent(Z, X)."
    val goal = "nonno(abramo, giacobbe)."

    val q = Question(
      id = 1,
      question = "Definisci la regola nonno",
      correctAnswer = "",
      answers = List.empty,
      qType = QuestionType.OpenQuestion,
      validationQuery = Some(goal)
    )

    assert(!q.isCorrect(userCode, staticTheory))
  }

  test("Valutazione logica Prolog - caso con errore sintassi") {
    val staticTheory = "parent(abramo, isacco). parent(isacco, giacobbe)."
    val userCode =
      "nonno(X, Y) :- parent(X, Z parent(Z, Y)." // Errore di sintassi
    val goal = "nonno(abramo, giacobbe)."

    val q = Question(
      id = 1,
      question = "Definisci la regola nonno",
      correctAnswer = "",
      answers = List.empty,
      qType = QuestionType.OpenQuestion,
      validationQuery = Some(goal)
    )

    assert(!q.isCorrect(userCode, staticTheory))
  }

  // Verifica sull'uso delle domande aperte tradizionali (senza motore Prolog)

  test(
    "Regression: Verifica domande aperte tradizionali (senza motore Prolog) - Caso positivo"
  ) {
    val teoria =
      "In Prolog un'affermazione sempre vera, come parent(abramo, isacco)..."
    val rispostaUtente = "fatto"

    val q = Question(
      id = 101,
      question = "Come si chiama un'affermazione sempre vera?",
      correctAnswer = "fatto",
      answers = List.empty,
      qType = QuestionType.OpenQuestion,
      validationQuery = None
    )

    assert(q.isCorrect(rispostaUtente, teoria))
  }

  test(
    "Regression: Verifica domande aperte tradizionali (senza motore Prolog) - Caso negativo"
  ) {
    val teoria =
      "In Prolog un'affermazione sempre vera, come parent(abramo, isacco)..."
    val rispostaUtente = "regola"

    val q = Question(
      id = 101,
      question = "Come si chiama un'affermazione sempre vera?",
      correctAnswer = "fatto",
      answers = List.empty,
      qType = QuestionType.OpenQuestion,
      validationQuery = None
    )

    assert(!q.isCorrect(rispostaUtente, teoria))
  }

}
