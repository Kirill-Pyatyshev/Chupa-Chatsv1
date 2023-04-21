package com.chupachats.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "chat_notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String senderId;
    private String senderName;
}
