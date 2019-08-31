package com.penguin.penguincoco.dao.domain.problembank;

import com.penguin.penguincoco.dao.domain.base.BaseEntity;
import com.penguin.penguincoco.dao.domain.problem.TestCase;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ProblemBank extends BaseEntity {

    private String name;
    private String category;
    @Type(type = "string-array")
    @Column(name = "tag", columnDefinition = "text[]")
    private String[] tag;
    private String description;
    private String inputDesc;
    private String outputDesc;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<TestCase> testCases;

    public ProblemBank(String name, String category, String[] tag, String description, String inputDesc, String outputDesc, List<TestCase> testCases) {
        this.name = name;
        this.category = category;
        this.tag = tag;
        this.description = description;
        this.inputDesc = inputDesc;
        this.outputDesc = outputDesc;
        this.testCases = testCases;
    }
}
