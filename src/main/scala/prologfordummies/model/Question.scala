package prologfordummies.model

import prologfordummies.model.QuestionType
import prologfordummies.services.prolog.PrologGrader

/** Rappresenta una domanda del quiz.
 */
case class Question(
  id: Int,
  question: String,
  correctAnswer: String,
  answers: List[String],
  qType: QuestionType,
  validationQuery: Option[String] = None
):

  /** Verifica se la risposta fornita è corretta
   *  passandola al motore Prolog.
   */
  def isCorrect(answer: String, theory: String = ""): Boolean =
    qType match
      case QuestionType.MultipleChoice =>
        PrologGrader.validateMultipleChoice(theory, answer, correctAnswer)
      case QuestionType.OpenQuestion =>
        validationQuery match
          case Some(query) => PrologGrader.validate(theory, answer, query)
          case None        => answer.trim == correctAnswer.trim

object Question:
  def create(
      id: Int,
      question: String,
      correctAnswer: String,
      answers: List[String],
      qType: QuestionType = QuestionType.MultipleChoice,
      validationQuery: Option[String] = None
  ): Either[String, Question] =
    (question, answers, correctAnswer, qType) match
      case (q, _, _, _) if q.isEmpty => Left("Question non può essere vuota.")
      case _                         =>
        Right(
          Question(id, question, correctAnswer, answers, qType, validationQuery)
        )
