package wku.smartplant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlantHistory is a Querydsl query type for PlantHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlantHistory extends EntityPathBase<PlantHistory> {

    private static final long serialVersionUID = 932569716L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlantHistory plantHistory = new QPlantHistory("plantHistory");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Double> humidity = createNumber("humidity", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Double> light = createNumber("light", Double.class);

    public final QPlant plant;

    public final NumberPath<Double> temperature = createNumber("temperature", Double.class);

    public final NumberPath<Double> water = createNumber("water", Double.class);

    public QPlantHistory(String variable) {
        this(PlantHistory.class, forVariable(variable), INITS);
    }

    public QPlantHistory(Path<? extends PlantHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlantHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlantHistory(PathMetadata metadata, PathInits inits) {
        this(PlantHistory.class, metadata, inits);
    }

    public QPlantHistory(Class<? extends PlantHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.plant = inits.isInitialized("plant") ? new QPlant(forProperty("plant"), inits.get("plant")) : null;
    }

}

