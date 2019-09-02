package com.penguin.penguincoco.model.problem;

import com.penguin.penguincoco.model.base.BaseEntity;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import java.util.List;

@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class)})
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemBank extends BaseEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<Language> languages;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<Tag> tags;
    private String description;
    private String inputDesc;
    private String outputDesc;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<TestCase> testCases;

}
