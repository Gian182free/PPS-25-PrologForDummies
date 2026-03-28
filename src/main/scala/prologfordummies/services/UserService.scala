package prologfordummies.services

import prologfordummies.model.User
import java.time.LocalDateTime
import java.nio.file.{Files, Paths}
import prologfordummies.model.codec.UserCodec.given
import upickle.default.*
import java.time.LocalDateTime
import java.util.UUID

/** Interfaccia che definisce le operazioni di alto livello per la gestione
  * degli utenti
  */
trait UserService:
  /** Registra un nuovo utente nel sistema, se il nome non è già esistente
    * @param name
    *   Il nome scelto dall'utente
    * @return
    *   Un [[Either]] contenente:
    *   - [[Right]] con l'oggetto [[User]] creato in caso di successo
    *   - [[Left]] con un messaggio di errore in caso di fallimento
    */
  def signUp(name: String): Either[String, User]

  /** Aggiorna le informazioni di un utente esistente
    * @param user
    *   L'istanza dell'utente con i dati aggiornati
    * @return
    *   Un [[Either]] contenente [[Unit]] in caso di successo o un messaggio
    *   d'errore in caso di fallimento
    */
  def updateUser(user: User): Either[String, Unit]

  /** Rimuove definitivamente un utente dal sistema
    * @param user
    *   L'utente da eliminare
    * @return
    *   Un [[Either]] contenente [[Unit]] in caso di successo o un messaggio
    *   d'errore in caso di fallimento
    */
  def deleteUser(user: User): Either[String, Unit]
