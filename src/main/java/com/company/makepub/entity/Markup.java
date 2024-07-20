package com.company.makepub.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

@JmixEntity
@Table(name = "MARKUP", indexes = {
        @Index(name = "IDX_MARKUP_UNQ", columnList = "ID", unique = true)
})
@Entity
public class Markup {
    @NotNull
    @Column(name = "ID", nullable = false)
    @Id
    private String id;

    @NotNull
    @Column(name = "HTML_START")
    private String htmlStart;

    @Column(name = "HTML_END")
    private String htmlEnd;

    @Column(name = "IS_PARAGRAPH", nullable = false)
    @NotNull
    private Boolean isParagraph = false;

    @Column(name = "IS_FOOTNOTE_SYMBOL", nullable = false)
    @NotNull
    private Boolean isFootnoteSymbol = false;

    @Column(name = "IS_FOOTNOTE_TEXT", nullable = false)
    @NotNull
    private Boolean isFootnoteText = false;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private OffsetDateTime createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private OffsetDateTime lastModifiedDate;

    public Boolean getIsFootnoteText() {
        return isFootnoteText;
    }

    public void setIsFootnoteText(Boolean isFootnoteText) {
        this.isFootnoteText = isFootnoteText;
    }

    public Boolean getIsFootnoteSymbol() {
        return isFootnoteSymbol;
    }

    public void setIsFootnoteSymbol(Boolean isFootnoteSymbol) {
        this.isFootnoteSymbol = isFootnoteSymbol;
    }

    public Boolean getIsParagraph() {
        return isParagraph;
    }

    public void setIsParagraph(Boolean isParagraph) {
        this.isParagraph = isParagraph;
    }

    public String getHtmlEnd() {
        return htmlEnd;
    }

    public void setHtmlEnd(String htmlEnd) {
        this.htmlEnd = htmlEnd;
    }

    public String getHtmlStart() {
        return htmlStart;
    }

    public void setHtmlStart(String htmlStart) {
        this.htmlStart = htmlStart;
    }

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}