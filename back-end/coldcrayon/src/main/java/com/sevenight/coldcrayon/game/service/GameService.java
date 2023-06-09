package com.sevenight.coldcrayon.game.service;

import com.sevenight.coldcrayon.auth.dto.UserDto;
import com.sevenight.coldcrayon.game.dto.*;
import com.sevenight.coldcrayon.theme.entity.ThemeCategory;

import java.io.IOException;

public interface GameService {
    ResponseGameDto startGame(UserDto userDto, GameRequestDto gameRequestDto) throws IOException;

    ResponseRoundDto endRound(RequestRoundDto requestRoundDto);

    ResponseGameDto nextRound(RequestRoundDto requestRoundDto) throws IOException;

    GameEndDto endGame(String roomIdx);

}
