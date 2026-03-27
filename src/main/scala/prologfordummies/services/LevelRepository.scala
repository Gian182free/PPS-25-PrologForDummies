package prologfordummies.services

import prologfordummies.model.Level
import java.util.UUID

/** 
 * Interfaccia di Level Repository per la gestione dei dati dei livelli 
 * con metodi read-only
 */
trait LevelRepository:
  /**
    * Restituisce la lista dei livelli disponibili
    *
    * @return lista di [[Level]]
    */
  def loadAll(): List[Level]

  /**
    * Restituisce un livello se presente
    *
    * @param id L'id del livello
    * @return Some(Level) se trovato, None altrimenti
    */
  def findById(id: Level.Id): Option[Level]

