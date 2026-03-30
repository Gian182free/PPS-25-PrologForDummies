---
layout: default
title: Sprint 5
nav_order: 13
---

# Sprint Planning 5
**Periodo:** 16/03/2026 – 29/03/2026


## Sprint Goal
Completare il ciclo di gioco introducendo il sistema di punteggio, la schermata finale di riepilogo del quiz e la persistenza dei progressi utente.
Inoltre, estendere la validazione delle risposte tramite motore Prolog, migliorare la gestione della sessione di gioco e rafforzare la qualità complessiva dell’interfaccia e dell’architettura applicativa.


---

## Deadline
La scadenza dello Sprint è il **29/03/2026**.


---

## Task pianificati per lo Sprint


### Quiz e Valutazione
**Sistema di punteggio** — introduzione del calcolo dei risultati in base alle risposte corrette e al numero totale di tentativi.


**Schermata finale del quiz** — sviluppo della vista conclusiva con riepilogo delle prestazioni dell’utente al termine del livello.


**Validazione tramite Prolog** — integrazione del motore Prolog per la validazione delle risposte, sia per le domande aperte sia per quelle a scelta multipla.


### Progressi Utente
**Persistenza dei progressi** — salvataggio dei risultati del quiz associati all’utente autenticato.


**Storico completamento livelli** — registrazione dei tentativi e dei livelli completati per consentire future analisi dei progressi.


### Architettura e Gestione Sessione
**Gestione sessione di gioco** — estensione della sessione livello per tracciare correttamente tentativi, risposte corrette e tempo trascorso.


**Refactoring del controller quiz** — consolidamento della logica di fine livello, avanzamento e salvataggio dei dati in un unico punto di controllo.


### Interfaccia Grafica
**Miglioramenti grafici** — affinamento della UI finale e delle finestre di conferma per una migliore esperienza utente.


**Coerenza visiva** — uniformare i componenti grafici delle diverse schermate del quiz.


### Quality Assurance
**Test delle funzionalità di sessione e progressi** — verifica del corretto salvataggio dei dati e della gestione delle statistiche di gioco.


---

## Sprint Review


### Progresso
Lo Sprint ha completato il flusso di gioco introducendo la fase finale del quiz e il salvataggio dei progressi dell’utente.
Al termine del livello, l’applicazione ora raccoglie statistiche sul quiz svolto, calcola il riepilogo finale e mostra una finestra di conferma con i risultati ottenuti.


È stata inoltre introdotta la persistenza dei progressi utente, così da registrare in modo strutturato i livelli completati, il numero di tentativi, le risposte corrette e il tempo impiegato.
Questo ha permesso di dare continuità all’esperienza di gioco e di preparare il terreno per la futura schermata dei progressi.


Sul fronte della logica applicativa, il controller dei quiz è stato ulteriormente rafforzato: ora gestisce non solo la selezione della prossima domanda, ma anche la chiusura del livello, il calcolo delle statistiche e il salvataggio dei risultati.
La gestione della sessione di gioco è stata estesa per supportare il tracciamento completo delle prestazioni dell’utente.


### Architettura
Le principali modifiche architetturali riguardano:
- Centralizzazione della logica di fine livello nel QuizController.
- Introduzione della persistenza dei progressi utente.
- Estensione della sessione di livello per raccogliere statistiche di gioco.
- Separazione più netta tra flusso applicativo, persistenza e presentazione.
- Miglioramento dell'architettura per la schermata dedicata ai progressi.


### Utente
L’utente ora può:
- Completare un quiz e visualizzare un riepilogo finale.
- Ricevere un feedback più chiaro sui risultati ottenuti.
- Vedere tracciati i propri progressi in modo persistente.
- Visualizzare un riepilogo completo dei risultati del livello svolto.

### Qualità
I test introdotti e quelli già presenti hanno confermato:
- Corretto avanzamento del flusso quiz.
- Persistenza affidabile dei progressi.
- Calcolo coerente delle statistiche finali.
- Nessuna regressione rispetto alle funzionalità introdotte negli sprint precedenti.


L’infrastruttura di testing risulta ora più solida e adatta a supportare ulteriori estensioni, come una schermata dedicata ai progressi complessivi e analisi più avanzate dei risultati.


---

## Sprint Retrospective


### Cosa è andato bene
- La chiusura del flusso quiz ha reso l’applicazione più completa e soddisfacente per l’utente.
- Il salvataggio dei progressi ha introdotto una base solida per la parte di tracking e monitoraggio.
- Il controller ha beneficiato di un ulteriore accentramento della logica, migliorando la leggibilità del codice.
- La gestione della sessione di livello si è dimostrata utile per mantenere statistiche coerenti durante tutta la partita.


### Difficoltà incontrate
- La sincronizzazione tra sessione, persistenza e interfaccia finale ha richiesto attenzione per evitare inconsistenze nei dati salvati.
- La definizione del momento corretto in cui chiudere il livello e mostrare il riepilogo ha richiesto alcuni aggiustamenti logici.
- L’integrazione della persistenza dei progressi ha richiesto una revisione dell’architettura già consolidata negli sprint precedenti.


---

## Conclusioni
Complessivamente il team è soddisfatto del lavoro effettuato nonostante le difficoltà riconducibili al nuovo paradigma di programmazione.
Ogni componente è stato in grado di portare a termine i propri task, mirando a uno stile di programmazione il più funzionale possibile.
Lo sviluppo dell'applicazione è avvenuto tramite un approccio Test-Driven Development, seppur non sempre sia stato rispettato in maniera rigorosa.