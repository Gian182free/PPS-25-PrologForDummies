package prologfordummies.model

import java.util.UUID
import java.time.LocalDateTime

/**
 * Modello della classe User, utilizzatrice dell'applicazione.
 */
case class User(
  id: User.Id,
  username: User.Name,
  registrationDate: LocalDateTime
)

object User:
  opaque type Id = UUID
  object Id:
    def apply(uuid: UUID): Id = uuid
    def random: Id = UUID.randomUUID()

  opaque type Name = String
  object Name:
    def apply(value: String): Name = value
    extension (n: Name)
      def asString: String = n