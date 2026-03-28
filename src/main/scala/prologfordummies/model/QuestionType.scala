package prologfordummies.model

import upickle.default.*

/** Tipo di domanda supportato dal quiz.
 *
 * - MultipleChoice: risposta a scelta multipla
 * - OpenQuestion: risposta aperta
 */
enum QuestionType:
  case MultipleChoice, OpenQuestion

object QuestionType:

  private def fromStringOrDefault(value: String): QuestionType =
    values.find(_.toString == value).getOrElse(MultipleChoice)

  /** Serializzazione/deserializzazione JSON */
  given ReadWriter[QuestionType] =
    readwriter[String].bimap(_.toString, fromStringOrDefault)