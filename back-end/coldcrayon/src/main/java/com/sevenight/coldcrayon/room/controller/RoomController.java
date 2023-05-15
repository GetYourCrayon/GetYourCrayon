package com.sevenight.coldcrayon.room.controller;
import com.sevenight.coldcrayon.auth.dto.UserDto;
import com.sevenight.coldcrayon.auth.service.AuthService;
import com.sevenight.coldcrayon.room.dto.RoomDto;
import com.sevenight.coldcrayon.room.dto.RoomRequestDto;
import com.sevenight.coldcrayon.room.dto.RoomResponseDto;
import com.sevenight.coldcrayon.room.service.RoomService;
import com.sevenight.coldcrayon.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestHeader String Authorization){

        System.err.println("컨트롤러 진입");
        UserDto user = authService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
//        UserDto user = UserDto.builder().userIdx(1L).userEmail("1번@naver.com").userPoint(0).userNickname("바보").build();
        Map<String, Object> data = roomService.saveRoom(user);

        return ResponseEntity.badRequest().body(data);
    }

    @PostMapping("/join")
    public ResponseEntity<RoomResponseDto> joinRoom(@RequestHeader String Authorization, @RequestBody String roomIdx){
        UserDto user = authService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
//        UserDto user = UserDto.builder().userIdx(2L).userEmail("2번@naver.com").userPoint(0).userNickname("바보2").build();

        RoomResponseDto roomResponseDto = roomService.joinRoom(user, roomIdx);

        return ResponseEntity.status(HttpStatus.OK).body(roomResponseDto);
    }

    @GetMapping("/get-user-list")
    public ResponseEntity<?> getUserList(@RequestHeader String Authorization, @RequestBody String roomIdx){
        return ResponseEntity.ok().body(roomService.getUserList(roomIdx));
    }


    @PostMapping("/out")
    public RoomResponseDto outRoom(@RequestHeader String Authorization){

        UserDto user = authService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
        return roomService.outRoom(user);
    }

    @PatchMapping("/change-admin")
    public RoomResponseDto changeAdmin(@RequestHeader String Authorization, @RequestBody RoomRequestDto roomRequestDto){
        UserDto user = authService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
//        UserDto user = UserDto.builder().userIdx(1L).userEmail("1번@naver.com").userPoint(0).userNickname("바보").build();

        return roomService.changeAdminUser(user, roomRequestDto.getRoomIdx(), roomRequestDto.getToUserIdx());
    }

    @PatchMapping("/maxuser")
    public RoomResponseDto changeMaxUser(@RequestHeader String Authorization, @RequestBody RoomRequestDto roomRequestDto){

        UserDto user = authService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
//        UserDto user = UserDto.builder().userIdx(1L).userEmail("1번@naver.com").userPoint(0).userNickname("바보").build();
        return roomService.changeMaxUser(user, roomRequestDto.getRoomIdx(), roomRequestDto.getRoomMax());

    }


    @GetMapping("/{roomIdx}")
    public ResponseEntity<RoomResponseDto> getRoom(@RequestHeader String Authorization, @PathVariable String roomIdx){
        RoomResponseDto roomResponseDto = roomService.getRoom(roomIdx);
        return ResponseEntity.ok().body(roomResponseDto);
    }
}
