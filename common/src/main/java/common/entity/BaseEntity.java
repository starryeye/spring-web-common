package common.entity;

import lombok.Getter;

/**
 * TODO
 */
@Getter
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends BaseTimeEntity{

//    @CreatedBy
//    @Column(updatable = false)
    private String createdBy;

//    @LastModifiedBy
    private String lastModifiedBy;
}

