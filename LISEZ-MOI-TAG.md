# TAG — intégration contrôlée

Cette archive couvre le sujet WPWD 2024, sans ajouter de migration : la structure
`tags` et `show_tag`, les contraintes et les données de test existent déjà dans V16.

## Fonctionnalités apportées

- relation many-to-many entre `Show` et `Tag` ;
- recherche publique de spectacles par mot-clé, avec le nombre de résultats ;
- liste des mots-clés dans la fiche d'un spectacle ;
- ajout d'un mot-clé, seulement par ADMIN ;
- validation de mot-clé (obligatoire, maximum 30 caractères, réutilisation sans
  doublon) ;
- route publique `/tags/{tag}/shows-without` ;
- tests unitaires du service TAG.

## Régressions protégées

`SecurityConfiguration.java` conserve les protections ADMIN de ROOM, VIDEO et
TROUPE, et ajoute seulement `/shows/*/tags`.

## Fichiers inclus

Les fichiers de cette archive sont les remplacements complets nécessaires au
scénario TAG. Intégrer uniquement après sauvegarde et contrôle Git, puis exécuter
`./mvnw.cmd clean test` avant tout commit.
