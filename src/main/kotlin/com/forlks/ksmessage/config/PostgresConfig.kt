package com.forlks.ksmessage.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
        basePackages = ["com.forlks.ksmessage.common.repository.postgres"],
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager"
)
class PostgresConfig (
        private val jpaProperties: JpaProperties,
        private val hibernateProperties: HibernateProperties
){

    @Bean
    @ConfigurationProperties("spring.datasource.postgresql")
    fun postgresDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    fun postgresDataSource(): DataSource {
        return postgresDataSourceProperties()
                .initializeDataSourceBuilder()
                .build()
    }

    @Bean
    fun postgresEntityManagerFactory(
            builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        val properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.properties, HibernateSettings()
        )

        return builder.dataSource(postgresDataSource())
                .packages("com.forlks.ksmessage.common.entity.postgres")
                .persistenceUnit("postgresEntityManager")
                .properties(properties)
                .build()
    }

    @Bean
    fun postgresTransactionManager(
            @Qualifier(value = "postgresEntityManagerFactory")  entityManagerFactory : LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory.`object`

        return transactionManager
    }
}
