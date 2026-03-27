package prologfordummies.services

import prologfordummies.model.User

/** Interfaccia di User Repository per la gestione dei dati utente.
  */
trait UserRepository:
  /** Salva un nuovo utente nel sistema
    * @param user
    *   L'istanza [[User]] da archiviare
    */
  def save(user: User): Unit

  /** Recupera l'elenco completo di tutti gli utenti registrati
    * @return
    *   Una [[List]] contenente tutti gli oggetti [[User]]
    */
  def loadAll(): List[User]

  /** Ricerca un utente specifico tramite il suo nome univoco
    * @param name
    *   Il nome utente da cercare
    * @return
    *   Un [[Option]] contenente lo [[User]] se trovato, [[None]] altrimenti
    */
  def findByName(name: String): Option[User]

  /** Aggiorna i dati di un utente già esistente
    * @param user
    *   L'istanza [[User]] contenente le informazioni aggiornate
    */
  def update(user: User): Unit

  /** Elimina definitivamente un utente dal sistema
    * @param user
    *   L'istanza [[User]] da rimuovere
    */
  def delete(user: User): Unit
