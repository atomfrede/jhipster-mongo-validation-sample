package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Band;

/**
 * A Hipster.
 */

@Document(collection = "hipster")
public class Hipster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("name")
    private String name;

    @NotNull
    @Field("shirt")
    private Band shirt;

    @Min(value = 17)
    @Max(value = 35)
    @Field("age")
    private Integer age;

    @NotNull
    @Size(max = 5000000)
    @Field("avatar")
    private byte[] avatar;

    @Field("avatar_content_type")
    private String avatarContentType;

    @NotNull
    @Field("birthday")
    private LocalDate birthday;

    @NotNull
    @Field("really_hipster")
    private Boolean reallyHipster;

    @Field("sample_field")
    private Double sampleField;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Band getShirt() {
        return shirt;
    }

    public void setShirt(Band shirt) {
        this.shirt = shirt;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarContentType() {
        return avatarContentType;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Boolean isReallyHipster() {
        return reallyHipster;
    }

    public void setReallyHipster(Boolean reallyHipster) {
        this.reallyHipster = reallyHipster;
    }

    public Double getSampleField() {
        return sampleField;
    }

    public void setSampleField(Double sampleField) {
        this.sampleField = sampleField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hipster hipster = (Hipster) o;
        if(hipster.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hipster.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hipster{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shirt='" + shirt + "'" +
            ", age='" + age + "'" +
            ", avatar='" + avatar + "'" +
            ", avatarContentType='" + avatarContentType + "'" +
            ", birthday='" + birthday + "'" +
            ", reallyHipster='" + reallyHipster + "'" +
            ", sampleField='" + sampleField + "'" +
            '}';
    }
}
