package prologfordummies.model

import prologfordummies.model.User

/** Trait della classe UserSessionState, dove si definiscono i due stati
  * possibili: Guest e Authenticated.
  */
sealed trait UserSessionState
case object Guest extends UserSessionState
case class Authenticated(user: User) extends UserSessionState

/** Gestisce lo stato della sessione utente corrente all'interno
  * dell'applicazione. Funge da punto di accesso per verificare
  * l'autenticazione e recuperare i dati dell'utente attivo.
  */
object UserSession:

  private var currentUser: UserSessionState = Guest

  // Salvo l'utente loggato nella sessione, passando da Guest ad Authenticated.
  def login(user: User): Unit =
    currentUser = Authenticated(user)

  // Per il logout, resetto lo stato a Guest.
  def logout(): Unit =
    currentUser = Guest

  // Metodo di utilità per ottenere l'utente attualmente loggato, se esiste.
  def currentSessionUser: Option[User] = currentUser match
    case Authenticated(u) => Some(u)
    case Guest            => None

  // Metodo di utilità per verificare se l'utente è autenticato.
  def isAuthenticated: Boolean = currentUser match
    case Authenticated(_) => true
    case _                => false
