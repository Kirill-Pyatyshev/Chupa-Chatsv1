package com.chupachats.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "chat_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String chatId;
    private String senderId;
    private String recipientId;
}
