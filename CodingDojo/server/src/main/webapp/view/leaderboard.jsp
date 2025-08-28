<%--
  #%L
  Codenjoy - it's a dojo-like platform from developers to developers.
  %%
  Copyright (C) 2012 - 2022 Codenjoy
  %%
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as
  published by the Free Software Foundation, either version 3 of the
  License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public
  License along with this program.  If not, see
  <http://www.gnu.org/licenses/gpl-3.0.html>.
  #L%
  --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<div id="leaderboard" class="board" style="display:none;" zoom-on-wheel>
    <div class="tabs">
        <label id="leaderboard-tab" class="tv-tab" for="tv-tab-1">Leaderboard</label>
        <label id="room-chat-tab" class="tv-tab hidden" for="tv-tab-2">Room chat</label>
        <label id="field-chat-tab" class="tv-tab hidden" for="tv-tab-3">Field chat</label>
    </div>

    <input class="tv-radio" id="tv-tab-1" name="tv-group" type="radio" checked="checked"/>
    <div class="tv-content">
        <%@include file="leaderstable.jsp"%>
        <%@include file="info.jsp"%>
    </div>

    <input class="tv-radio" id="tv-tab-2" name="tv-group" type="radio"/>
    <div class="tv-content id-room-chat">
        <%@include file="chat.jsp"%>
    </div>

    <input class="tv-radio" id="tv-tab-3" name="tv-group" type="radio"/>
    <div class="tv-content id-field-chat">
        <%@include file="chat.jsp"%>
    </div>
</div>