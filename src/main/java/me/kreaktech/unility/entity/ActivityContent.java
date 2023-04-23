package me.kreaktech.unility.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.kreaktech.unility.constants.Enum.Language;
import me.kreaktech.unility.constants.Enum.PhysicalStatus;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "activity_content")
public class ActivityContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @Column(name = "organizer")
    private String organizer;

    @Column(name = "incentive")
    private boolean incentive;

    @NonNull
    @Column(name = "activity_language")
    private Language activityLanguage;

    @NonNull
    @Column(name = "activity_duration")
    private Timestamp activityDuration;

    @Column(name = "is_canceled")
    private boolean isCanceled;

    @NonNull
    @Column(name = "details")
    private String details;

    @NonNull
    @Column(name = "physical_status")
    private PhysicalStatus physicalStatus;
}
