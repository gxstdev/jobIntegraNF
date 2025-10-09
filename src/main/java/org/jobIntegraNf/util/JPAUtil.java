package org.jobIntegraNf.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Fornece fábrica e instâncias de {@link jakarta.persistence.EntityManager}
 * para acesso ao banco de dados via JPA/Hibernate.
 */
public class JPAUtil {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("JobIntegraNF");

    /**
     * Obtém uma nova instância de {@link EntityManager} a partir da fábrica
     * configurada pelo persistence unit "JobIntegraNF".
     *
     * @return uma instância de EntityManager.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Encerra a fábrica de {@link EntityManager} do aplicativo.
     * Deve ser chamada no shutdown da aplicação.
     */
    public static void close() {
        emf.close();
    }
}
