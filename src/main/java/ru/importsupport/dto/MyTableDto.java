package ru.importsupport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
//TODO add table name
@Table(name = "my_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyTableDto {
    @Id
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "channel_id")
    private Integer channelId;
    @Column(name = "bot_id")
    private Integer botId;
    @Column
    private String data;
    @Column(name = "date_created")
    private Timestamp dateCreated;
    @Column(name = "date_updated")
    private Timestamp dateUpdated;
    @Column
    private Integer status;
}
