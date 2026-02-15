package prologfordummies.model

import upickle.default.*
import java.time.LocalDateTime
import java.util.UUID

/**
 * Questo oggetto definisce le istanze implicite di ReadWriter utilizzate da uPickle per convertire User da/e verso JSON.
 */
object UserCodec:

  implicit val dateTimeRW: ReadWriter[LocalDateTime] =
    readwriter[String].bimap(_.toString, LocalDateTime.parse)

  implicit val idRW: ReadWriter[User.Id] =
    readwriter[String].bimap(
      _.toString,
      s => User.Id(UUID.fromString(s))
    )

  implicit val nameRW: ReadWriter[User.Name] =
    readwriter[String].bimap(
      _.asString,
      User.Name(_)
    )

  implicit val userRW: ReadWriter[User] = macroRW