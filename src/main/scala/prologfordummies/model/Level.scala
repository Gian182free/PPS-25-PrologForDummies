package prologfordummies.model

import java.util.UUID
import java.time.LocalDateTime

/**
 * Modello della classe Level, che rappresenta un livello di gioco con teoria e domande.
 */
case class Level(
  id: Level.Id,
  title: Level.Title,
  theory: Level.Theory,
  questions: List[Question],
  createdAt: LocalDateTime = LocalDateTime.now()
)

object Level:
  opaque type Id = UUID
  object Id:
    def apply(uuid: UUID): Id = uuid
    def random: Id = UUID.randomUUID()

  opaque type Title = String
  object Title:
    def apply(value: String): Title = value
    extension (n: Title)
      def asString: String = n

  opaque type Theory = String
  object Theory:
    def apply(value: String): Theory = value
    extension (n: Theory)
      def asString: String = n