# TARIF — intégration contrôlée

Cette archive couvre le sujet PID-WPWD2022SS-DISPENSE. La table `tarifs`, ses
données et sa clé étrangère vers `shows` existent déjà dans V16 : aucune migration
Flyway n'est ajoutée.

## Fonctionnalités apportées

- relation un spectacle / plusieurs tarifs spéciaux ;
- affichage du prix normal et des tarifs spéciaux dans la fiche d'un spectacle ;
- route publique `/shows/{id}/tarifs` ;
- formulaire ADMIN `/tarifs/create` ;
- liste des types `promo`, `senior`, `kids` fournie par l'API
  `/api/tarifs/types` puis chargée dynamiquement avec JavaScript ;
- validations serveur du spectacle, du type et du prix ;
- tests du service TARIF.

La sécurité ROOM, VIDEO, TROUPE et TAG est conservée et la protection ADMIN de
`/tarifs/create` est ajoutée.
