package prologfordummies.model

import upickle.default.*
import java.time.LocalDateTime
import java.util.UUID

/** Questo oggetto definisce le istanze implicite di ReadWriter utilizzate da
  * uPickle per convertire Level da/e verso JSON.
  */
object LevelCodec:

  implicit val idRW: ReadWriter[Level.Id] =
    readwriter[String].bimap(
      _.toString,
      s => Level.Id(UUID.fromString(s))
    )

  implicit val titleRW: ReadWriter[Level.Title] =
    readwriter[String].bimap(
      _.asString,
      Level.Title(_)
    )

  implicit val theoryRW: ReadWriter[Level.Theory] =
    readwriter[String].bimap(
      _.asString,
      Level.Theory(_)
    )
    
  implicit val questionRW: ReadWriter[Question] = macroRW

  implicit val createdAtRW: ReadWriter[LocalDateTime] =
    readwriter[String].bimap(_.toString, LocalDateTime.parse)

  implicit val levelRW: ReadWriter[Level] = macroRW
