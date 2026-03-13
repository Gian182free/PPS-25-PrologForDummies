package prologfordummies.model

import upickle.default.*

enum QuestionType:
  case MultipleChoice, OpenQuestion

object QuestionType:
  private def fromStringOrDefault(value: String): QuestionType =
    values.find(_.toString == value).getOrElse(MultipleChoice)

  given ReadWriter[QuestionType] =
    readwriter[String].bimap(_.toString, fromStringOrDefault)