package wku.smartplant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlant is a Querydsl query type for Plant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlant extends EntityPathBase<Plant> {

    private static final long serialVersionUID = -1870046720L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlant plant = new QPlant("plant");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> exp = createNumber("exp", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public final StringPath name = createString("name");

    public final ListPath<PlantHistory, QPlantHistory> plantHistory = this.<PlantHistory, QPlantHistory>createList("plantHistory", PlantHistory.class, QPlantHistory.class, PathInits.DIRECT2);

    public final EnumPath<PlantType> plantType = createEnum("plantType", PlantType.class);

    public QPlant(String variable) {
        this(Plant.class, forVariable(variable), INITS);
    }

    public QPlant(Path<? extends Plant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlant(PathMetadata metadata, PathInits inits) {
        this(Plant.class, metadata, inits);
    }

    public QPlant(Class<? extends Plant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

