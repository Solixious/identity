package org.pratyush.identity.repository;

import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.jooq.DSLContext;
import org.jooq.Record6;
import org.pratyush.identity.db.tables.records.TblUsersRecord;
import org.pratyush.identity.model.dto.User;
import org.pratyush.identity.model.request.UserRegistrationRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.pratyush.identity.db.tables.TblRefreshToken.TBL_REFRESH_TOKEN;
import static org.pratyush.identity.db.tables.TblUsers.TBL_USERS;

@Repository
@Slf4j
public class UserRepository {

    private final DSLContext dsl;
    private final Mapper mapper;
    private final RoleRepository roleRepository;

    public UserRepository(DSLContext dsl, Mapper mapper, RoleRepository roleRepository) {
        this.dsl = dsl;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
    }

    public Mono<Integer> register(UserRegistrationRequest request) {
        TblUsersRecord record = dsl.newRecord(TBL_USERS, request);
        return Mono.from(
                        dsl.insertInto(TBL_USERS)
                                .set(record))
                .flatMap(i -> roleRepository.addDefaultRoleForUser(request))
                .onErrorReturn(0);
    }

    public Mono<User> findByEmail(String email) {
        return Mono.from(
                        dsl.selectFrom(TBL_USERS)
                                .where(TBL_USERS.EMAIL.eq(email)))
                .map(this::toUser)
                .flatMap(roleRepository::attachRoles);
    }

    public Mono<User> findByRefreshToken(String refreshToken) {
        return Mono.from(
                        dsl.select(TBL_USERS.ID, TBL_USERS.EMAIL, TBL_USERS.FIRST_NAME, TBL_USERS.SURNAME, TBL_USERS.PASSWORD, TBL_USERS.DATE_OF_BIRTH)
                                .from(TBL_USERS)
                                .join(TBL_REFRESH_TOKEN).on(TBL_USERS.ID.eq(TBL_REFRESH_TOKEN.USER_ID))
                                .where(TBL_REFRESH_TOKEN.TOKEN.eq(refreshToken))
                                .and(TBL_REFRESH_TOKEN.EXPIRY_DATE.ge(LocalDate.now())))
                .map(this::toUser)
                .flatMap(roleRepository::attachRoles);
    }

    private User toUser(TblUsersRecord tblUsersRecord) {
        return mapper.map(tblUsersRecord, User.class);
    }

    private User toUser(Record6 tblUsersRecord) {
        User user = new User();
        user.setId((Integer) tblUsersRecord.value1());
        user.setEmail((String) tblUsersRecord.value2());
        user.setFirstName((String) tblUsersRecord.value3());
        user.setSurname((String) tblUsersRecord.value4());
        user.setPassword((String) tblUsersRecord.value5());
        user.setDateOfBirth(tblUsersRecord.value6().toString());
        return user;
    }
}
