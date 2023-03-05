package org.pratyush.identity.repository;

import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.jooq.DSLContext;
import org.pratyush.identity.db.tables.records.TblRefreshTokenRecord;
import org.pratyush.identity.model.dto.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.pratyush.identity.db.tables.TblRefreshToken.TBL_REFRESH_TOKEN;

@Repository
@Slf4j
public class RefreshTokenRepository {

    private final DSLContext dsl;
    private final Mapper mapper;

    public RefreshTokenRepository(DSLContext dsl, Mapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    public Mono<String> createRefreshToken(User user) {
        String refreshToken = UUID.randomUUID().toString();
        return Mono.from(dsl.insertInto(TBL_REFRESH_TOKEN)
                .set(TBL_REFRESH_TOKEN.TOKEN, refreshToken)
                .set(TBL_REFRESH_TOKEN.USER_ID, user.getId())).map(integer -> refreshToken);
    }
}
