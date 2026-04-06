# Projet_r4.01_Microservices

## Description

Projet de microservices pour une application de gestion de commandes, menus et plats utilisateurs. L'application utilise une architecture MVC (Model-View-Controller) en PHP pour l'interface utilisateur, avec plusieurs microservices backend basés sur Quarkus/Java.

## Architecture MVC

### Structure MVC

L'application IHM (Interface Homme-Machine) suit le pattern **Model-View-Controller** :

- **Model** : Logique métier et gestion des données
  - `src/ApiClient.php` : Client pour communiquer avec les APIs des microservices
  - Accès aux données via les microservices

- **View** : Présentation des données aux utilisateurs
  - `src/views/layout/` : Templates HTML commune (header, footer)
  - `src/views/commandes/` : Vues pour les commandes
  - `src/views/menus/` : Vues pour les menus
  - `src/views/plats/` : Vues pour les plats utilisateurs

- **Controller** : Gestion des requêtes et orchestration
  - `src/controllers/CommandesController.php` : Gestion des commandes
  - `src/controllers/MenusController.php` : Gestion des menus
  - `src/controllers/PlatsController.php` : Gestion des plats utilisateurs

### Microservices

- **Commandes** : Service de gestion des commandes (port configurable)
- **Menu** : Service de gestion des menus (port configurable)
- **PlatsUtilisateurs** : Service de gestion des plats utilisateurs (port configurable)

## Installation et Configuration

### Prérequis

- Node.js et npm
- PHP 7.4+
- Java 11+
- Maven

### Installation des dépendances

```bash
# IHM - Installation des dépendances npm
cd IHM
npm install

# Retour à la racine
cd ..
```

## Démarrage de l'Application

### 1. Démarrer les serveurs JSON (Fake API)

Dans des terminaux séparés, lancez les trois serveurs JSON Server qui fournissent les données simulées :

```bash
# Terminal 1 - Commandes (port 3005)
json-server --watch IHM/fakeApi/commandes.json --port 3005

# Terminal 2 - Menus (port 3004)
json-server --watch IHM/fakeApi/menus.json --port 3004

# Terminal 3 - Plats Utilisateurs (port 3003)
json-server --watch IHM/fakeApi/plats-utilisateurs.json --port 3003
```

### 2. Démarrer le serveur PHP

Dans un autre terminal, lancez le serveur PHP de développement :

```bash
cd IHM
php -S localhost:8000
```

Le serveur sera accessible à : **http://localhost:8000**

### 3. (Optionnel) Démarrer les microservices Quarkus

Si vous souhaitez utiliser les vrais microservices au lieu des Fake APIs :

```bash
# Commandes
cd Commandes
./mvnw quarkus:dev

# Menu
cd Menu
./mvnw quarkus:dev

# PlatsUtilisateurs
cd PlatsUtilisateurs
./mvnw quarkus:dev
```

## Accès à l'Application

Une fois tous les services démarrés :

1. **IHM** : http://localhost:8000
2. **Commandes API** : http://localhost:3005
3. **Menus API** : http://localhost:3004
4. **Plats Utilisateurs API** : http://localhost:3003

## Flux de Requêtes

```
Navigateur
    ↓
PHP (MVC Pattern)
    ├─ Controller (traite la requête)
    ├─ Model (requête à l'API)
    └─ View (affichage du résultat)
    ↓
JSON Server ou Microservices
    ↓
Fichiers JSON ou Base de données
```

## Notes

- Les fichiers JSON se trouvent dans `IHM/fakeApi/`
- Modifiez les ports des serveurs JSON si nécessaire
- Assurez-vous que les ports 3003, 3004, 3005 et 8000 sont disponibles
- L'application utilise PHP natif, aucun framework web requis
