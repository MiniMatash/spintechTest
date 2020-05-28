package com.minimatash.spintech.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class FavouriteActor {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private Long actorId;
}
