package kr.pe.kwonnam.hibernate4localdatetime.entities;

import kr.pe.kwonnam.hibernate4localdatetime.StringLocalDateUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "string_local_date_entities")
public class StringLocalDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Type(
        type = "kr.pe.kwonnam.hibernate4localdatetime.StringLocalDateUserType",
        parameters = {
            @Parameter(name = StringLocalDateUserType.PARAM_PATTERN, value = "yyyyMMdd")
        }
    )
    @Column(name = "created_date", nullable = true, length = 8)
    private LocalDate createdDate;

    @Type(
        type = "kr.pe.kwonnam.hibernate4localdatetime.StringLocalDateUserType",
        parameters = {
            @Parameter(name = StringLocalDateUserType.PARAM_PATTERN, value = "yyyy/MM/dd")
        }
    )
    @Column(name = "updated_date", nullable = true, length = 10)
    private LocalDate updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }
}
