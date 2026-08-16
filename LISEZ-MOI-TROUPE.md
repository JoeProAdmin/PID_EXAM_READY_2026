# TROUPE — intégration contrôlée

Cette archive ne contient que les huit fichiers prévus pour le scénario TROUPE.
Elle n'inclut aucune migration Flyway et ne modifie pas les fichiers ROOM ou VIDEO,
sauf `SecurityConfiguration.java`, qui conserve explicitement leurs protections :

- `/representations/create` : ADMIN ;
- `/shows/*/videos` : ADMIN ;
- `/artists/*/troupe` : ADMIN.

## Fonctionnalités apportées

- Validation de l'entité `Troupe` : nom obligatoire, unique en base et maximum
  60 caractères ; URL de logo maximum 255 caractères.
- Affiliation et désaffiliation via `ArtistService`.
- Route POST `/artists/{id}/troupe` protégée côté serveur par le rôle ADMIN.
- Liste dynamique des troupes et option `Non affilié` (valeur vide).
- Messages flash de succès ou d'erreur.
- Contrôles des artistes, troupes et identifiants inexistants.
- Logo conservé à la largeur de 50 pixels.
- Tests unitaires d'affiliation et de désaffiliation.

## Intégration ultérieure

Ne copiez ces fichiers dans le projet local qu'après sauvegarde, vérification Git
et instruction explicite. Ensuite, exécutez `./mvnw.cmd clean test` dans
PowerShell avant tout démarrage de l'application.
