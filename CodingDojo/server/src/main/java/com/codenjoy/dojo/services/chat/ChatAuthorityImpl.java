package com.codenjoy.dojo.services.chat;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.services.Player;
import com.codenjoy.dojo.services.dao.Chat;
import com.codenjoy.dojo.services.multiplayer.Spreader;
import com.codenjoy.dojo.web.rest.pojo.PMessage;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.services.chat.ChatService.exception;
import static com.codenjoy.dojo.services.chat.ChatType.*;
import static com.codenjoy.dojo.services.dao.Chat.FOR_ALL;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class ChatAuthorityImpl implements ChatAuthority {

    private final ChatService service;
    private final Chat chat;
    private final Spreader spreader;

    private final List<Player> player;
    private final String playerId;
    private final OnChange listener;

    public ChatAuthorityImpl(ChatService service, Chat chat, Spreader spreader, String playerId, OnChange listener) {
        this.service = service;
        this.chat = chat;
        this.spreader = spreader;
        this.playerId = playerId;
        this.player = player(playerId);
        this.listener = listener;
    }

    private void inform(List<Player> players,
                        TriConsumer<List<PMessage>, ChatType, String> listener,
                        ChatType type,
                        List<PMessage> messages)
    {
        if (messages.isEmpty()) return;
        players.forEach(player -> listener.accept(messages, type, player.getId()));
    }

    private void informCreated(List<Player> players, ChatType type, List<PMessage> messages) {
        inform(players, listener::created, type, messages);
    }

    private void informDeleted(List<Player> players, ChatType type, List<PMessage> messages) {
        inform(players, listener::deleted, type, messages);
    }

    private List<Player> players(String room) {
        return spreader.players(room);
    }

    private List<Player> players(int topicId) {
        return spreader.players(topicId);
    }

    private List<Player> players(String recipientId, Integer topicId) {
        return recipientId == null ? players(topicId) : player(recipientId);
    }

    private List<Player> player(String playerId) {
        return Arrays.asList(new Player(playerId));
    }

    @Override
    public List<PMessage> getAllRoom(Filter filter) {
        List<PMessage> messages = service.getRoomMessages(playerId, filter);
        informCreated(player, ROOM, messages);
        return messages;
    }

    @Override
    public List<PMessage> getAllTopic(int topicId, Filter filter) {
        // TODO тут пришлось нарушить инкапсуляцию исходного метода, чтобы достать тип type сообщения
        ChatType type = service.validateTopicAvailable(topicId, playerId, filter.room());
        List<PMessage> messages = service.getMessages(type, topicId, playerId, filter);
        informCreated(player, type.root(), messages);
        return messages;
    }

    @Override
    public List<PMessage> getAllField(Filter filter) {
        List<PMessage> messages = service.getFieldMessages(playerId, filter);
        informCreated(player, FIELD, messages);
        return messages;
    }

    @Override
    public PMessage get(int id, String room) {
        PMessage message = service.getMessage(id, room, playerId);
        ChatType type = valueOf(message.getType()).root();
        informCreated(player, type, asList(message));
        return message;
    }

    @Override
    public PMessage postRoom(String text, String room) {
        PMessage message = service.postMessageForRoom(text, room, playerId);
        informCreated(players(room), ROOM, asList(message));
        return message;
    }

    @Override
    public PMessage postTopic(int topicId, String text, String room) {
        // TODO тут пришлось нарушить инкапсуляцию исходного метода, чтобы достать тип type сообщения
        ChatType type = service.validateTopicAvailable(topicId, playerId, room);
        PMessage message = service.saveMessage(topicId, type, text, room, playerId, FOR_ALL);
        informCreated(players(room), type.root(), asList(message));
        return message;
    }

    @Override
    public PMessage postFieldFor(String recipientId, String text, String room) {
        PMessage message = service.postMessageForField(text, room, playerId, recipientId);
        informCreated(players(recipientId, message.getTopicId()), FIELD, asList(message));
        return message;
    }

    @Override
    public PMessage postField(String text, String room) {
        return postFieldFor(FOR_ALL, text, room);
    }

    @Override
    public boolean delete(int id, String room) {
        // TODO тут несколько запросов делается, не совсем оптимально
        Chat.Message message = chat.getMessageById(id);
        boolean deleted = service.deleteMessage(id, room, playerId);
        if (deleted) {
            informDeleted(asList(service.wrap(message)), room, headFor(message));
        }
        return deleted;
    }

    private Chat.Message headFor(Chat.Message message) {
        while (message.getType() == ROOM_TOPIC
            || message.getType() == FIELD_TOPIC)
        {
            Integer topicId = message.getTopicId();
            if (topicId == null) {
                throw exception("Topic message with null topic_id: " + message.getId());
            }
            message = chat.getAnyMessageById(topicId);
            if (message == null) {
                return null;
            }
        }
        return message;
    }

    private void informDeleted(List<PMessage> messages, String room, Chat.Message root) {
        ChatType type = (root == null)
                // TODO очень мало вероятно что такое случится,
                //   но если так - то информируем всех в комнате
                ? ROOM
                : root.getType();

        switch (type.root()) {
            case ROOM:
                informDeleted(players(room), ROOM, messages);
                break;
            case FIELD:
                informDeleted(players(root.getTopicId()), FIELD, messages);
                break;
            default:
                throw exception("Should be only ROOM or FIELD: " +
                        messages.stream()
                                .map(PMessage::getId)
                                .map(Object::toString)
                                .collect(joining(",")));
        }
    }

}
