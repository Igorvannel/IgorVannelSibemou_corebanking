# Core Banking Application - Test Technique

## 📝 Contexte du Projet
Projet de test technique pour le poste de développeur backend chez Advance IT Group, visant à développer une application de core banking répondant aux exigences spécifiques de l'entreprise.

## 🎯 Objectif Principal
Concevoir une application bancaire modulaire qui permet :
- La gestion dynamique des opérations bancaires
- La configuration flexible des processus métier
- Une sécurité robuste des transactions
- Une traçabilité complète des opérations

## 🛠 Technologies et Choix Techniques
### Stack Technique
- **Langage**: Java 17
- **Framework**: Spring Boot 3.x
- **Base de données**:
  - PostgreSQL (production)
  - H2 Database (tests)
- **Sécurité**:
  - Spring Security
  - Authentification JWT
- **Gestion de projet**: Maven

### Principes Architecturaux
- Architecture hexagonale
- Séparation claire des responsabilités
- Configurable et extensible
- Forte emphase sur la sécurité et la traçabilité

## 📂 Structure Détaillée du Projet
```
src/
├── main/
│   ├── java/com/banking/
│   │   ├── config/         # Configuration globale de l'application
│   │   ├── controller/     # Endpoints REST API
│   │   ├── dto/            # Objects de transfert de données
│   │   ├── entity/         # Modèles de données métier
│   │   ├── exception/      # Gestion personnalisée des exceptions
│   │   ├── repository/     # Interfaces de persistance JPA
│   │   ├── security/       # Mécanismes d'authentification et autorisation
│   │   ├── service/        # Logique métier et traitements
│   │   └── util/           # Outils et utilitaires transverses
│   └── resources/
│       ├── application.yml         # Configuration principale
│       ├── application-local.yml   # Profil développement local
│       ├── application-test.yml    # Profil pour les tests
│       └── application-prod.yml    # Profil de production
└── test/                    # Tests unitaires et d'intégration
```

## 🔧 Prérequis Techniques
- Java Development Kit (JDK) 17
- Maven 3.8+
- PostgreSQL 13+
- IDE recommandé : IntelliJ IDEA ou Eclipse

## 🚀 Commandes Principales

### Lancement des Environnements
```bash
# Environnement Local
mvn spring-boot:run -Dspring-boot.run.profiles=local

# Environnement de Test
mvn spring-boot:run -Dspring-boot.run.profiles=test

# Environnement de Production
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Autres Commandes Utiles
```bash
# Compilation
mvn clean package

# Exécution des tests
mvn test

# Génération du rapport de couverture de tests
mvn jacoco:report
```

## 🌐 Configuration des Variables d'Environnement
Variables requises pour les profils test et production :
- `DB_HOST` : Hôte de la base de données
- `DB_PORT` : Port de connexion
- `DB_NAME` : Nom de la base de données
- `DB_USERNAME` : Utilisateur de connexion
- `DB_PASSWORD` : Mot de passe
- `JWT_SECRET` : Clé secrète pour le token JWT

## 🔐 Comptes de Test
Pour faciliter l'évaluation du projet, plusieurs comptes utilisateurs ont été créés avec différents rôles :

### 1. Compte Administrateur
- **Nom d'utilisateur**: `admin`
- **Mot de passe**: `admin123`
- **Rôles**:
  - ROLE_ADMIN
  - ROLE_USER
  - ROLE_ACCOUNT_MANAGER
  - ROLE_CONFIGURATION_MANAGER
  - ROLE_AUDITOR
  - ROLE_TELLER

### 2. Compte Utilisateur Standard
- **Nom d'utilisateur**: `user`
- **Mot de passe**: `user123`
- **Rôles**:
  - ROLE_USER

### 3. Compte Guichetier
- **Nom d'utilisateur**: `teller`
- **Mot de passe**: `teller123`
- **Rôles**:
  - ROLE_USER
  - ROLE_TELLER

### 4. Compte Auditeur
- **Nom d'utilisateur**: `auditor`
- **Mot de passe**: `auditor123`
- **Rôles**:
  - ROLE_USER
  - ROLE_AUDITOR

### 5. Compte Gestionnaire de Configuration
- **Nom d'utilisateur**: `config`
- **Mot de passe**: `config123`
- **Rôles**:
  - ROLE_USER
  - ROLE_CONFIGURATION_MANAGER

🚨 **Note importante** : Ces mots de passe sont temporaires et doivent être modifiés en production.

## 📋 Fonctionnalités Clés
- [x] Gestion des opérations bancaires (crédit/débit)
- [x] Configuration dynamique des processus
- [x] Authentification et autorisation sécurisées
- [x] Journalisation détaillée des transactions
- [ ] Génération de rapports (à développer)

## 🔐 Approche Sécurité
- Authentification JWT
- Gestion fine des autorisations
- Validation systématique des entrées
- Journalisation des événements sensibles

## 🤔 Réflexions et Défis Techniques
- Conception d'un moteur de règles configurable
- Gestion de la traçabilité sans impact sur les performances
- Flexibilité de l'architecture pour l'ajout de nouvelles fonctionnalités

## 🚨 Informations du Test Technique
- **Date limite de livraison** : 04 avril 2025
- **Initialisation du repository** : Avant le 29 mars 2025
- **Contact Entreprise** : if5.forge@gmail.com

## 📚 Documentation Complémentaire
- Swagger/OpenAPI 
- Documentation technique de rédaction
- Documentation Fonctionnel
- Rapport de test et validation