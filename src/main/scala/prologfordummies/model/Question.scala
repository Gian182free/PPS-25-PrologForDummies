package prologfordummies.model

import prologfordummies.model.QuestionType
import prologfordummies.model.prolog.PrologGrader

case class Question(
  id: Int,
  question: String,
  correctAnswer: String,
  answers: List[String],
  qType: QuestionType,
  validationQuery: Option[String] = None
):

  def isCorrect(answer: String, theory: String = ""): Boolean =
    qType match
      case QuestionType.MultipleChoice =>
        answer == correctAnswer
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
