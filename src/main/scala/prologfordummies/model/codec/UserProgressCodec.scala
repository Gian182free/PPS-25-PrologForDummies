package prologfordummies.model.codec

import upickle.default.*
import java.time.LocalDateTime
import prologfordummies.model.{UserProgress, LevelRecord, Level, User}
import java.util.UUID

/** Questo oggetto definisce le istanze implicite di ReadWriter utilizzate da
  * uPickle per convertire UserProgress e LevelRecord da/e verso JSON.
  */
object UserProgressCodec:

  given dateTimeRW: ReadWriter[LocalDateTime] =
    readwriter[String].bimap(
      _.toString,
      LocalDateTime.parse(_)
    )

  given userIdRW: ReadWriter[User.Id] =
    readwriter[String].bimap(
      _.toString, 
      s => User.Id(UUID.fromString(s))
    )

  given levelIdRW: ReadWriter[Level.Id] =
    readwriter[String].bimap(
      _.toString, 
      s => Level.Id(UUID.fromString(s))
    )

  given recordRW: ReadWriter[LevelRecord] = macroRW

  given progressRW: ReadWriter[UserProgress] = macroRW