package com.chupachats.repositories;

import com.chupachats.models.ChatMessage;
import com.chupachats.models.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);

    ChatMessage findBySenderIdAndRecipientId(String senderId, String recipientId);

    List<ChatMessage> findByChatId(Object chatId);
}
