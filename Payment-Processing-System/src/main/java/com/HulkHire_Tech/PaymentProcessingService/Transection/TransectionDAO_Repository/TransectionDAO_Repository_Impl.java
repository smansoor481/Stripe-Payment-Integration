package com.HulkHire_Tech.PaymentProcessingService.Transection.TransectionDAO_Repository;

import com.HulkHire_Tech.PaymentProcessingService.Transection.Transection_Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TransectionDAO_Repository_Impl implements TransectionDAO_Repository {
    private final NamedParameterJdbcTemplate JdbcTemplate;

    private static final String INSERT_SQL = """
        INSERT INTO payments.`Transaction` (
            userId, paymentMethodId, providerId, paymentTypeId, txnStatusId,
            amount, currency, merchantTransactionReference, txnReference
        ) VALUES (
            :userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId,
            :amount, :currency, :merchantTransactionReference, :txnReference
        )
        """;

    @Override
    public Integer InsertTransection(Transection_Entity transectionEntity) {
        log.info("=> Inserting transaction: {}", transectionEntity);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(transectionEntity);

        JdbcTemplate.update(INSERT_SQL, params, keyHolder, new String[]{"id"});
        // set the generated id back to the entity
        transectionEntity.setId(keyHolder.getKey().intValue());

        log.info("=> Inserted transaction with generated id: {}", transectionEntity.getId());
        return transectionEntity.getId();
    }

    @Override
    public Transection_Entity getTransectionByTransectionReference(String trxReference) {
        String sql = "SELECT * FROM payments.`Transaction` WHERE txnReference = :txnReference";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("txnReference", trxReference);

        Transection_Entity trxEntity = null;
        try {
            trxEntity = JdbcTemplate.queryForObject(
                    sql,
                    params,
                    new BeanPropertyRowMapper<>(Transection_Entity.class)
            );
        } catch (EmptyResultDataAccessException e) {
            log.warn("=> No transaction found with txnReference: {}", trxReference);
        }

        return trxEntity; // will return null if not found
    }

    @Override
    public Transection_Entity getTransectionByProviderReference(String providerReference, int providerId) {
        String sql = "SELECT * FROM payments.`Transaction` " +
                "WHERE providerReference = :providerReference AND providerId = :providerId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("providerReference", providerReference);
        params.addValue("providerId", providerId);

        Transection_Entity trxEntity = null;
        try {
            trxEntity = JdbcTemplate.queryForObject(
                    sql,
                    params,
                    new BeanPropertyRowMapper<>(Transection_Entity.class)
            );
        } catch (EmptyResultDataAccessException e) {
            log.warn("=> No transaction found with providerReference: {} and providerId: {}", providerReference, providerId);
        }

        return trxEntity; // will return null if not found
    }

    @Override
    public Integer updateTransectionByTransectionReference(Transection_Entity transection_entity) {
        log.info("=> Enter updateTransectionByTransectionReference with entity: {}", transection_entity);
        //Write a Update Query for update the transaction
        String sql = """
            UPDATE payments.`Transaction`
            SET txnStatusId = :txnStatusId,
                providerReference = :providerReference
            WHERE txnReference = :txnReference
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("txnStatusId", transection_entity.getTxnStatusId());
        params.addValue("providerReference", transection_entity.getProviderReference());
        params.addValue("txnReference", transection_entity.getTxnReference());

        log.info("=> Update SQL: {}", sql);
        return JdbcTemplate.update(sql, params);
    }

    @Override
    public Integer PendingTransectionByTransectionReference(Transection_Entity transection_entity) {
        log.info("=> Enter pendingTransectionByTransectionReference with entity: {}", transection_entity);
        //Write a Update Query for update the transaction
        String sql = """
                    UPDATE payments.`Transaction`
                    SET txnStatusId = :txnStatusId
                    WHERE txnReference = :txnReference
                    """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("txnStatusId", transection_entity.getTxnStatusId());
        params.addValue("txnReference", transection_entity.getTxnReference());

        log.info("=> Pending SQL: {}", sql);
        return JdbcTemplate.update(sql, params);
    }
}
