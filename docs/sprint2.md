---
layout: default
title: Sprint 2
nav_order: 11
---

# Sprint Planning 2
**Periodo:** 12/02/2026 – 22/02/2026  

## Sprint Goal
Completare l’interfaccia di registrazione, introdurre la persistenza locale per gli utenti e abilitare il cambio scene dinamico. Preparare il terreno per l’inserimento dei primi quiz.

---

## Deadline
La scadenza dello sprint è il **22/02/2026**.

---

## Task completati nello Sprint

### Interfaccia
**Pagina Registrazione** — sviluppo e integrazione della schermata di registrazione con le stesse regole stilistiche della login.

**Scene switching** — meccanismo funzionale per passare da una scena all'altra (login ➜ registrazione ➜ dashboard).

### Persistenza
**Storage utenti** — introduzione di un file/DB locale per salvare gli oggetti `User` creati e letti dal CRUD.

### Logica utente
**Validazione** — controllo dei campi durante la registrazione e modifica con messaggi di errore visibili.

### Quality Assurance
**Test persistenti** — estensione della suite di unit test per verificare le operazioni CRUD atte alla persistenza.

---

## Sprint Review

### Progresso
Le funzionalità di base dell’utente ora conservano i dati tra le sessioni e l’esperienza GUI è navigabile.

### Architettura
Nessuna modifica sostanziale, ma il package `persistence` è stato aggiunto con le classi di salvataggio.

### Utente
Registrazione e login sono operativi con validazione e salvataggio; resta da sviluppare la pagina di modifica/eliminazione.

### Qualità
I test sono passati e l’infrastruttura per testare la persistenza è stabile.

---

## Sprint Retrospective

### Cosa è andato bene
- Persistenza semplice e veloce da introdurre grazie alla struttura MVC chiara.
- Scene switching è risultato molto utile per prototipare percorsi utente.

### Difficoltà incontrate
- Gestione degli errori di I/O (file corrotto, permessi) ha richiesto più tempo del previsto.
- La validazione lato client ha rivelato casi limite non previsti inizialmente.

---

## Miglioramenti previsti per Sprint 3
- Completare CRUD utente (modifica/eliminazione interface e storage).
- Iniziare la modellazione dei quiz (risposta multipla e fill-in-the-blank).
- Progettare interfaccia grafica per l’interazione Utente‑Quiz.
