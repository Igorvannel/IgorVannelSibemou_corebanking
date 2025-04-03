package com.banking.IgorVannelSibemou_corebanking.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lancée lorsqu'une ressource demandée n'est pas trouvée dans le système.
 * Cette exception est typiquement utilisée lorsqu'une entité n'est pas trouvée dans la base de données
 * à partir de son identifiant ou d'un autre critère de recherche.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructeur avec un message d'erreur.
     *
     * @param message Le message décrivant la ressource qui n'a pas été trouvée
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message d'erreur et une cause.
     *
     * @param message Le message décrivant la ressource qui n'a pas été trouvée
     * @param cause La cause originale de cette exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crée une exception ResourceNotFoundException pour une entité spécifique.
     *
     * @param resourceName Le nom de la ressource (par exemple, "Account", "Operation")
     * @param fieldName Le nom du champ utilisé pour la recherche (par exemple, "id", "accountNumber")
     * @param fieldValue La valeur du champ recherché
     * @return Une nouvelle instance de ResourceNotFoundException avec un message formaté
     */
    public static ResourceNotFoundException create(String resourceName, String fieldName, Object fieldValue) {
        return new ResourceNotFoundException(
                String.format("%s non trouvé avec %s : '%s'", resourceName, fieldName, fieldValue)
        );
    }
}
